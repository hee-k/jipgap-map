package com.jipgap.dto;

import java.util.ArrayList;
import java.util.List;

public class TradePeriodsResponse {
    private List<Period> periods = new ArrayList<>();

    public List<Period> getPeriods() { return periods; }

    public void setPeriods(List<Period> periods) { this.periods = periods; }

    public static class Period {
        private int year;
        private int month;

        public Period() {
        }

        public Period(int year, int month) {
            this.year = year;
            this.month = month;
        }

        public int getYear() { return year; }
        public int getMonth() { return month; }
    }
}
