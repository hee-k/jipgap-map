package com.jipgap.repository;

import com.jipgap.domain.SggBoundary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SggBoundaryRepository extends JpaRepository<SggBoundary, String> {

    @Query("SELECT s.sggCd FROM SggBoundary s ORDER BY s.sggCd")
    List<String> findAllSggCodes();
}
