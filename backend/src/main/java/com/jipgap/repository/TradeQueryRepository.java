package com.jipgap.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TradeQueryRepository {

    private final EntityManager em;

    public List<TradeMapRow> fetchTradeMap(int year, int month) {
        String sql = """
            SELECT
                sgg_cd            AS sggCd,
                sgg_kor_nm        AS sggKorNm,
                sido_nm           AS sidoNm,
                avg_price         AS avgPrice,
                avg_price_per_sqm AS avgPricePerSqm,
                trade_count       AS tradeCount,
                ST_AsGeoJSON(ST_SimplifyPreserveTopology(geom, 0.001)) AS geojson
            FROM mv_sgg_avg_price
            WHERE deal_year = :year AND deal_month = :month
            """;

        List<Tuple> rows = em.createNativeQuery(sql, Tuple.class)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();
        return rows.stream()
                .map(t -> new TradeMapRow(
                        t.get("sggCd", String.class),
                        t.get("sggKorNm", String.class),
                        t.get("sidoNm", String.class),
                        toLong(t.get("avgPrice")),
                        toLong(t.get("avgPricePerSqm")),
                        toLong(t.get("tradeCount")),
                        t.get("geojson", String.class)
                ))
                .toList();
    }

    public Optional<SummaryRow> fetchSummary(String sggCd, int year, int month) {
        String sql = """
            SELECT
                ROUND(AVG(deal_amount)) AS avgPrice,
                MAX(deal_amount)        AS maxPrice,
                MIN(deal_amount)        AS minPrice,
                COUNT(*)                AS tradeCount
            FROM apt_trade
            WHERE sgg_cd = :sggCd
              AND deal_year = :year
              AND deal_month = :month
              AND (cancel_type IS NULL OR cancel_type = '')
            """;

        Tuple row = (Tuple) em.createNativeQuery(sql, Tuple.class)
                .setParameter("sggCd", sggCd)
                .setParameter("year", year)
                .setParameter("month", month)
                .getSingleResult();

        Long count = toLong(row.get("tradeCount"));
        if (count == null || count == 0L) {
            return Optional.empty();
        }
        return Optional.of(new SummaryRow(
                toLong(row.get("avgPrice")),
                toLong(row.get("maxPrice")),
                toLong(row.get("minPrice")),
                count
        ));
    }

    public List<AptRow> fetchAptDetails(String sggCd, int year, int month) {
        String sql = """
            SELECT
                apt_name                AS aptName,
                ROUND(AVG(deal_amount)) AS avgPrice,
                MAX(deal_amount)        AS maxPrice,
                MIN(deal_amount)        AS minPrice,
                COUNT(*)                AS tradeCount
            FROM apt_trade
            WHERE sgg_cd = :sggCd
              AND deal_year = :year
              AND deal_month = :month
              AND (cancel_type IS NULL OR cancel_type = '')
            GROUP BY apt_name
            ORDER BY avgPrice DESC
            """;

        List<Tuple> rows = em.createNativeQuery(sql, Tuple.class)
                .setParameter("sggCd", sggCd)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();

        return rows.stream()
                .map(t -> new AptRow(
                        t.get("aptName", String.class),
                        toLong(t.get("avgPrice")),
                        toLong(t.get("maxPrice")),
                        toLong(t.get("minPrice")),
                        toLong(t.get("tradeCount"))
                ))
                .toList();
    }

    public List<PeriodRow> fetchPeriods() {
        String sql = """
            SELECT DISTINCT deal_year AS year, deal_month AS month
            FROM mv_sgg_avg_price
            ORDER BY deal_year DESC, deal_month DESC
            """;

        List<Tuple> rows = em.createNativeQuery(sql, Tuple.class).getResultList();
        return rows.stream()
                .map(t -> new PeriodRow(
                        ((Number) t.get("year")).intValue(),
                        ((Number) t.get("month")).intValue()
                ))
                .toList();
    }

    private static Long toLong(Object value) {
        return value == null ? null : ((Number) value).longValue();
    }

    public record TradeMapRow(
            String sggCd,
            String sggKorNm,
            String sidoNm,
            Long avgPrice,
            Long avgPricePerSqm,
            Long tradeCount,
            String geojson
    ) {}

    public record SummaryRow(Long avgPrice, Long maxPrice, Long minPrice, Long tradeCount) {}

    public record AptRow(String aptName, Long avgPrice, Long maxPrice, Long minPrice, Long tradeCount) {}

    public record PeriodRow(int year, int month) {}
}
