package com.jipgap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TradePeriodsResponse {
    private List<Period> periods = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Period {
        private int year;
        private int month;
    }
}
