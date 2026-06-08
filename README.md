# Users API - Prueba Técnica Chakray

# API REST de gestión de usuarios desarrollada por Ricardo Zavala

## Tecnologias
- Java 21
- Spring Boot 3.5.14
- Maven
- Swagger / OpenAPI
- Docker


## PUERTOS
Swagger UI: http://localhost:8080/swagger-ui.html
OpenAPI JSON: http://localhost:8080/v3/api-docs


## ENDPOINTS Base URL: http://localhost:8080/ricky-api/users
Lista todos los usuarios
- GET	/ricky-api/users
Lista ordenada por campo (email, id, name, phone, tax_id, created_at)
- GET	/ricky-api/users?sortedBy=name
Lista filtrada (operadores: co, eq, sw, ew)
- GET	/ricky-api/users?filter=name+co+user
Nuevo usuario
- POST	/ricky-api/users
Update usuario por id
- PATCH	/ricky-api/users/{id}
Elimina un usuario por id
- DELETE	/ricky-api/users/{id}
Autenticacion (tax_id como usuario)
- POST	/ricky-api/users/login

Ejemplo crear usuario (POST)

{
  "name": "user1",
  "email": "user1@mail.com",
  "phone": "5512345678",
  "password": "miclave123",
  "taxId": "AARR990101XXX",
  "addres": [
    { 
     "id": 1, 
     "name": "workaddress",
     "street": "street No. 1", 
     "countryCode": "UK" }
  ]
}

Ejemplo login (POST)

{
  "taxId": "AARR990101XXX",
  "password": "miclave123"
}

Ejemplos de filtros
?filter=name+co+user → nombres que contienen "user"
?filter=email+ew+mail.com → emails que terminan en "mail.com"
?filter=phone+sw+555 → teléfonos que empiezan con "555"
?filter=tax_id+eq+AARR990101XXX → tax_id exactamente igual



## Requisitos
- JDK 21
- Maven 3.6.3 o >superior
- Docker



## ======= EJECUTAR =======

### Maven
bash:
./mvnw spring-boot:run
Docker:
./mvnw clean package -DskipTests
docker build -t users-api .
docker run -p 8080:8080 users-api


Notas técnicas
Las contraseñas se almacenan cifradas con AES256 y no se exponen en las respuestas.
El campo created_at se genera con la zona horaria de Madagascar (Indian/Antananarivo) en formato dd-MM-yyyy HH:mm.
Validaciones: tax_id con formato RFC y unico, phone de mínimo 10 dígitos.
Los datos se almacenan en memoria (se reinician al apagar la app).




