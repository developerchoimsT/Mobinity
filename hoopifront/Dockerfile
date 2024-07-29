# Stage 1: Build
FROM node:alpine AS build
WORKDIR /front
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Serve
FROM nginx:alpine
COPY nginx/nginx.conf /etc/nginx/nginx.conf
COPY --from=build /front/build /usr/share/nginx/html