package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
public class AccumulatePriceDto {
    private String grade;
    private int diff;
    private boolean disCountYN;
}
