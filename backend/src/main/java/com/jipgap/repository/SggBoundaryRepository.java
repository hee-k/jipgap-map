package com.jipgap.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SggBoundaryRepository {

    private final JdbcTemplate jdbcTemplate;

    public SggBoundaryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> findAllSggCodes() {
        return jdbcTemplate.queryForList("SELECT sgg_cd FROM sgg_boundary ORDER BY sgg_cd", String.class);
    }
}
