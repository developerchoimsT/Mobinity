# Build stage
FROM node:14 AS build
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

# Nginx stage
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY ../nginx/nginx.conf /etc/nginx/nginx.conf