package com.jipgap.dto;

import java.util.ArrayList;
import java.util.List;

public class TradeDetailResponse {
    private String sggCd;
    private int year;
    private int month;
    private Summary summary;
    private List<AptSummary> topApts = new ArrayList<>();

    public String getSggCd() { return sggCd; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public Summary getSummary() { return summary; }
    public List<AptSummary> getTopApts() { return topApts; }

    public void setSggCd(String sggCd) { this.sggCd = sggCd; }
    public void setYear(int year) { this.year = year; }
    public void setMonth(int month) { this.month = month; }
    public void setSummary(Summary summary) { this.summary = summary; }
    public void setTopApts(List<AptSummary> topApts) { this.topApts = topApts; }

    public static class Summary {
        private Long avgPrice;
        private Long maxPrice;
        private Long minPrice;
        private Long tradeCount;

        public Summary() {
        }

        public Summary(Long avgPrice, Long maxPrice, Long minPrice, Long tradeCount) {
            this.avgPrice = avgPrice;
            this.maxPrice = maxPrice;
            this.minPrice = minPrice;
            this.tradeCount = tradeCount;
        }

        public Long getAvgPrice() { return avgPrice; }
        public Long getMaxPrice() { return maxPrice; }
        public Long getMinPrice() { return minPrice; }
        public Long getTradeCount() { return tradeCount; }
    }

    public static class AptSummary {
        private String aptName;
        private Long avgPrice;
        private Long maxPrice;
        private Long minPrice;
        private Long tradeCount;

        public AptSummary() {
        }

        public AptSummary(String aptName, Long avgPrice, Long maxPrice, Long minPrice, Long tradeCount) {
            this.aptName = aptName;
            this.avgPrice = avgPrice;
            this.maxPrice = maxPrice;
            this.minPrice = minPrice;
            this.tradeCount = tradeCount;
        }

        public String getAptName() { return aptName; }
        public Long getAvgPrice() { return avgPrice; }
        public Long getMaxPrice() { return maxPrice; }
        public Long getMinPrice() { return minPrice; }
        public Long getTradeCount() { return tradeCount; }
    }
}
