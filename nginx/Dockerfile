FROM nginx:alpine

# Copy the built React app from the build stage
COPY --from=react-build /app/build /usr/share/nginx/html

# Copy the custom Nginx configuration file
COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 80 to be accessible externally
EXPOSE 80