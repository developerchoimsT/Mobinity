# Stage 1: Build the React app
FROM node:18 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install --no-optional || (cat /root/.npm/_logs/*-debug.log && exit 1) && npm cache clean --force

# Copy the rest of the application code and build the app
COPY . .
RUN npm run build || (cat /root/.npm/_logs/*-debug.log && exit 1) && npm cache clean --force


# Stage 2: Serve the app with Nginx
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80