# Task 3 작업 내역 (국토교통부 API 연동 & 데이터 수집)

## 목표
국토교통부 아파트 실거래가 API를 호출하여 `apt_trade`에 적재하고, 집계 뷰를 갱신할 수 있는 백엔드 기능을 제공한다.

---

## 완료 항목

### 1) DB 스키마 준비
- `apt_trade` 테이블 생성
- `mv_sgg_avg_price` Materialized View 생성
- 인덱스 생성
  - `idx_apt_trade_sgg_cd`, `idx_apt_trade_deal`, `idx_apt_trade_cancel`
  - `idx_mv_sgg_avg_price_geom`

### 2) API 연동 서비스 구현
- MOLIT API 호출 및 XML 파싱
- 시군구 코드 기준 수집
- 중복 거래 방지 저장 로직

### 3) 관리자 API
- `POST /api/v1/admin/collect?year=&month=`
- `POST /api/v1/admin/refresh-view`

### 4) 테스트
- `TradeCollectServiceTest` (성공/무데이터 케이스)
- `AdminControllerTest`

---

## 주요 파일
- `backend/src/main/java/com/jipgap/domain/AptTrade.java`
- `backend/src/main/java/com/jipgap/dto/MolitApiResponse.java`
- `backend/src/main/java/com/jipgap/service/TradeCollectService.java`
- `backend/src/main/java/com/jipgap/controller/AdminController.java`
- `backend/src/main/java/com/jipgap/service/AdminService.java`
- `backend/src/main/java/com/jipgap/config/MolitApiProperties.java`
- `backend/src/main/java/com/jipgap/config/RestClientConfig.java`

---

## 실행 전 체크
- `.env`에 `MOLIT_API_KEY` 세팅 필요
- DB에 `sgg_boundary` 데이터 존재 필요

---

## 비고
- 테스트는 H2 기반으로 통과
- 데이터 수집 후 `refresh-view` 호출로 집계 갱신
