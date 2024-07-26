# Stage 1: Build
FROM node:16 AS build

# 작업 디렉토리 설정
WORKDIR /app

# package.json과 package-lock.json 복사
COPY package*.json ./

# 의존성 설치
RUN npm install

# 소스 복사
COPY . .

# 빌드
RUN npm run build

# Stage 2: Serve
FROM nginx:alpine

# Nginx 설정 파일 복사
COPY nginx/nginx.conf /etc/nginx/nginx.conf

# Build 스테이지에서 생성된 결과물 복사
COPY --from=build /app/build /usr/share/nginx/html

# 컨테이너가 시작될 때 Nginx 실행
CMD ["nginx", "-g", "daemon off;"]