# Gestión de Usuarios y CRUDL de Edificios — Spring Boot, Arquitectura Hexagonal, DDD y CQRS

Aplicación backend desarrollada con **Java 17** y **Spring Boot 3.3.5** siguiendo **Domain-Driven Design (DDD)**, **Arquitectura Hexagonal (Ports & Adapters)**, **CQRS**, principios **SOLID** y **Clean Code**.

Incluye la entidad común **Usuario** (autenticación JWT y gestión de usuarios) y el CRUDL completo de la entidad **Edificio** (Ejercicio 13):
- `nombre`, `metrosCuadrados`, `altura`, `numPisos`, `numApartamentos`, `numOficinas`, `nombreParqueadero`, `numPiscinas`, `pais`, `departamento`, `ciudad`, `tieneAscensor`, `valorAdministracion`, `tieneZonaSocial`.

## Arquitectura por Capas

| Capa | Componentes de Edificio |
|---|---|
| **Domain** | `EdificioModel`, 11 Value Objects (`EdificioId`, `EdificioNombre`, `EdificioMetrosCuadrados`, `EdificioAltura`, `EdificioNumPisos`, `EdificioNumApartamentos`, `EdificioNumOficinas`, `EdificioNombreParqueadero`, `EdificioNumPiscinas`, `EdificioUbicacion`, `EdificioValorAdministracion`), excepciones (`InvalidEdificioIdException`, `InvalidEdificioFieldException`, `EdificioAlreadyExistsException`, `EdificioNotFoundException`) y eventos (`EdificioCreatedDomainEvent`, `EdificioUpdatedDomainEvent`, `EdificioDeletedDomainEvent`) |
| **Application** | 5 puertos de entrada (`CreateEdificioUseCase`, `UpdateEdificioUseCase`, `GetEdificioByIdUseCase`, `GetAllEdificiosUseCase`, `DeleteEdificioUseCase`), 6 puertos de salida (`SaveEdificioPort`, `UpdateEdificioPort`, `GetEdificioByIdPort`, `GetEdificioByNombreAndCiudadPort`, `GetAllEdificiosPort`, `DeleteEdificioPort`), Commands y Queries CQRS, `EdificioApplicationMapper` y los 5 servicios de aplicación |
| **Infrastructure (Adapters)** | `EdificioRepositoryMySQL` (JDBC + `DataSource`), `EdificioPersistenceDto`, `EdificioEntity`, `EdificioPersistenceMapper` |
| **Infrastructure (Entrypoints)** | `EdificioRestController` (`/api/edificios`), `EdificioRestControllerDocs` (Swagger/OpenAPI), `CreateEdificioRestRequest`, `UpdateEdificioRestRequest`, `EdificioRestResponse`, `EdificioRestMapper`, `GlobalExceptionHandler` y `SecurityConfig` (JWT) |

### Reglas de Negocio en el Dominio
1. Unicidad de `nombre` + `ciudad`.
2. `numPisos >= 1`, `metrosCuadrados > 0`, `altura > 0`.
3. Todo edificio debe contar con al menos una unidad (`numApartamentos + numOficinas > 0`).
4. Altura consistente con el número de pisos (`altura / numPisos >= 2.0` metros por piso).

## Ejecución Local

1. Copiar `.env.example` a `.env` y configurar las credenciales de MySQL.
2. Ejecutar el script de base de datos:
   ```bash
   mysql -u root -p < src/main/resources/schema.sql
   ```
   *(Para bases de datos en la nube donde el esquema ya existe, ejecutar `src/main/resources/schema_tables.sql`)*.
3. Compilar, ejecutar pruebas y levantar la aplicación:
   ```bash
   ./mvnw clean test
   ./mvnw spring-boot:run
   ```
   En Windows usar `mvnw.cmd`.
4. Documentación interactiva Swagger UI disponible en `http://localhost:8080/swagger-ui.html` y estado del servicio en `GET http://localhost:8080/health`.
   - Usuario administrador inicial: `admin@example.com` / `Admin1234!`.

## Docker y Docker Compose

```bash
docker compose up --build
```

Levanta MySQL 8.4 (inicializando `src/main/resources/schema.sql`) y la API Spring Boot en `http://localhost:8080`.

## Pruebas y Colección Postman

- **Pruebas unitarias y de controladores (JUnit 5 + Mockito + MockMvc):**
  ```bash
  ./mvnw clean verify
  ```
- **Postman:** importar `postman/Edificios.postman_collection.json`. Ejecutar primero **Login Admin (Obtiene JWT)** para almacenar automáticamente el token en `{{jwt_token}}` y luego probar los endpoints CRUDL de `/api/edificios`.

## Despliegue en la Nube y CI/CD

- **Render:** archivo Blueprint `render.yaml` listo para desplegar con Docker y conectar a MySQL remoto con `DB_SSL_MODE=REQUIRED`.
- **GitHub Actions:** flujo `.github/workflows/ci.yml` que compila con JDK 17, ejecuta todas las pruebas, construye la imagen Docker y notifica el deploy hook de Render.
