package com.jipgap.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public final class GeoJsonConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};

    private GeoJsonConverter() {
    }

    public static Map<String, Object> parse(String geojson) {
        try {
            return MAPPER.readValue(geojson, MAP_TYPE);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid GeoJSON", e);
        }
    }
}
