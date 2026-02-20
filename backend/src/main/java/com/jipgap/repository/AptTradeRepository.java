package com.jipgap.repository;

import com.jipgap.domain.AptTrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface AptTradeRepository extends JpaRepository<AptTrade, Long> {
    boolean existsBySggCdAndAptNameAndDealYearAndDealMonthAndDealDayAndExclusiveAreaAndDealAmountAndFloor(
            String sggCd,
            String aptName,
            Integer dealYear,
            Integer dealMonth,
            Integer dealDay,
            BigDecimal exclusiveArea,
            Long dealAmount,
            Integer floor
    );
}
