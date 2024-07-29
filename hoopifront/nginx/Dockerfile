# Nginx 베이스 이미지 사용
FROM nginx:alpine

# 리액트 애플리케이션 빌드 파일을 Nginx의 HTML 디렉토리에 복사
COPY build /usr/share/nginx/html

# Nginx 포트 노출
EXPOSE 80