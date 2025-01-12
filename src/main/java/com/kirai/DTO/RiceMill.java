package com.kirai.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RiceMill {

    @NotEmpty(message = "Id should be null")
    private String id;
    private String name;
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String phone;
    private String contactPerson;
    private String location;
    @NotEmpty(message = "GST number is mandatory")
    private String GST;
}
