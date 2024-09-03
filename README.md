# 스파르타 Java 단기 심화 과정 9조 Team Blue AI 검증 프로젝트
## 프로젝트 구성원
- [김규준](https://github.com/mbc2579)
- [송형근](https://github.com/lukeydokey)
- [박지민](https://github.com/MeGuuuun)
## 프로젝트 구성
- Eureka Server
  - 서비스 디스커버리 서버
- Gateway Server
  - Spring Cloud Gateway를 사용해 요청을 인증/인가 후 각 서비스로 요청을 전달하는 Gateway
- Service Server ( Auth를 제외한 Service API가 있는 서버 )
  - 배송지, 가게, 리뷰, 상품, 주문, 결제 도메인으로 나누어 구현한 서버
- Auth Server
  - 로그인, 회원가입 등 Auth 관련된 API를 구현한 서버

## 산출물
- [API 명세서](https://www.notion.so/teamsparta/67be017afeac40fe8fe2cff81a6f1929?v=08007c9a00a448f181467b4f32af2c9d)
- [테이블 설계서](https://www.notion.so/teamsparta/0902_-1b4a3b53ae2b4b77a6f862d8e49b307b)
- [ERD 명세서](https://file.notion.so/f/f/83c75a39-3aba-4ba4-a792-7aefe4b07895/3e495b1a-b3c6-46a3-9796-29bc7dc5e35a/%E1%84%87%E1%85%B3%E1%86%AF%E1%84%85%E1%85%AE%E1%84%90%E1%85%B5%E1%86%B7_ERD_%E1%84%86%E1%85%A7%E1%86%BC%E1%84%89%E1%85%A6%E1%84%89%E1%85%A5.pdf?table=block&id=25a74aad-73e0-45bf-a663-06fef4a53e72&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&expirationTimestamp=1725415200000&signature=YXuYqM-A6Bnj44jVyLLjpxxIUu_PLRpAVG3k3srg5JQ&downloadName=%E1%84%87%E1%85%B3%E1%86%AF%E1%84%85%E1%85%AE%E1%84%90%E1%85%B5%E1%86%B7+ERD+%E1%84%86%E1%85%A7%E1%86%BC%E1%84%89%E1%85%A6%E1%84%89%E1%85%A5.pdf)
- [인프라 설계서](https://file.notion.so/f/f/83c75a39-3aba-4ba4-a792-7aefe4b07895/5e9c4d1f-f4ca-4ce4-aa55-3c87f0ad87f8/image.png?table=block&id=1377e40f-b041-4171-88e2-4ef99e283c76&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&expirationTimestamp=1725415200000&signature=edjVArinmS6TU2-LJ3xtjWH8iZKoTykI0i7PJ_0ebS4&downloadName=image.png)
