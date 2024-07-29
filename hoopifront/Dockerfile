# Stage 1: Build
FROM node:alpine as build
WORKDIR /front
COPY package.json .
RUN npm install
COPY ./ ./
RUN npm run build

# Stage 2: Serve with Nginx
FROM nginx:alpine
EXPOSE 80
COPY ../nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /front/build /usr/share/nginx/html