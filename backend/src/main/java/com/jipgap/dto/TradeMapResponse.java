package com.jipgap.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeMapResponse {
    private String type = "FeatureCollection";
    private List<Feature> features = new ArrayList<>();

    public String getType() { return type; }
    public List<Feature> getFeatures() { return features; }

    public void addFeature(Feature feature) {
        this.features.add(feature);
    }

    public static class Feature {
        private String type = "Feature";
        private Map<String, Object> geometry;
        private Map<String, Object> properties;

        public Feature() {
        }

        public Feature(Map<String, Object> geometry, Map<String, Object> properties) {
            this.geometry = geometry;
            this.properties = properties;
        }

        public String getType() { return type; }
        public Map<String, Object> getGeometry() { return geometry; }
        public Map<String, Object> getProperties() { return properties; }
    }
}
