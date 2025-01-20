package com.kirai.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("RiceMill")
@Data
@Builder
public class RiceMill {

    @NotEmpty(message = "Id should be null")
    @Id
    private String id;
    private String name;
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String phone;
    private String contactPerson;
    private String location;
    @NotEmpty(message = "GST number is mandatory")
    private String GST;
}
