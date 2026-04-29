package com.jipgap.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    public void addFailedSggCd(String sggCd) {
        this.failedSggCd.add(sggCd);
    }
}
