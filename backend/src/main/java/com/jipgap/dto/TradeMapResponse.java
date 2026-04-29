package com.jipgap.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class TradeMapResponse {
    private final String type = "FeatureCollection";
    private final List<Feature> features = new ArrayList<>();

    public void addFeature(Feature feature) {
        this.features.add(feature);
    }

    @Getter
    @RequiredArgsConstructor
    public static class Feature {
        private final String type = "Feature";
        private final Map<String, Object> geometry;
        private final Map<String, Object> properties;
    }
}
