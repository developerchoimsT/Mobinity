# 공예품 판매 B2C 사이트 #
---------------------------
사이트 : http://hoopi.p-e.kr/
관리자 : admin , 4gkrsus1qks!
회원 : ms123 , ms123ms123!
## 메인
### 로그인하지 않은 경우
- 가격 관련 정보는 상품 목록 응답에 표시되지 않습니다.
### 로그인한 경우
- 사용자의 유형에 따라 설정된 금액이 상품 목록에 표시됩니다.(관리자: 0, 회원: 정가, 로그인x: 가격확인 필요)
- 만약 사용자 유형에 맞는 금액 정보가 없을 경우, 상품의 기본 금액이 표시됩니다.
## 로그인
### 회원가입 API
- 사용자가 회원가입을 할 수 있는 기능을 구현합니다.
- 비밀번호는 암호화되어있어야합니다.
### 로그인 API
- 사용자가 로그인할 수 있는 기능을 구현합니다.
- 아이디/비번이 다를 때 다른 예외처리를 해야합니다
## Pagination
- 관리자 회원 관리 페이지 적용
## 검색
- 관리자 회원 관리 페이지 검색 기능


## 사용 skill ##
<div align='center'>
  <img src="https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=IntelliJ-&logoColor=white">
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot-&logoColor=white">
  <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=SpringSecurity-&logoColor=white">
  <img src="https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=JPA-&logoColor=white">
  <img src="https://img.shields.io/badge/Java-4B4B77?style=for-the-badge&logo=Java-&logoColor=white">
  <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MySql-&logoColor=white">
  <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white">
  <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub-&logoColor=white">
  <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white">
  <img src="https://img.shields.io/badge/AWS S3-569A31?style=for-the-badge&logo=AWS S3&logoColor=white">
  <img src="https://img.shields.io/badge/AWS RDS-527FFF?style=for-the-badge&logo=AWS RDS&logoColor=white">
  <img src="https://img.shields.io/badge/AWS EC2-FF9900?style=for-the-badge&logo=AWS EC2&logoColor=white">
</div>


## 공통 구현 ##
- [x] JWT Token 인증(cookie, redis 이용)
- [x] 아이디/비밀번호 exception 다르게 처리
- [x] 동적 배너 및 검색 카테고리 구현
- [ ] 장기 휴면 고객 처리


## 회원 페이지 구현 ##
### 메인 페이지 
- [x] 신상품 컨테이너
- [x] 인기상품 컨테이너

### 회원 페이지
- [ ] 회원 정보 메인 페이지
- [ ] 회원 정보 수정 페이지(핸드폰 번호, 이메일 수정)
- [ ] 배송지 수정 및 추가 삭제 페이지
- [ ] 주문 내역 확인 페이지
- [ ] 상제 주문 내역 확인 페이지
- [ ] 탈퇴 버튼
      
### 상품 페이지
- [ ] 상품 메인
- [ ] 상품 디테일

### 장바구니
- [ ] 장바구니 메인

### 주문
- [ ] 주문 메인
- [ ] 결제창
- [ ] 결제 완료 or 실패 페이지

### 공지
- [ ] 공지 메인
- [ ] 공지 상세


## 관리자 페이지 구현 ##
### 회원 관리 페이지
- [x] 회원 페이지
- [x] 회원 디테일 페이지
- [x] 회원 탈퇴 처리

### 상품 관리 페이지
- [ ] 상품 메인(회원 동일)
- [ ] 상품 디테일(회원 동일)
- [x] 상품 추가 페이지
- [ ] 상품 삭제, 수정 버튼

### 주문 관리 페이지
- [ ] 주문 메인
- [ ] 주문 상세
- [ ] 주문 상태 변경

### 공지 페이지
- [ ] 공지 메인(회원 동일)
- [ ] 공지 상세(회원 동일)
- [x] 공지 추가 페이지
- [ ] 공지 삭제, 수정 버튼
