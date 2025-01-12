package com.kirai.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DhalariDetails {

    @NotEmpty(message = "Dhalari Id is mandatory")
    private String id;
    private String name;
    private String rythuName;
    private String location;
}
