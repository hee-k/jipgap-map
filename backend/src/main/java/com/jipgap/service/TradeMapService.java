package com.jipgap.service;

import com.jipgap.dto.TradeDetailResponse;
import com.jipgap.dto.TradeMapResponse;
import com.jipgap.dto.TradePeriodsResponse;
import com.jipgap.exception.NotFoundException;
import com.jipgap.repository.TradeQueryRepository;
import com.jipgap.util.GeoJsonConverter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TradeMapService {

    private final TradeQueryRepository tradeQueryRepository;

    public TradeMapService(TradeQueryRepository tradeQueryRepository) {
        this.tradeQueryRepository = tradeQueryRepository;
    }

    public TradeMapResponse getTradeMap(int year, int month) {
        List<Map<String, Object>> rows = tradeQueryRepository.fetchTradeMap(year, month);
        TradeMapResponse response = new TradeMapResponse();
        for (Map<String, Object> row : rows) {
            Map<String, Object> properties = new HashMap<>();
            properties.put("sggCd", row.get("sggcd"));
            properties.put("sggKorNm", row.get("sggkornm"));
            properties.put("sidoNm", row.get("sidonm"));
            properties.put("avgPrice", row.get("avgprice"));
            properties.put("avgPricePerSqm", row.get("avgpricepersqm"));
            properties.put("tradeCount", row.get("tradecount"));

            String geojson = (String) row.get("geojson");
            Map<String, Object> geometry = GeoJsonConverter.parse(geojson);
            response.addFeature(new TradeMapResponse.Feature(geometry, properties));
        }
        return response;
    }

    public TradeDetailResponse getTradeDetail(String sggCd, int year, int month) {
        TradeQueryRepository.SummaryRow summaryRow = tradeQueryRepository.fetchSummary(sggCd, year, month)
                .orElseThrow(() -> new NotFoundException("No trade data"));

        TradeDetailResponse response = new TradeDetailResponse();
        response.setSggCd(sggCd);
        response.setYear(year);
        response.setMonth(month);
        response.setSummary(new TradeDetailResponse.Summary(
                summaryRow.avgPrice(),
                summaryRow.maxPrice(),
                summaryRow.minPrice(),
                summaryRow.tradeCount()
        ));

        List<TradeQueryRepository.AptRow> aptRows = tradeQueryRepository.fetchAptDetails(sggCd, year, month);
        List<TradeDetailResponse.AptSummary> topApts = aptRows.stream()
                .map(row -> new TradeDetailResponse.AptSummary(
                        row.aptName(),
                        row.avgPrice(),
                        row.maxPrice(),
                        row.minPrice(),
                        row.tradeCount()
                ))
                .toList();
        response.setTopApts(topApts);
        return response;
    }

    public TradePeriodsResponse getPeriods() {
        List<Map<String, Object>> rows = tradeQueryRepository.fetchPeriods();
        TradePeriodsResponse response = new TradePeriodsResponse();
        List<TradePeriodsResponse.Period> periods = rows.stream()
                .map(row -> new TradePeriodsResponse.Period(
                        ((Number) row.get("year")).intValue(),
                        ((Number) row.get("month")).intValue()
                ))
                .toList();
        response.setPeriods(periods);
        return response;
    }
}
