# Etapa de construcción
FROM node:20 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Etapa de producción
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80


# También podés usar vite en lugar de create-react-app, pero el enfoque es igual.