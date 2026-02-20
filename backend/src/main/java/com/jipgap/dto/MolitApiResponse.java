package com.jipgap.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "response")
public class MolitApiResponse {

    @JacksonXmlProperty(localName = "header")
    private Header header;

    @JacksonXmlProperty(localName = "body")
    private Body body;

    public Header getHeader() { return header; }
    public Body getBody() { return body; }

    public static long parseDealAmount(String raw) {
        if (raw == null) return 0L;
        return Long.parseLong(raw.replaceAll(",", "").trim());
    }

    public static class Header {
        @JacksonXmlProperty(localName = "resultCode")
        private String resultCode;
        @JacksonXmlProperty(localName = "resultMsg")
        private String resultMsg;

        public String getResultCode() { return resultCode; }
        public String getResultMsg() { return resultMsg; }
    }

    public static class Body {
        @JacksonXmlProperty(localName = "items")
        private Items items;

        public Items getItems() { return items; }
    }

    public static class Items {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "item")
        private List<Item> item;

        public List<Item> getItem() { return item; }
    }

    public static class Item {
        @JacksonXmlProperty(localName = "dealAmount")
        private String dealAmount;
        @JacksonXmlProperty(localName = "dealYear")
        private String dealYear;
        @JacksonXmlProperty(localName = "dealMonth")
        private String dealMonth;
        @JacksonXmlProperty(localName = "dealDay")
        private String dealDay;
        @JacksonXmlProperty(localName = "dong")
        private String dong;
        @JacksonXmlProperty(localName = "aptName")
        private String aptName;
        @JacksonXmlProperty(localName = "excluUseAr")
        private String excluUseAr;
        @JacksonXmlProperty(localName = "floor")
        private String floor;
        @JacksonXmlProperty(localName = "buildYear")
        private String builtYear;
        @JacksonXmlProperty(localName = "cdealType")
        private String cancelType;
        @JacksonXmlProperty(localName = "cdealDay")
        private String cancelDay;
        @JacksonXmlProperty(localName = "dealingGbn")
        private String dealingType;
        @JacksonXmlProperty(localName = "aptDong")
        private String aptDong;
        @JacksonXmlProperty(localName = "sellerGbn")
        private String sellerType;
        @JacksonXmlProperty(localName = "buyerGbn")
        private String buyerType;
        @JacksonXmlProperty(localName = "landLeaseholdGbn")
        private String landLeasehold;

        public String getDealAmount() { return dealAmount; }
        public String getDealYear() { return dealYear; }
        public String getDealMonth() { return dealMonth; }
        public String getDealDay() { return dealDay; }
        public String getDong() { return dong; }
        public String getAptName() { return aptName; }
        public String getExcluUseAr() { return excluUseAr; }
        public String getFloor() { return floor; }
        public String getBuiltYear() { return builtYear; }
        public String getCancelType() { return cancelType; }
        public String getCancelDay() { return cancelDay; }
        public String getDealingType() { return dealingType; }
        public String getAptDong() { return aptDong; }
        public String getSellerType() { return sellerType; }
        public String getBuyerType() { return buyerType; }
        public String getLandLeasehold() { return landLeasehold; }
    }
}
