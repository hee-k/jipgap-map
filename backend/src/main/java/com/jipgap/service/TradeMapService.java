package com.jipgap.service;

import com.jipgap.dto.TradeDetailResponse;
import com.jipgap.dto.TradeMapResponse;
import com.jipgap.dto.TradePeriodsResponse;
import com.jipgap.exception.NotFoundException;
import com.jipgap.repository.TradeQueryRepository;
import com.jipgap.repository.TradeQueryRepository.AptRow;
import com.jipgap.repository.TradeQueryRepository.PeriodRow;
import com.jipgap.repository.TradeQueryRepository.SummaryRow;
import com.jipgap.repository.TradeQueryRepository.TradeMapRow;
import com.jipgap.util.GeoJsonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeMapService {

    private final TradeQueryRepository tradeQueryRepository;

    public TradeMapResponse getTradeMap(int year, int month) {
        List<TradeMapRow> rows = tradeQueryRepository.fetchTradeMap(year, month);
        TradeMapResponse response = new TradeMapResponse();
        for (TradeMapRow row : rows) {
            response.addFeature(new TradeMapResponse.Feature(
                    GeoJsonConverter.parse(row.geojson()),
                    toProperties(row)
            ));
        }
        return response;
    }

    public TradeDetailResponse getTradeDetail(String sggCd, int year, int month) {
        SummaryRow summary = tradeQueryRepository.fetchSummary(sggCd, year, month)
                .orElseThrow(() -> new NotFoundException("No trade data"));

        List<TradeDetailResponse.AptSummary> topApts = tradeQueryRepository.fetchAptDetails(sggCd, year, month).stream()
                .map(TradeMapService::toAptSummary)
                .toList();

        TradeDetailResponse response = new TradeDetailResponse();
        response.setSggCd(sggCd);
        response.setYear(year);
        response.setMonth(month);
        response.setSummary(new TradeDetailResponse.Summary(
                summary.avgPrice(), summary.maxPrice(), summary.minPrice(), summary.tradeCount()
        ));
        response.setTopApts(topApts);
        return response;
    }

    public TradePeriodsResponse getPeriods() {
        List<TradePeriodsResponse.Period> periods = tradeQueryRepository.fetchPeriods().stream()
                .map(TradeMapService::toPeriod)
                .toList();
        TradePeriodsResponse response = new TradePeriodsResponse();
        response.setPeriods(periods);
        return response;
    }

    private static Map<String, Object> toProperties(TradeMapRow row) {
        Map<String, Object> props = new HashMap<>();
        props.put("sggCd", row.sggCd());
        props.put("sggKorNm", row.sggKorNm());
        props.put("sidoNm", row.sidoNm());
        props.put("avgPrice", row.avgPrice());
        props.put("avgPricePerSqm", row.avgPricePerSqm());
        props.put("tradeCount", row.tradeCount());
        return props;
    }

    private static TradeDetailResponse.AptSummary toAptSummary(AptRow row) {
        return new TradeDetailResponse.AptSummary(
                row.aptName(), row.avgPrice(), row.maxPrice(), row.minPrice(), row.tradeCount()
        );
    }

    private static TradePeriodsResponse.Period toPeriod(PeriodRow row) {
        return new TradePeriodsResponse.Period(row.year(), row.month());
    }
}
