# Stage 1: Build React app
FROM node:alpine as build
WORKDIR /app
COPY package.json .
RUN npm install
COPY ./ ./
RUN npm run build

# Stage 2: Serve with Nginx
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html