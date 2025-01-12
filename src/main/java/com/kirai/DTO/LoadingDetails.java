package com.kirai.DTO;

import lombok.Data;

@Data
public class LoadingDetails {

    private long perBag;
    private String deliveryType;
    private String riceType;
    private long bagCount;
    private String waymentType;
    private long loadingRate;
    private long commission;
    private long totalRate;
}
