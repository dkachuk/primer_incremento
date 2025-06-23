# Etapa 1: build del frontend
FROM node:18 AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# Etapa 2: servir con Nginx
FROM nginx:stable-alpine

# Elimina el contenido por defecto de Nginx
RUN rm -rf /usr/share/nginx/html/*

# Copia la build del paso anterior
COPY --from=build /app/build /usr/share/nginx/html

# Opcional: reemplaza config default de Nginx si necesitás modificarlo
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expone el puerto 80
EXPOSE 80

# Comando por defecto
CMD ["nginx", "-g", "daemon off;"]