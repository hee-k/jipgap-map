package com.jipgap.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final EntityManager em;

    @Transactional
    public void refreshMaterializedView() {
        em.createNativeQuery("REFRESH MATERIALIZED VIEW mv_sgg_avg_price").executeUpdate();
    }
}
