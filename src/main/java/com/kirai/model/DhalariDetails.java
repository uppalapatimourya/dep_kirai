package com.kirai.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Dhalari")
@Data
@Builder
public class DhalariDetails {

    @NotEmpty(message = "Dhalari Id is mandatory")
    @Id
    private String id;
    private String name;
    private String rythuName;
    private String location;
}
