package com.jipgap.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "apt_trade")
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

    public AptTrade() {
    }

    public Long getId() { return id; }
    public String getSggCd() { return sggCd; }
    public void setSggCd(String sggCd) { this.sggCd = sggCd; }
    public String getDongName() { return dongName; }
    public void setDongName(String dongName) { this.dongName = dongName; }
    public String getAptName() { return aptName; }
    public void setAptName(String aptName) { this.aptName = aptName; }
    public Long getDealAmount() { return dealAmount; }
    public void setDealAmount(Long dealAmount) { this.dealAmount = dealAmount; }
    public Integer getDealYear() { return dealYear; }
    public void setDealYear(Integer dealYear) { this.dealYear = dealYear; }
    public Integer getDealMonth() { return dealMonth; }
    public void setDealMonth(Integer dealMonth) { this.dealMonth = dealMonth; }
    public Integer getDealDay() { return dealDay; }
    public void setDealDay(Integer dealDay) { this.dealDay = dealDay; }
    public BigDecimal getExclusiveArea() { return exclusiveArea; }
    public void setExclusiveArea(BigDecimal exclusiveArea) { this.exclusiveArea = exclusiveArea; }
    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
    public Integer getBuiltYear() { return builtYear; }
    public void setBuiltYear(Integer builtYear) { this.builtYear = builtYear; }
    public String getCancelType() { return cancelType; }
    public void setCancelType(String cancelType) { this.cancelType = cancelType; }
    public String getCancelDay() { return cancelDay; }
    public void setCancelDay(String cancelDay) { this.cancelDay = cancelDay; }
    public String getDealingType() { return dealingType; }
    public void setDealingType(String dealingType) { this.dealingType = dealingType; }
    public String getAptDong() { return aptDong; }
    public void setAptDong(String aptDong) { this.aptDong = aptDong; }
    public String getSellerType() { return sellerType; }
    public void setSellerType(String sellerType) { this.sellerType = sellerType; }
    public String getBuyerType() { return buyerType; }
    public void setBuyerType(String buyerType) { this.buyerType = buyerType; }
    public String getLandLeasehold() { return landLeasehold; }
    public void setLandLeasehold(String landLeasehold) { this.landLeasehold = landLeasehold; }
}
