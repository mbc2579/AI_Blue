FROM openjdk:17-alpine AS builder

# bash 설치
RUN apk add --no-cache bash

# 빌드 컨텍스트의 gradlew 파일을 복사
COPY gradlew /app/gradlew
COPY gradle /app/gradle
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle
COPY src /app/src

# 작업 디렉토리 설정
WORKDIR /app

RUN chmod +x ./gradlew && ls -l ./gradlew

# 윈도우 환경에서 \r 들어간 부분 삭제
RUN sed -i 's/\r$//' ./gradlew

RUN /bin/bash ./gradlew bootJar

FROM openjdk:17-slim

COPY --from=builder /app/build/libs/*.jar blue_auth.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","/blue_auth.jar"]