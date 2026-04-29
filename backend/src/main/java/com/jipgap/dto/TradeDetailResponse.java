package com.jipgap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TradeDetailResponse {
    private String sggCd;
    private int year;
    private int month;
    private Summary summary;
    private List<AptSummary> topApts = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Summary {
        private Long avgPrice;
        private Long maxPrice;
        private Long minPrice;
        private Long tradeCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AptSummary {
        private String aptName;
        private Long avgPrice;
        private Long maxPrice;
        private Long minPrice;
        private Long tradeCount;
    }
}
