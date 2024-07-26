# Stage 1: Build
FROM node:16 AS build

WORKDIR /app

COPY hoopifront/package*.json ./
RUN npm install

COPY hoopifront/ .
RUN npm run build

# Stage 2: Serve
FROM nginx:alpine

COPY hoopifront/nginx/nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/build /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]