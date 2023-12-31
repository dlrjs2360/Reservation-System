# Reservation-System (개발 중)

Numble 딥다이브 챌린지를 위해 개발한 대용량 처리 예매 시스템 API 서버

## 프로젝트 설명

| 분류 | 내용 |
| --- | --- |
| 주제 | 대용량 처리 예매 시스템 백엔드 API 서버 개발 |
| 인원 | 1 |
| 기간 | 2023.06.12 ~ 2023.06.25 |


## 사용 기술 및 스택

| 스택 | 버전 |
| --- | --- |
| JAVA | 11 |
| SpringBoot | 2.x.x |
| MySQL | x.x.x |


## ERD (수정 중)

![스크린샷 2023-06-25 오후 1 17 20](https://github.com/dlrjs2360/Reservation-System/assets/81156109/1aed4119-cbda-413b-b2da-f8c5ff638590)


## API 명세서

| 분류 | 서비스 | 컨트롤러 | 권한 | 내용 | 태그 | API  | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 회원 | 완료 | Done | 사용자,사업자 | 회원가입 | POST | / member / signup |  |
|  | 완료 | Done | 사용자,사업자 | 로그인 | POST | / member / login | - swagger에서 테스트를 간편히 하기 위해 토큰만 전달하는 것으로 변경, Login DTO 필요 |
|  | 완료 |  | 사용자,사업자 | 회원 삭제 | DELETE | / member / {id} |  |
|  | 완료 |  | 사용자,사업자 | 회원 정보 수정 | PATCH | / member |  |
|  | 완료 | Done | 사용자 | 회원 정보 조회 | GET | / member / {id} |  |
|  | 완료 | Done | 관리자 | 모든 회원 정보 조회 | GET | / member / all | - 권한 처리 필요 |
|  |  |  |  |  |  |  |  |
| 공연 | 완료 | Done | 사용자,사업자 | 공연 조회 | GET | / show / {id} |  |
|  | 완료 | Done | 사용자,사업자 | 예매 가능한 공연 목록 조회 | GET | / show / list |  |
|  | 완료 | Done | 사업자 | 공연 등록 | POST | / show |  |
|  | 완료 | Done | 사업자 | 공연 삭제 | DELETE | / show / {id} | - 수정과 함께 구현하였는데 따로 구현하는 것으로 변경 필요 |
|  | 완료 | Done | 사업자 | 공연 수정 | PATCH | / show |  |
|  |  |  |  |  |  |  |  |
| 좌석 | 완료 | Done | 사용자,사업자 | 좌석 정보 조회 | GET | / seat / {id} |  |
|  |  |  | 사용자,사업자 | 상태별 좌석 조회 | GET | / seat / {concertId} / {status} |  |
|  | 완료 |  | 사업자 | 예매 좌석 수정 | PATCH | / seat |  |
|  | 완료 | Done | 사업자 | 좌석 등록 | POST | / seat |  |
|  | 완료 | Done | 사용자,사업자,관리자 | 공연별 모든 좌석 조회 | GET | / seat / {concerId} / all |  |
|  |  |  |  |  |  |  |  |
| 예매 | 완료 | Done | 사용자 | 공연 예매 | POST | / book |  |
|  | 완료 | Done | 사용자 | 예매 취소 | DELETE | / book |  |
|  | 완료 | Done | 사용자 | 예매 내역 상세 조회 | GET | / book / {ticketid} |  |
|  | 완료 | Done | 사용자 | 예매 내역 목록 조회 | GET | / book / list |  |
|  |  |  | 사업자 | 쿠폰 발급 | POST | / book / coupon |  |
|  |  |  |  |  |  |  |  |
| 결제 |  |  | 사용자 | 포인트 충전 | POST | / point / filling |  |
|  |  |  | 사용자 | 포인트 인출 | PATCH | / point / withdrawal |  |
|  |  |  | 사용자 | 공연 좌석 결제 | PATCH | / point / payment |  |
|  |  |  | 사업자 | 예매 환불 | PATCH | / point / refund |  |

## 기능

- 공연 정보 조회, 관리
    - 사업자
        - 공연 조회
        - 공연 등록
            - 예매 가능 횟수 지정
        - 공연 삭제
            - 이미 사용자가 예매한 공연 삭제 불가능
            - 또는 삭제 시 환불 처리
        - 공연 수정
            - 사용자가 예매한 공연도 수정 가능
    - 사용자
        - 공연 조회
        - 공연 예매
            - 이미 예매한 공연도 예매 가능
        - 예매 공연 취소
    

- 예매 좌석 관리 기능
    - 사업자
        - 좌석 활성화 변경 가능
        - 이미 예매된 좌석은 변경 불가능
    - 사용자
        - 예매한 공연 좌석 변경 불가능
        - 좌석을 바꾸기 위해서는 다시 예매
        
- 회원 관리
    - 사용자
        - 로그인
        - 회원가입
            - 이메일 중복 검사
        - 회원정보 수정
        - 회원 삭제
    - 사업자
        - 로그인
        - 회원가입
        - 사업자정보 수정
        - 사업자 삭제

- 결제
    - 한 번 예매하고 공연날짜가 지나면 할인쿠폰 발급
    - 할인쿠폰은 기한이 지나면 사용 불가능
    
- 공연 목록 조회 기능(홈 노출/베스트/기타)
    - 예매 가능한 공연 선택
    - 날짜 순 정렬
    - 인기순 정렬(예매좌석 순)
    

## 확장성

- 포인트 사용 내역
- 마일리지 기능
- 좌석 지정 등록
- 공연 등록시 같은 장소, 같은 시간 불가능
- 좀 더 유연한 쿠폰 기능
    - 쿠폰 유효기간
    - 예매 취소시 쿠폰 삭제
    - 쿠폰 발급날짜 확인
