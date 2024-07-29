# Stage 1: Build
FROM node:18 AS build
WORKDIR /front

COPY package*.json .
RUN npm install

COPY . .
RUN npm run build

# Stage 2: Serve
FROM nginx:alpine
WORKDIR /front
COPY --from=build /front/build /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]<build_ima