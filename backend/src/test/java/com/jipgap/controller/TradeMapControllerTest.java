package com.jipgap.controller;

import com.jipgap.dto.TradeDetailResponse;
import com.jipgap.dto.TradeMapResponse;
import com.jipgap.dto.TradePeriodsResponse;
import com.jipgap.exception.NotFoundException;
import com.jipgap.service.TradeMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TradeMapController.class)
class TradeMapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeMapService tradeMapService;

    @Test
    void map_requires_params() throws Exception {
        mockMvc.perform(get("/v1/trade/map"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void map_returns_ok() throws Exception {
        when(tradeMapService.getTradeMap(2025, 1)).thenReturn(new TradeMapResponse());

        mockMvc.perform(get("/v1/trade/map")
                        .param("year", "2025")
                        .param("month", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void detail_requires_params() throws Exception {
        mockMvc.perform(get("/v1/trade/detail"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void detail_returns_not_found() throws Exception {
        when(tradeMapService.getTradeDetail("11110", 2025, 1))
                .thenThrow(new NotFoundException("No trade data"));

        mockMvc.perform(get("/v1/trade/detail")
                        .param("sggCd", "11110")
                        .param("year", "2025")
                        .param("month", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void detail_returns_ok() throws Exception {
        TradeDetailResponse response = new TradeDetailResponse();
        response.setSggCd("11110");
        response.setYear(2025);
        response.setMonth(1);
        response.setSummary(new TradeDetailResponse.Summary(10000L, 15000L, 8000L, 3L));

        when(tradeMapService.getTradeDetail("11110", 2025, 1)).thenReturn(response);

        mockMvc.perform(get("/v1/trade/detail")
                        .param("sggCd", "11110")
                        .param("year", "2025")
                        .param("month", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void periods_returns_ok() throws Exception {
        TradePeriodsResponse periodsResponse = new TradePeriodsResponse();
        periodsResponse.setPeriods(List.of(new TradePeriodsResponse.Period(2025, 1)));
        when(tradeMapService.getPeriods()).thenReturn(periodsResponse);

        mockMvc.perform(get("/v1/trade/periods"))
                .andExpect(status().isOk());
    }
}
