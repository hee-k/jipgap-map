package com.jipgap.dto;

import java.util.ArrayList;
import java.util.List;

public class CollectResponse {
    private int year;
    private int month;
    private int totalRequested;
    private int totalInserted;
    private List<String> failedSggCd = new ArrayList<>();

    public CollectResponse() {
    }

    public CollectResponse(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getTotalRequested() { return totalRequested; }
    public int getTotalInserted() { return totalInserted; }
    public List<String> getFailedSggCd() { return failedSggCd; }

    public void setTotalRequested(int totalRequested) { this.totalRequested = totalRequested; }
    public void setTotalInserted(int totalInserted) { this.totalInserted = totalInserted; }
    public void addFailedSggCd(String sggCd) { this.failedSggCd.add(sggCd); }
}
