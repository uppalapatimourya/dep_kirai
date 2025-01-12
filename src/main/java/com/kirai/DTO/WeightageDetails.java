package com.kirai.DTO;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class WeightageDetails {

    private String id;
    private String billNumber;
    private String type;
    private long total;
    private long empty;
    private long itemWeight;
}
