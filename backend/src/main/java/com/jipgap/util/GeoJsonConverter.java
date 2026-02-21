package com.jipgap.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class GeoJsonConverter {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, Object> parse(String geojson) {
        try {
            return mapper.readValue(geojson, new TypeReference<>() {});
        } catch (Exception e) {
            throw new IllegalStateException("Invalid GeoJSON", e);
        }
    }
}
