# Task 1 작업계획 - 프로젝트 환경 구성

## 목표
Docker Compose + Spring Boot + React 로컬 개발 환경을 구성한다.

## 작업 범위
- `docker-compose.yml` 작성
- `.env.example` 작성 및 `.env` 생성 가이드
- DB 컨테이너 실행 확인
- DDL 실행 (테이블 2개 + MV 1개)
- Spring Boot 기본 설정 (Gradle, application.yml, Health/CORS/Swagger)
- React(Vite) 기본 설정 (의존성, proxy, axios)

---

## 세부 작업 목록 (TDD 흐름 포함)

### 1) Docker Compose & 환경변수
- [ ] `docker-compose.yml` 작성 (db/backend/frontend)
- [ ] `.env.example` 작성 (DB_USER/DB_PASSWORD/MOLIT_API_KEY)
- [ ] `.env` 생성 및 `.gitignore` 확인
- [ ] `docker compose up -d db` 실행 후 접속 확인

### 2) DB 스키마(DDL)
- [ ] `sgg_boundary` 테이블 생성
- [ ] `apt_trade` 테이블 생성
- [ ] `mv_sgg_avg_price` Materialized View 생성
- [ ] 인덱스 생성 확인

### 3) Backend (Spring Boot)
- [ ] `backend/` Gradle 프로젝트 확인/생성
- [ ] `build.gradle` 의존성 추가
- [ ] `application.yml` (local/prod 분리)
- [ ] `CorsConfig.java` (localhost:5173 허용)
- [ ] `SwaggerConfig.java` (그룹 설정)
- [ ] `HealthController.java` (`GET /api/health`)

### 4) Frontend (React + Vite)
- [ ] `frontend/` Vite 프로젝트 확인/생성
- [ ] `ol`, `axios` 설치
- [ ] `vite.config.js` 프록시 (`/api` → `http://localhost:8080`)
- [ ] `src/utils/api.js` Axios 인스턴스
- [ ] `npm run dev` 실행 확인

---

## 테스트/검증 체크리스트
- [ ] `docker compose up -d` 후 DB 정상 실행
- [ ] `GET /api/health` → `{"status":"UP"}`
- [ ] Swagger UI `/api/swagger-ui.html` 접속
- [ ] React `http://localhost:5173` 접속

---

## 예상 파일 변경
- `docker-compose.yml`
- `.env.example`
- `backend/` (Gradle, config, controller)
- `frontend/` (Vite config, api util)

---

## 진행 방식
1) Docker/DB부터 세팅
2) Backend 기본 구동
3) Frontend 구동 확인
4) 최종 통합 확인
