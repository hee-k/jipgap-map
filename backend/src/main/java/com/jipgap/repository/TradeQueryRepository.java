package com.jipgap.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TradeQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public TradeQueryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> fetchTradeMap(int year, int month) {
        String sql = """
            SELECT
                sgg_cd AS sggCd,
                sgg_kor_nm AS sggKorNm,
                sido_nm AS sidoNm,
                avg_price AS avgPrice,
                avg_price_per_sqm AS avgPricePerSqm,
                trade_count AS tradeCount,
                ST_AsGeoJSON(ST_SimplifyPreserveTopology(geom, 0.001)) AS geojson
            FROM mv_sgg_avg_price
            WHERE deal_year = ? AND deal_month = ?
        """;
        return jdbcTemplate.queryForList(sql, year, month);
    }

    public Optional<SummaryRow> fetchSummary(String sggCd, int year, int month) {
        String sql = """
            SELECT
                ROUND(AVG(deal_amount)) AS avgPrice,
                MAX(deal_amount) AS maxPrice,
                MIN(deal_amount) AS minPrice,
                COUNT(*) AS tradeCount
            FROM apt_trade
            WHERE sgg_cd = ?
              AND deal_year = ?
              AND deal_month = ?
              AND (cancel_type IS NULL OR cancel_type = '')
        """;
        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }
            long count = rs.getLong("tradeCount");
            if (count == 0) {
                return Optional.empty();
            }
            SummaryRow row = new SummaryRow(
                    rs.getLong("avgPrice"),
                    rs.getLong("maxPrice"),
                    rs.getLong("minPrice"),
                    count
            );
            return Optional.of(row);
        }, sggCd, year, month);
    }

    public List<AptRow> fetchAptDetails(String sggCd, int year, int month) {
        String sql = """
            SELECT
                apt_name AS aptName,
                ROUND(AVG(deal_amount)) AS avgPrice,
                MAX(deal_amount) AS maxPrice,
                MIN(deal_amount) AS minPrice,
                COUNT(*) AS tradeCount
            FROM apt_trade
            WHERE sgg_cd = ?
              AND deal_year = ?
              AND deal_month = ?
              AND (cancel_type IS NULL OR cancel_type = '')
            GROUP BY apt_name
            ORDER BY avgPrice DESC
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new AptRow(
                rs.getString("aptName"),
                rs.getLong("avgPrice"),
                rs.getLong("maxPrice"),
                rs.getLong("minPrice"),
                rs.getLong("tradeCount")
        ), sggCd, year, month);
    }

    public List<Map<String, Object>> fetchPeriods() {
        String sql = """
            SELECT DISTINCT deal_year AS year, deal_month AS month
            FROM mv_sgg_avg_price
            ORDER BY deal_year DESC, deal_month DESC
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public record SummaryRow(Long avgPrice, Long maxPrice, Long minPrice, Long tradeCount) {
    }

    public record AptRow(String aptName, Long avgPrice, Long maxPrice, Long minPrice, Long tradeCount) {
    }
}
