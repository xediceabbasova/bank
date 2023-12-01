package com.company.bank.service;

import com.company.bank.dto.AccountDTO;
import com.company.bank.dto.converter.AccountDTOConverter;
import com.company.bank.dto.request.CreateAccountRequest;
import com.company.bank.dto.request.MoneyTransferRequest;
import com.company.bank.dto.request.UpdateAccountRequest;
import com.company.bank.model.Account;
import com.company.bank.model.Customer;
import com.company.bank.repository.AccountRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDTOConverter accountDTOConverter;
    private final AmqpTemplate rabbitTemplate;
    private final DirectExchange exchange;

    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;

    @Value("${sample.rabbitmq.queue}")
    String queueName;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, AccountDTOConverter accountDTOConverter, AmqpTemplate rabbitTemplate, DirectExchange exchange) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountDTOConverter = accountDTOConverter;
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public AccountDTO createAccount(CreateAccountRequest createAccountRequest) {

        Customer customer = customerService.getCustomerById(createAccountRequest.getCustomerId());

        if (customer.getId() == null || customer.getId().trim().equals("")) {
            throw new RuntimeException("Customer not found");
        }

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();

        return accountDTOConverter.convert(accountRepository.save(account));
    }

    public AccountDTO updateAccount(String id, UpdateAccountRequest request) {
        Customer customer = customerService.getCustomerById(request.getCustomerId());

        if (customer.getId() == null || customer.getId().equals("")) {
            return AccountDTO.builder().build();
        }

        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
            account.setBalance(request.getBalance());
            account.setCity(request.getCity());
            account.setCurrency(request.getCurrency());
            account.setCustomerId(request.getCustomerId());
            accountRepository.save(account);
        });

        return accountOptional.map(accountDTOConverter::convert).orElse(new AccountDTO());

    }

    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }

    public List<AccountDTO> getAllAccounts() {
        List<Account> accountList = accountRepository.findAll();
        return accountList
                .stream()
                .map(accountDTOConverter::convert)
                .collect(Collectors.toList());
    }

    public AccountDTO getAccountById(String id) {
        return accountRepository.findById(id)
                .map(accountDTOConverter::convert)
                .orElse(new AccountDTO());
    }

    public AccountDTO withDrawMoney(String id, Double amount) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
            if (account.getBalance() > amount) {
                account.setBalance(account.getBalance() - amount);
                accountRepository.save(account);
            } else {
                System.out.println("Insufficient funds -> accountId:" + id + "balance:" + account.getBalance() + "amount:" + amount);
            }
        });
        return accountOptional.map(accountDTOConverter::convert).orElse(new AccountDTO());
    }

    public AccountDTO addMoney(String id, Double amount) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
        });
        return accountOptional.map(accountDTOConverter::convert).orElse(new AccountDTO());
    }

    public void transferMoney(MoneyTransferRequest transferRequest) {
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, transferRequest);
    }

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void transferMoneyMessage(MoneyTransferRequest transferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getFromId());
        accountOptional.ifPresentOrElse(account -> {
                    if (account.getBalance() > transferRequest.getAmount()) {
                        account.setBalance(account.getBalance() - transferRequest.getAmount());
                        accountRepository.save(account);
                        rabbitTemplate.convertAndSend(exchange.getName(), "secondRoute", transferRequest);
                    } else {
                        System.out.println("Insufficient funds -> accountId: " + transferRequest.getFromId() + " balance: " + account.getBalance() + " amount: " + transferRequest.getAmount());
                    }
                },
                () -> System.out.println("Account not found")
        );
    }

    @RabbitListener(queues = "secondStepQueue")
    public void updateReceiverAccount(MoneyTransferRequest transferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getToId());
        accountOptional.ifPresentOrElse(account -> {
                    account.setBalance(account.getBalance() + transferRequest.getAmount());
                    accountRepository.save(account);
                    rabbitTemplate.convertAndSend(exchange.getName(), "thirdRoute", transferRequest);
                },
                () -> {
                    System.out.println("Receiver Account not found");
                    Optional<Account> senderAccount = accountRepository.findById(transferRequest.getFromId());
                    senderAccount.ifPresent(sender -> {
                        System.out.println("Money charge back to sender");
                        sender.setBalance(sender.getBalance() + transferRequest.getAmount());
                        accountRepository.save(sender);
                    });

                }
        );
    }

    @RabbitListener(queues = "thirdStepQueue")
    public void finalizeTransfer(MoneyTransferRequest transferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getFromId());
        accountOptional.ifPresentOrElse(account ->
                        System.out.println("Sender(" + account.getId() + ") new account balance:" + account.getBalance()),
                () -> System.out.println("Account not found")
        );

        Optional<Account> accountToOptional = accountRepository.findById(transferRequest.getToId());
        accountToOptional.ifPresentOrElse(account ->
                        System.out.println("Receiver(" + account.getId() + ") new account balance: " + account.getBalance()),
                () -> System.out.println("Account not found")
        );
    }
}

