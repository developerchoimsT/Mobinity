# Stage 1: Build React app
FROM node:18 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Output the build files to a folder
RUN mkdir -p /app/build && cp -r /app/build /app/build

# No need to expose a port here, as this is just for building the app