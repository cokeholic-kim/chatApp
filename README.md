# chatApp

## 제작목적 

- 웹소켓을 이용한 채팅 서비스. 

## 필요한 기능

- 회원가입: 이메일 아이디, 비밀번호
- 로그인 시 계정명, 사진 프로필 세팅
- 친구 목록: 아이디로 추가
- 대상자 클릭시 채팅방 생성되어 채팅
- 1:1 채팅 기능만 구현

### 진행상황

- 회원가입 , 로그인 기능 구현 
  - SpringSecurity , Jwt를 사용하여 구현 
  - 회원가입시 email,password,role 정보 필요 
  - role [ROLE_USER, ROLE_ADMIN]
  - 로그인하면 헤더에 jwt토큰을 실어서 보내준다.
- 친구등록, 삭제 기능 구현
  - 친구등록시 등록할 이메일주소를 requestedEmail 로 보내주면 등록가능
  - 삭제기능도 동일하게 삭제할 이메일주소를 보내주면 삭제가능
  - 이미 등록한 이메일의 경우는 등록 안되게 예외처리 완료.
  - 없는 이메일을 삭제하는경우도 예외발생.

- 회원탈퇴, 친구 전체 조회 , 친구 상세조회 기능 구현 필요.