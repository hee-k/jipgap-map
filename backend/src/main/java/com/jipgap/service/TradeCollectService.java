package com.jipgap.service;

import com.jipgap.domain.AptTrade;
import com.jipgap.dto.CollectResponse;
import com.jipgap.dto.MolitApiResponse;
import com.jipgap.repository.AptTradeRepository;
import com.jipgap.repository.SggBoundaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeCollectService {

    private static final String RESULT_CODE_OK = "00";
    private static final String RESULT_CODE_NO_DATA = "03";

    private final MolitApiClient molitApiClient;
    private final SggBoundaryRepository sggBoundaryRepository;
    private final AptTradeRepository aptTradeRepository;

    @Transactional
    public CollectResponse collect(int year, int month) {
        List<String> sggCodes = sggBoundaryRepository.findAllSggCodes();
        CollectResponse response = new CollectResponse(year, month);
        response.setTotalRequested(sggCodes.size());

        int inserted = 0;
        for (String sggCd : sggCodes) {
            inserted += collectSgg(sggCd, year, month, response);
        }
        response.setTotalInserted(inserted);
        return response;
    }

    private int collectSgg(String sggCd, int year, int month, CollectResponse response) {
        MolitApiResponse molit = molitApiClient.fetch(sggCd, year, month);
        String resultCode = Optional.ofNullable(molit.getHeader())
                .map(MolitApiResponse.Header::getResultCode)
                .orElse(null);

        if (RESULT_CODE_NO_DATA.equals(resultCode)) {
            return 0;
        }
        if (!RESULT_CODE_OK.equals(resultCode)) {
            response.addFailedSggCd(sggCd);
            return 0;
        }

        List<MolitApiResponse.Item> items = extractItems(molit);
        int inserted = 0;
        for (MolitApiResponse.Item item : items) {
            AptTrade trade = MolitItemMapper.toEntity(sggCd, item);
            if (!isDuplicate(trade)) {
                aptTradeRepository.save(trade);
                inserted++;
            }
        }
        return inserted;
    }

    private static List<MolitApiResponse.Item> extractItems(MolitApiResponse molit) {
        return Optional.ofNullable(molit.getBody())
                .map(MolitApiResponse.Body::getItems)
                .map(MolitApiResponse.Items::getItem)
                .orElseGet(List::of);
    }

    private boolean isDuplicate(AptTrade trade) {
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
}
