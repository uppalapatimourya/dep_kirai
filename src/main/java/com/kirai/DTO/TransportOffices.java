package com.kirai.DTO;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TransportOffices {

    private String name;
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String phoneNumber;
}
