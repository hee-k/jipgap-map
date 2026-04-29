package com.jipgap.controller;

import com.jipgap.dto.TradeDetailResponse;
import com.jipgap.dto.TradeMapResponse;
import com.jipgap.dto.TradePeriodsResponse;
import com.jipgap.service.TradeMapService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/trade")
@RequiredArgsConstructor
@Validated
public class TradeMapController {

    private final TradeMapService tradeMapService;

    @GetMapping("/map")
    public TradeMapResponse map(
            @RequestParam @NotNull @Min(2005) @Max(2100) Integer year,
            @RequestParam @NotNull @Min(1) @Max(12) Integer month
    ) {
        return tradeMapService.getTradeMap(year, month);
    }

    @GetMapping("/detail")
    public TradeDetailResponse detail(
            @RequestParam @NotBlank String sggCd,
            @RequestParam @NotNull @Min(2005) @Max(2100) Integer year,
            @RequestParam @NotNull @Min(1) @Max(12) Integer month
    ) {
        return tradeMapService.getTradeDetail(sggCd, year, month);
    }

    @GetMapping("/periods")
    public TradePeriodsResponse periods() {
        return tradeMapService.getPeriods();
    }
}
