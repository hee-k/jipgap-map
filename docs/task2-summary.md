# Task 2 작업 내역 (서울 행정경계 데이터 적재)

## 목표
서울 25개 자치구 Shapefile을 `sgg_boundary` 테이블에 적재한다.

---

## 사용 데이터
- 파일: `/home/ubuntu/jipgap-map/data/시군구.zip`
- 추출 경로: `/home/ubuntu/jipgap-map/data/sgg`
- 주요 파일: `N3A_G0100000.shp` (+ .shx/.dbf/.prj)

---

## 수행 단계

### 1) Shapefile 적재 (raw)
- `shp2pgsql` 사용하여 원본을 `sgg_boundary_raw` 테이블로 적재
- 좌표계 변환: **EPSG:5179 → 4326**
- 인코딩: **EUC-KR**

### 2) 정제 적재
- `sgg_boundary_raw` → `sgg_boundary`로 변환 삽입
  - `sgg_cd`: `bjcd` 앞 5자리
  - `sgg_kor_nm`: `name`
- 서울 자치구만 필터 (`sgg_cd LIKE '11%'`)
- `sido_nm = '서울특별시'` 설정
- `area_sqkm` 계산: `ST_Area(ST_Transform(geom, 5179)) / 1,000,000`

---

## 검증 결과
- `sgg_boundary` 총 레코드: **25개**
- `sgg_cd`, `sgg_kor_nm`, `sido_nm` 정상 확인

---

## 비고
- 원본 적재 테이블: `sgg_boundary_raw`
- 최종 테이블: `sgg_boundary`
