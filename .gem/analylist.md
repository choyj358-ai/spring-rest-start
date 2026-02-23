# Spring REST API Project Analysis (spring-rest-start)

이 문서는 `spring-rest-start` 프로젝트의 아키텍처, 기술 스택, 핵심 구현 전략 및 현재 개발 상태에 대한 분석 보고서입니다.

## 1. 프로젝트 개요
- **기술 스택**: Java 21, Spring Boot 3.5.5, Spring Data JPA, Spring Security, H2 Database, Lombok, JWT (java-jwt 4.3.0).
- **목적**: RESTful API 기반의 게시판 서비스를 위한 백엔드 기초 설계.
- **특징**: Stateless JWT 인증 방식을 채택하고 있으며, `_core` 패키지를 통해 공통 관심사(보안, 예외 처리, 유틸리티)를 중앙 집중식으로 관리.

## 2. 핵심 아키텍처 및 공통 설계 (`_core`)

### 2.1 보안 전략 (Security & JWT)
- **Stateless Session**: 세션을 사용하지 않고 JWT 토큰을 통한 무상태 인증을 수행.
- **SecurityConfig**: CSRF, FormLogin, HttpBasic을 비활성화하고 `JwtAuthorizationFilter`를 `UsernamePasswordAuthenticationFilter` 이전에 배치하여 모든 요청에 대한 토큰 검증을 수행.
- **JwtProvider & JwtUtil**: JWT의 생성, 추출, 검증 및 Spring Security `Authentication` 객체 생성 로직을 캡슐화.
- **권한 관리**: `/api/**` 경로는 인증이 필요하며, `/admin/**`은 `ADMIN` 권한 필요. 그 외 경로는 허용.

### 2.2 예외 및 유효성 검사 처리
- **GlobalExceptionHandler**: `Exception400`~`500` 등 커스텀 예외를 처리하며, 모든 에러 응답을 `Resp` 클래스로 규격화하여 반환.
- **GlobalValidationHandler (AOP)**: `@PostMapping` 및 `@PutMapping` 메서드 실행 전 `Errors` 객체를 검사하여 유효성 실패 시 자동으로 `Exception400`을 던지는 공통 로직 구현.
- **공통 응답 (Resp)**: `status`, `msg`, `body` 구조의 일관된 응답 포맷을 사용하여 프론트엔드와의 통신 효율성 증대.

## 3. 도메인 분석 및 구현 현황

### 3.1 회원 관리 (Auth & User)
- **AuthService**: 회원가입 시 BCrypt를 통한 비밀번호 암호화 및 로그인 시 JWT 발급 로직 구현 완료.
- **User Entity**: `UserDetails`를 구현하여 Spring Security와 통합. `roles` 필드를 통해 다중 권한 지원 가능 (쉼표 구분).
- **UserRepository**: `findByUsername` 쿼리 메서드 구현 완료.

### 3.2 게시판 및 댓글 (Board & Reply)
- **Board Entity**: `User`와 다대일(`ManyToOne`), `Reply`와 일대다(`OneToMany`) 관계 매핑 완료. 작성자 삭제 시 댓글도 연쇄 삭제(`CascadeType.REMOVE`)되도록 설정.
- **현황**: `BoardController`, `BoardService`, `BoardRepository` 및 `Reply` 관련 클래스들이 생성되어 있으나 실제 비즈니스 로직은 대부분 비어 있는 스켈레톤 상태임.

### 3.3 관리자 (Admin)
- 관리자 관련 API 구조는 잡혀 있으나 구체적인 관리 기능(사용자 관리, 게시글 강제 삭제 등)은 미구현 상태.

## 4. 데이터베이스 설계
- `data.sql`을 통해 초기 데이터(테스트용 사용자, 게시글 등)를 로드할 수 있도록 설정되어 있음.
- JPA의 `CreationTimestamp`를 사용하여 모든 엔티티의 생성 시각을 자동 관리.

## 5. 종합 의견 및 제언
1. **아키텍처 완성도**: `_core`를 통한 인프라 스트럭처 설계가 매우 견고하며, 공통 처리 로직(AOP, ExceptionHandler)이 잘 구축되어 있음.
2. **구현 과제**: 현재 회원가입/로그인 외의 핵심 비즈니스 로직(게시글 CRUD, 댓글 작성 등)이 비어 있으므로, `BoardService`와 `ReplyService`를 중심으로 기능을 확장해야 함.
3. **테스트 코드**: `src/test` 경로에 테스트 클래스가 존재하지 않음. `SpringRestDocs` 의존성이 설정되어 있으므로, API 명세 자동화를 위한 MockMvc 테스트 코드 작성이 권장됨.
