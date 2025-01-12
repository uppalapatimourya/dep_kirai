package com.kirai.model;

import com.kirai.DTO.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("KirayDetails")
@Data
@Builder
public class Kirai {

    @Id
    private String id;
    private String loadingDate;
    private String reachedDate;
    private Mediator mediator;
    private String notes;
    private String instructions;
    private RiceMill riceMill;
    private LoadingDetails loadingDetails;
    private DhalariDetails dhalariDetails;
    private LorryDetails lorryDetails;
    private TransportOffices transportOffices;
    private KiraiDetails kiraiDetails;
    private WeightageDetails weightageDetails;

}
