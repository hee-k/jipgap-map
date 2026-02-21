package com.jipgap.controller;

import com.jipgap.dto.ErrorResponse;
import com.jipgap.dto.TradeDetailResponse;
import com.jipgap.dto.TradeMapResponse;
import com.jipgap.dto.TradePeriodsResponse;
import com.jipgap.exception.NotFoundException;
import com.jipgap.service.TradeMapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/trade")
public class TradeMapController {

    private final TradeMapService tradeMapService;

    public TradeMapController(TradeMapService tradeMapService) {
        this.tradeMapService = tradeMapService;
    }

    @GetMapping("/map")
    public ResponseEntity<?> map(@RequestParam(required = false) Integer year,
                                 @RequestParam(required = false) Integer month) {
        if (year == null || month == null) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("BAD_REQUEST", "year and month are required"));
        }
        TradeMapResponse response = tradeMapService.getTradeMap(year, month);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> detail(@RequestParam(required = false) String sggCd,
                                    @RequestParam(required = false) Integer year,
                                    @RequestParam(required = false) Integer month) {
        if (sggCd == null || sggCd.isBlank() || year == null || month == null) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("BAD_REQUEST", "sggCd, year, month are required"));
        }
        try {
            TradeDetailResponse response = tradeMapService.getTradeDetail(sggCd, year, month);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", e.getMessage()));
        }
    }

    @GetMapping("/periods")
    public ResponseEntity<TradePeriodsResponse> periods() {
        return ResponseEntity.ok(tradeMapService.getPeriods());
    }
}
