//package com.company.bank.service;
//
//import com.company.bank.dto.AccountDTO;
//import com.company.bank.dto.converter.AccountDTOConverter;
//import com.company.bank.dto.request.CreateAccountRequest;
//import com.company.bank.model.*;
//import com.company.bank.repository.AccountRepository;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import java.util.Set;
//
//public class AccountServiceTest {
//
//    private AccountService accountService;
//    private AccountRepository accountRepository;
//    private CustomerService customerService;
//    private AccountDTOConverter accountDTOConverter;
//
//    @Before
//    public void setUp() throws Exception {
//
//        accountRepository = Mockito.mock(AccountRepository.class);
//        customerService = Mockito.mock(CustomerService.class);
//        accountDTOConverter = Mockito.mock(AccountDTOConverter.class);
//        accountService = new AccountService(accountRepository, customerService, accountDTOConverter, rabbitTemplate);
//    }
//
//    @Test
//    public void whenCreateAccountCalledWithValidRequest_itShouldReturnValidAccountDto() {
//        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
//        createAccountRequest.setCustomerId("12345");
//        createAccountRequest.setBalance(100.0);
//        createAccountRequest.setCity(City.ISTANBUL);
//        createAccountRequest.setCurrency(Currency.TRY);
//
//        Customer customer = Customer.builder()
//                .id("12345")
//                .address(Set.of(Address.builder()
//                        .city(City.ISTANBUL)
//                        .postcode("541545")
//                        .addressDetails("this is address")
//                        .build()))
//                .city(City.ISTANBUL)
//                .dateOfBirth(1998)
//                .name("Muratcan")
//                .build();
//
//        Account account = Account.builder()
//                .id(createAccountRequest.getId())
//                .balance(createAccountRequest.getBalance())
//                .currency(createAccountRequest.getCurrency())
//                .customerId(createAccountRequest.getCustomerId())
//                .city(createAccountRequest.getCity())
//                .build();
//
//        AccountDTO accountDTO = AccountDTO.builder()
//                .id("1234")
//                .customerId("12345")
//                .currency(Currency.TRY)
//                .balance(100.0)
//                .build();
//
//        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
//        Mockito.when(accountRepository.save(account)).thenReturn(account);
//        Mockito.when(accountDTOConverter.convert(account)).thenReturn(accountDTO);
//
//        AccountDTO result = accountService.createAccount(createAccountRequest);
//        Assert.assertEquals(result, accountDTO);
//        Mockito.verify(customerService).getCustomerById("12345");
//        Mockito.verify(accountRepository).save(account);
//        Mockito.verify(accountDTOConverter).convert(account);
//
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void whenCreateAccountCalledWithNonExistCustomer_itShouldReturnEmptyAccountDto() {
//        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
//        createAccountRequest.setCustomerId("12345");
//        createAccountRequest.setBalance(100.0);
//        createAccountRequest.setCity(City.ISTANBUL);
//        createAccountRequest.setCurrency(Currency.TRY);
//
//        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder().build());
//
//        AccountDTO expectedAccountDTO = AccountDTO.builder().build();
//
//        AccountDTO result = accountService.createAccount(createAccountRequest);
//
//        Assert.assertEquals(result, expectedAccountDTO);
//        Mockito.verifyNoInteractions(accountRepository);
//        Mockito.verifyNoInteractions(accountDTOConverter);
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void whenCreateAccountCalledWithCustomerWithoutId_itShouldReturnEmptyAccountDto() {
//        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
//        createAccountRequest.setCustomerId("12345");
//        createAccountRequest.setBalance(100.0);
//        createAccountRequest.setCity(City.ISTANBUL);
//        createAccountRequest.setCurrency(Currency.TRY);
//
//        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder()
//                .id(" ")
//                .build());
//
//        AccountDTO expectedAccountDTO = AccountDTO.builder().build();
//
//        AccountDTO result = accountService.createAccount(createAccountRequest);
//
//        Assert.assertEquals(result, expectedAccountDTO);
//        Mockito.verifyNoInteractions(accountRepository);
//        Mockito.verifyNoInteractions(accountDTOConverter);
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void whenCreateAccountCalledAndRepositoryThrewException_itShouldThrowException() {
//        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
//        createAccountRequest.setCustomerId("12345");
//        createAccountRequest.setBalance(100.0);
//        createAccountRequest.setCity(City.ISTANBUL);
//        createAccountRequest.setCurrency(Currency.TRY);
//
//        Customer customer = Customer.builder()
//                .id("12345")
//                .address(Set.of(Address.builder()
//                        .city(City.ISTANBUL)
//                        .postcode("541545")
//                        .addressDetails("this is address")
//                        .build()))
//                .city(City.ISTANBUL)
//                .dateOfBirth(1998)
//                .name("Muratcan")
//                .build();
//
//        Account account = Account.builder()
//                .id(createAccountRequest.getId())
//                .balance(createAccountRequest.getBalance())
//                .currency(createAccountRequest.getCurrency())
//                .customerId(createAccountRequest.getCustomerId())
//                .city(createAccountRequest.getCity())
//                .build();
//
//
//        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
//        Mockito.when(accountRepository.save(account)).thenThrow(new RuntimeException("Exception bas verdi"));
//
//        accountService.createAccount(createAccountRequest);
//
//        Mockito.verify(customerService).getCustomerById("12345");
//        Mockito.verify(accountRepository).save(account);
//        Mockito.verifyNoInteractions(accountDTOConverter);
//
//
//    }
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
