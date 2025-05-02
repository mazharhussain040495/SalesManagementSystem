# Stage 1: Build Angular 19 App
FROM node:18 AS builder
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --configuration=production

# Stage 2: Serve with Nginx
FROM nginx:alpine
COPY --from=builder /app/dist/sales-application-frontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
