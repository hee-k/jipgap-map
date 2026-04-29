package com.jipgap.controller;

import com.jipgap.dto.CollectResponse;
import com.jipgap.service.AdminService;
import com.jipgap.service.TradeCollectService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private final TradeCollectService tradeCollectService;
    private final AdminService adminService;

    @PostMapping("/collect")
    public CollectResponse collect(
            @RequestParam @NotNull @Min(2005) @Max(2100) Integer year,
            @RequestParam @NotNull @Min(1) @Max(12) Integer month
    ) {
        return tradeCollectService.collect(year, month);
    }

    @PostMapping("/refresh-view")
    public ResponseEntity<Void> refreshView() {
        adminService.refreshMaterializedView();
        return ResponseEntity.ok().build();
    }
}
