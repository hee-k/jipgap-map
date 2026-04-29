package com.jipgap.service;

import com.jipgap.domain.AptTrade;
import com.jipgap.dto.MolitApiResponse;

import java.math.BigDecimal;

final class MolitItemMapper {

    private MolitItemMapper() {
    }

    static AptTrade toEntity(String sggCd, MolitApiResponse.Item item) {
        return AptTrade.builder()
                .sggCd(sggCd)
                .dongName(trim(item.getDong()))
                .aptName(trim(item.getAptName()))
                .dealAmount(MolitApiResponse.parseDealAmount(item.getDealAmount()))
                .dealYear(parseInt(item.getDealYear()))
                .dealMonth(parseInt(item.getDealMonth()))
                .dealDay(parseInt(item.getDealDay()))
                .exclusiveArea(parseBigDecimal(item.getExcluUseAr()))
                .floor(parseInteger(item.getFloor()))
                .builtYear(parseInteger(item.getBuiltYear()))
                .cancelType(trim(item.getCancelType()))
                .cancelDay(trim(item.getCancelDay()))
                .dealingType(trim(item.getDealingType()))
                .aptDong(trim(item.getAptDong()))
                .sellerType(trim(item.getSellerType()))
                .buyerType(trim(item.getBuyerType()))
                .landLeasehold(trim(item.getLandLeasehold()))
                .build();
    }

    private static Integer parseInteger(String raw) {
        return isBlank(raw) ? null : Integer.valueOf(raw.trim());
    }

    private static int parseInt(String raw) {
        return isBlank(raw) ? 0 : Integer.parseInt(raw.trim());
    }

    private static BigDecimal parseBigDecimal(String raw) {
        return isBlank(raw) ? null : new BigDecimal(raw.trim());
    }

    private static String trim(String raw) {
        return raw == null ? null : raw.trim();
    }

    private static boolean isBlank(String raw) {
        return raw == null || raw.trim().isEmpty();
    }
}
