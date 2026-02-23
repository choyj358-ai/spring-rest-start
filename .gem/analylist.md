# 프로젝트 분석 보고서 (업데이트됨)

## 1. 프로젝트 개요

- **프로젝트명**: spring-rest-start
- **설명**: Spring Boot 기반의 REST API 서버. JWT 인증을 사용하며, 사용자, 게시판, 댓글 기능을 포함하는 것을 목표로 한다.
- **아키텍처**: 계층형 아키텍처 (Controller - Service - Repository)

## 2. 코어 아키텍처 (`_core` 패키지)

- **Security (`SecurityConfig.java`)**:
  - JWT 기반의 인증/인가를 사용 (`JwtAuthorizationFilter`).
  - `/api/**` 경로는 인증된 사용자만 접근 가능.
  - `/admin/**` 경로는 'ADMIN' 역할을 가진 사용자만 접근 가능.
  - CSRF, Form Login, HTTP Basic 인증은 비활성화됨.
- **CORS (`FilterConfig.java`, `CorsFilter.java`)**:
  - `FilterRegistrationBean`을 통해 커스텀 `CorsFilter`가 등록되어 모든 요청(`/*`)에 대해 CORS 헤더를 처리한다.
- **Exception Handling (`GlobalExceptionHandler.java`)**:
  - `Exception400`, `401`, `403`, `404`, `500` 등 커스텀 예외를 전역적으로 처리하여 일관된 오류 응답을 제공한다.
- **Standard Response (`Resp.java`)**:
  - `ResponseEntity`를 정적 메서드(`ok`, `fail`)로 래핑하여 표준화된 API 응답 형식을 유지한다.

## 3. 기능 구현 상태

| 기능    | 상태     | 설명                                                                                                                             |
| ------- | -------- | -------------------------------------------------------------------------------------------------------------------------------- |
| **Auth**  | **완료**   | 회원가입 (`/join`), 로그인 (`/login`) 기능이 구현됨. `AuthService`에서 비즈니스 로직을 처리한다.                               |
| **User**  | **완료**   | `User` 엔티티 및 `UserRepository`가 정의됨. 사용자 정보 조회 API (`GET /api/users/{id}`)가 구현됨.                             |
| **Board** | **미구현** | `Board` 엔티티는 정의되어 있으나, `BoardController`, `BoardService`, `BoardRepository`의 실제 로직은 비어있어 기능 개발이 필요하다. |
| **Reply** | **미구현** | `Reply` 엔티티는 정의되어 있으나, `ReplyController`, `ReplyService`, `ReplyRepository`의 실제 로직은 비어있어 기능 개발이 필요하다. |
| **Admin** | **최소**   | `ADMIN` 권한을 테스트하기 위한 간단한 엔드포인트 (`/admin/test`)만 존재한다.                                                     |

## 4. 데이터 모델 (Entities)

- **User**: 사용자 정보 (username, password, email, roles).
- **Board**: 게시글 정보 (title, content). `User`와 다대일 관계.
- **Reply**: 댓글 정보 (comment). `User`, `Board`와 각각 다대일 관계.

## 5. 향후 개발 제안

- **Board 기능 구현**: CRUD (생성, 읽기, 수정, 삭제) 기능 전체 개발.
- **Reply 기능 구현**: 특정 게시글에 대한 댓글 CRUD 기능 개발.
- **Repository 구체화**: `BoardRepository`와 `ReplyRepository`가 `JpaRepository`를 상속받도록 수정 및 필요한 쿼리 메서드 추가.
- **단위/통합 테스트**: 구현된 API에 대한 테스트 코드 작성.
