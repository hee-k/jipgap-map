# Task 1 작업 내역 (프로젝트 환경 구성)

## 목표
Docker Compose + Spring Boot + React 로컬 개발 환경을 구성한다.

---

## 완료 항목

### 1) Docker / 환경변수
- `docker-compose.yml` 작성 (db/backend/frontend)
- `.env.example` 작성
- DB 컨테이너 구동 확인

### 2) Backend (Spring Boot)
- Spring Boot 프로젝트 스캐폴딩 생성
- Java 21 기준 설정 (toolchain 21)
- JPA 전환 완료 (MyBatis 제거)
- `application.yml` (local/prod 분리)
- `CorsConfig` (localhost:5173 허용)
- `SwaggerConfig` (API 그룹 설정)
- `HealthController` (`GET /api/health`)
- 테스트용 H2 설정 추가

### 3) Frontend (React + Vite)
- Vite React 스캐폴딩 생성
- `ol`, `axios` 의존성 추가
- `vite.config.js` 프록시 설정 (`/api` → `http://localhost:8080`)
- `src/utils/api.js` 작성

### 4) 실행/검증
- Docker 설치 및 DB 컨테이너 기동
- Java 21 설치/적용
- Backend 테스트 통과 (`./gradlew test`)

---

## 주요 파일
- `docker-compose.yml`
- `.env.example`
- `backend/build.gradle`
- `backend/src/main/resources/application.yml`
- `backend/src/main/java/com/jipgap/config/CorsConfig.java`
- `backend/src/main/java/com/jipgap/config/SwaggerConfig.java`
- `backend/src/main/java/com/jipgap/controller/HealthController.java`
- `backend/src/test/resources/application.yml`
- `frontend/package.json`
- `frontend/vite.config.js`
- `frontend/src/utils/api.js`

---

## 검증 결과
- Backend 테스트: **PASS**
- Java 버전: **21**
- DB 컨테이너: **RUNNING**

---

## 비고
- JPA 전환에 맞춰 문서에서 Java 버전 및 ORM 관련 내용 업데이트 완료
