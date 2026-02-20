package com.jipgap.controller;

import com.jipgap.dto.CollectResponse;
import com.jipgap.service.AdminService;
import com.jipgap.service.TradeCollectService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/admin")
@Validated
public class AdminController {

    private final TradeCollectService tradeCollectService;
    private final AdminService adminService;

    public AdminController(TradeCollectService tradeCollectService, AdminService adminService) {
        this.tradeCollectService = tradeCollectService;
        this.adminService = adminService;
    }

    @PostMapping("/collect")
    public ResponseEntity<CollectResponse> collect(
            @RequestParam @NotNull @Min(2005) @Max(2100) Integer year,
            @RequestParam @NotNull @Min(1) @Max(12) Integer month
    ) {
        return ResponseEntity.ok(tradeCollectService.collect(year, month));
    }

    @PostMapping("/refresh-view")
    public ResponseEntity<Void> refreshView() {
        adminService.refreshMaterializedView();
        return ResponseEntity.ok().build();
    }
}
