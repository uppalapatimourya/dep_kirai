package com.kirai.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class KiraiDetailsDTO {

    private String KLNO;
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
