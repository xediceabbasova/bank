package com.company.bank.dto.request;

import com.company.bank.dto.request.BaseAccountRequest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest extends BaseAccountRequest {

    private String id;

}
