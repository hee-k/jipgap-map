package com.jipgap.controller;

import com.jipgap.dto.CollectResponse;
import com.jipgap.service.AdminService;
import com.jipgap.service.TradeCollectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeCollectService tradeCollectService;

    @MockBean
    private AdminService adminService;

    @Test
    void collect_endpoint_returns_ok() throws Exception {
        when(tradeCollectService.collect(2025, 1)).thenReturn(new CollectResponse(2025, 1));

        mockMvc.perform(post("/v1/admin/collect")
                        .param("year", "2025")
                        .param("month", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void refresh_view_endpoint_returns_ok() throws Exception {
        mockMvc.perform(post("/v1/admin/refresh-view"))
                .andExpect(status().isOk());
    }
}
