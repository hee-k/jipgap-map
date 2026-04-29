package com.jipgap.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sgg_boundary")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SggBoundary {

    @Id
    @Column(name = "sgg_cd", length = 5)
    private String sggCd;

    @Column(name = "sgg_kor_nm")
    private String sggKorNm;

    @Column(name = "sido_nm")
    private String sidoNm;
}
