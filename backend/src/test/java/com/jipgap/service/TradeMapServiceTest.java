package com.jipgap.service;

import com.jipgap.dto.TradeDetailResponse;
import com.jipgap.dto.TradeMapResponse;
import com.jipgap.dto.TradePeriodsResponse;
import com.jipgap.exception.NotFoundException;
import com.jipgap.repository.TradeQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeMapServiceTest {

    @Mock
    private TradeQueryRepository tradeQueryRepository;

    @InjectMocks
    private TradeMapService tradeMapService;

    @Test
    void getTradeMap_returns_feature_collection() {
        Map<String, Object> row = Map.of(
                "sggcd", "11110",
                "sggkornm", "종로구",
                "sidonm", "서울특별시",
                "avgprice", 12000L,
                "avgpricepersqm", 350L,
                "tradecount", 10L,
                "geojson", "{\"type\":\"Polygon\",\"coordinates\":[[[0,0],[1,0],[1,1],[0,0]]]}"
        );
        when(tradeQueryRepository.fetchTradeMap(2025, 1)).thenReturn(List.of(row));

        TradeMapResponse response = tradeMapService.getTradeMap(2025, 1);

        assertThat(response.getFeatures()).hasSize(1);
        assertThat(response.getFeatures().get(0).getProperties().get("sggCd")).isEqualTo("11110");
    }

    @Test
    void getTradeMap_returns_empty_when_no_data() {
        when(tradeQueryRepository.fetchTradeMap(2025, 1)).thenReturn(List.of());

        TradeMapResponse response = tradeMapService.getTradeMap(2025, 1);

        assertThat(response.getFeatures()).isEmpty();
    }

    @Test
    void getTradeDetail_returns_summary_and_apts() {
        when(tradeQueryRepository.fetchSummary("11110", 2025, 1))
                .thenReturn(Optional.of(new TradeQueryRepository.SummaryRow(12000L, 20000L, 8000L, 5L)));
        when(tradeQueryRepository.fetchAptDetails("11110", 2025, 1))
                .thenReturn(List.of(new TradeQueryRepository.AptRow("테스트아파트", 12000L, 20000L, 8000L, 5L)));

        TradeDetailResponse response = tradeMapService.getTradeDetail("11110", 2025, 1);

        assertThat(response.getSummary().getTradeCount()).isEqualTo(5L);
        assertThat(response.getTopApts()).hasSize(1);
    }

    @Test
    void getTradeDetail_throws_not_found_when_empty() {
        when(tradeQueryRepository.fetchSummary("11110", 2025, 1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> tradeMapService.getTradeDetail("11110", 2025, 1))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void getPeriods_returns_list() {
        when(tradeQueryRepository.fetchPeriods())
                .thenReturn(List.of(Map.of("year", 2025, "month", 1)));

        TradePeriodsResponse response = tradeMapService.getPeriods();

        assertThat(response.getPeriods()).hasSize(1);
        assertThat(response.getPeriods().get(0).getYear()).isEqualTo(2025);
    }
}
