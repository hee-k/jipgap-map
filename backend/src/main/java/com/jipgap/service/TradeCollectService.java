package com.jipgap.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jipgap.config.MolitApiProperties;
import com.jipgap.domain.AptTrade;
import com.jipgap.dto.CollectResponse;
import com.jipgap.dto.MolitApiResponse;
import com.jipgap.repository.AptTradeRepository;
import com.jipgap.repository.SggBoundaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class TradeCollectService {

    private final RestTemplate restTemplate;
    private final MolitApiProperties molitApiProperties;
    private final SggBoundaryRepository sggBoundaryRepository;
    private final AptTradeRepository aptTradeRepository;
    private final XmlMapper xmlMapper = new XmlMapper();

    public TradeCollectService(RestTemplate restTemplate,
                               MolitApiProperties molitApiProperties,
                               SggBoundaryRepository sggBoundaryRepository,
                               AptTradeRepository aptTradeRepository) {
        this.restTemplate = restTemplate;
        this.molitApiProperties = molitApiProperties;
        this.sggBoundaryRepository = sggBoundaryRepository;
        this.aptTradeRepository = aptTradeRepository;
    }

    public CollectResponse collect(int year, int month) {
        List<String> sggCodes = sggBoundaryRepository.findAllSggCodes();
        CollectResponse response = new CollectResponse(year, month);
        response.setTotalRequested(sggCodes.size());

        int inserted = 0;
        for (String sggCd : sggCodes) {
            MolitApiResponse molit = fetchMolitData(sggCd, year, month);
            String resultCode = molit.getHeader() != null ? molit.getHeader().getResultCode() : null;

            if ("03".equals(resultCode)) {
                continue; // no data
            }
            if ("22".equals(resultCode)) {
                response.addFailedSggCd(sggCd);
                continue;
            }
            if (!"00".equals(resultCode)) {
                response.addFailedSggCd(sggCd);
                continue;
            }

            if (molit.getBody() == null || molit.getBody().getItems() == null || molit.getBody().getItems().getItem() == null) {
                continue;
            }

            for (MolitApiResponse.Item item : molit.getBody().getItems().getItem()) {
                AptTrade trade = toEntity(sggCd, item);
                if (!existsTrade(trade)) {
                    aptTradeRepository.save(trade);
                    inserted++;
                }
            }
        }

        response.setTotalInserted(inserted);
        return response;
    }

    private MolitApiResponse fetchMolitData(String sggCd, int year, int month) {
        String dealYmd = String.format("%04d%02d", year, month);
        String url = UriComponentsBuilder
                .fromHttpUrl(molitApiProperties.getBaseUrl())
                .queryParam("serviceKey", molitApiProperties.getKey())
                .queryParam("LAWD_CD", sggCd)
                .queryParam("DEAL_YMD", dealYmd)
                .build(true)
                .toUriString();

        String xml = restTemplate.getForObject(url, String.class);
        try {
            return xmlMapper.readValue(xml.getBytes(StandardCharsets.UTF_8), MolitApiResponse.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse MOLIT API response", e);
        }
    }

    private AptTrade toEntity(String sggCd, MolitApiResponse.Item item) {
        AptTrade trade = new AptTrade();
        trade.setSggCd(sggCd);
        trade.setDongName(trim(item.getDong()));
        trade.setAptName(trim(item.getAptName()));
        trade.setDealAmount(MolitApiResponse.parseDealAmount(item.getDealAmount()));
        trade.setDealYear(parseInt(item.getDealYear()));
        trade.setDealMonth(parseInt(item.getDealMonth()));
        trade.setDealDay(parseInt(item.getDealDay()));
        trade.setExclusiveArea(parseBigDecimal(item.getExcluUseAr()));
        trade.setFloor(parseInteger(item.getFloor()));
        trade.setBuiltYear(parseInteger(item.getBuiltYear()));
        trade.setCancelType(trim(item.getCancelType()));
        trade.setCancelDay(trim(item.getCancelDay()));
        trade.setDealingType(trim(item.getDealingType()));
        trade.setAptDong(trim(item.getAptDong()));
        trade.setSellerType(trim(item.getSellerType()));
        trade.setBuyerType(trim(item.getBuyerType()));
        trade.setLandLeasehold(trim(item.getLandLeasehold()));
        return trade;
    }

    private boolean existsTrade(AptTrade trade) {
        return aptTradeRepository.existsBySggCdAndAptNameAndDealYearAndDealMonthAndDealDayAndExclusiveAreaAndDealAmountAndFloor(
                trade.getSggCd(),
                trade.getAptName(),
                trade.getDealYear(),
                trade.getDealMonth(),
                trade.getDealDay(),
                trade.getExclusiveArea(),
                trade.getDealAmount(),
                trade.getFloor()
        );
    }

    private Integer parseInteger(String raw) {
        if (raw == null || raw.trim().isEmpty()) return null;
        return Integer.parseInt(raw.trim());
    }

    private int parseInt(String raw) {
        if (raw == null || raw.trim().isEmpty()) return 0;
        return Integer.parseInt(raw.trim());
    }

    private BigDecimal parseBigDecimal(String raw) {
        if (raw == null || raw.trim().isEmpty()) return null;
        return new BigDecimal(raw.trim());
    }

    private String trim(String raw) {
        return raw == null ? null : raw.trim();
    }
}
