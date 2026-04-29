package com.jipgap.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jipgap.config.MolitApiProperties;
import com.jipgap.dto.MolitApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class MolitApiClient {

    private static final XmlMapper XML_MAPPER = new XmlMapper();

    private final RestTemplate restTemplate;
    private final MolitApiProperties properties;

    public MolitApiResponse fetch(String sggCd, int year, int month) {
        String url = UriComponentsBuilder
                .fromHttpUrl(properties.getBaseUrl())
                .queryParam("serviceKey", properties.getKey())
                .queryParam("LAWD_CD", sggCd)
                .queryParam("DEAL_YMD", String.format("%04d%02d", year, month))
                .build(true)
                .toUriString();

        String xml = restTemplate.getForObject(url, String.class);
        try {
            return XML_MAPPER.readValue(xml.getBytes(StandardCharsets.UTF_8), MolitApiResponse.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse MOLIT API response", e);
        }
    }
}
