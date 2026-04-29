package com.jipgap.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "apt_trade")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AptTrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sgg_cd", length = 5, nullable = false)
    private String sggCd;

    @Column(name = "dong_name")
    private String dongName;

    @Column(name = "apt_name")
    private String aptName;

    @Column(name = "deal_amount", nullable = false)
    private Long dealAmount;

    @Column(name = "deal_year", nullable = false)
    private Integer dealYear;

    @Column(name = "deal_month", nullable = false)
    private Integer dealMonth;

    @Column(name = "deal_day", nullable = false)
    private Integer dealDay;

    @Column(name = "exclusive_area")
    private BigDecimal exclusiveArea;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "built_year")
    private Integer builtYear;

    @Column(name = "cancel_type", length = 1)
    private String cancelType;

    @Column(name = "cancel_day", length = 8)
    private String cancelDay;

    @Column(name = "dealing_type", length = 10)
    private String dealingType;

    @Column(name = "apt_dong", length = 400)
    private String aptDong;

    @Column(name = "seller_type", length = 100)
    private String sellerType;

    @Column(name = "buyer_type", length = 100)
    private String buyerType;

    @Column(name = "land_leasehold", length = 1)
    private String landLeasehold;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private AptTrade(String sggCd, String dongName, String aptName, Long dealAmount,
                     Integer dealYear, Integer dealMonth, Integer dealDay,
                     BigDecimal exclusiveArea, Integer floor, Integer builtYear,
                     String cancelType, String cancelDay, String dealingType,
                     String aptDong, String sellerType, String buyerType, String landLeasehold) {
        this.sggCd = sggCd;
        this.dongName = dongName;
        this.aptName = aptName;
        this.dealAmount = dealAmount;
        this.dealYear = dealYear;
        this.dealMonth = dealMonth;
        this.dealDay = dealDay;
        this.exclusiveArea = exclusiveArea;
        this.floor = floor;
        this.builtYear = builtYear;
        this.cancelType = cancelType;
        this.cancelDay = cancelDay;
        this.dealingType = dealingType;
        this.aptDong = aptDong;
        this.sellerType = sellerType;
        this.buyerType = buyerType;
        this.landLeasehold = landLeasehold;
    }
}
