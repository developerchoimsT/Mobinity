# 베이스 이미지
FROM node:16

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

# Nginx 설정
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx/nginx.conf /etc/nginx/nginx.conf

# 컨테이너가 시작될 때 Nginx 실행
CMD ["nginx", "-g", "daemon off;"]