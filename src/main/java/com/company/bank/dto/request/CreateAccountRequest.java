package com.company.bank.dto.request;

import com.company.bank.dto.request.BaseAccountRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest extends BaseAccountRequest {

    @NotBlank(message = "Account id must not be empty")
    private String id;

}
