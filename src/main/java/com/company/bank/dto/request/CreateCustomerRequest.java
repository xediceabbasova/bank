package com.company.bank.dto.request;

import com.company.bank.dto.request.BaseCustomerRequest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest extends BaseCustomerRequest {


    private String id;


}
