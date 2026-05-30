# Backend KnockBet

Backend desarrollado con Spring Boot para gestionar el sistema de apuestas de KnockBet. El proyecto permite administrar peleadores, peleas, apuestas, presupuesto, retornos de pago, resultados y carga de imagenes de peleadores.

## Tecnologias

- Java 25
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- MySQL
- Lombok
- Thymeleaf
- Spring Mail
- Maven Wrapper

## Requisitos Previos

Antes de ejecutar el proyecto, asegurese de tener instalado:

- JDK 25
- MySQL Server
- Git
- Un cliente para probar la API, por ejemplo Postman o Insomnia

No es obligatorio instalar Maven globalmente, porque el proyecto incluye Maven Wrapper (`mvnw` y `mvnw.cmd`).

## Configuracion de Base de Datos

1. Inicie el servidor de MySQL.

2. Cree la base de datos:

```sql
CREATE DATABASE data_base_knockbet CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. Cree el archivo `src/main/resources/application.properties`.

Este archivo no se sube al repositorio porque contiene datos locales y credenciales. Use esta plantilla:

```properties
spring.application.name=backend-knockbet

spring.datasource.url=jdbc:mysql://127.0.0.1:3306/data_base_knockbet
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_DE_MYSQL
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=TU_CORREO@gmail.com
spring.mail.password=TU_APP_PASSWORD_DE_GMAIL
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

Notas:

- Cambie `TU_PASSWORD_DE_MYSQL` por la clave real de su usuario MySQL.
- Si su usuario de MySQL no es `root`, cambie `spring.datasource.username`.
- La configuracion de correo se usa para los flujos que envian emails. Para probar endpoints que no envian correos, puede dejar valores de prueba, pero los envios de email fallaran si las credenciales no son validas.
- `spring.jpa.hibernate.ddl-auto=update` permite que Hibernate cree o actualice las tablas automaticamente al iniciar la aplicacion.

## Instalacion y Ejecucion

1. Clone el repositorio:

```bash
git clone <URL_DEL_REPOSITORIO>
cd backend-knockbet
```

2. Verifique la version de Java:

```bash
java -version
```

Debe usar JDK 25, porque el proyecto esta configurado con:

```xml
<java.version>25</java.version>
```

3. Compile el proyecto e instale dependencias.

En Windows:

```powershell
.\mvnw.cmd clean install
```

En macOS/Linux:

```bash
./mvnw clean install
```

4. Ejecute la aplicacion.

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En macOS/Linux:

```bash
./mvnw spring-boot:run
```

5. Abra la API en:

```text
http://localhost:8080
```

El backend permite peticiones CORS desde:

```text
http://localhost:3000
```

## Ejecucion con JAR

Tambien puede construir un archivo `.jar`:

```powershell
.\mvnw.cmd clean package
```

Luego ejecutarlo:

```powershell
java -jar target/backend-knockbet-0.0.1-SNAPSHOT.jar
```

## Archivos Subidos

Las imagenes de peleadores se reciben como `multipart/form-data` usando `MultipartFile`.

Cuando se registra un peleador con imagen, el backend guarda el archivo en la carpeta:

```text
uploads/
```

Luego expone esos archivos publicamente desde:

```text
http://localhost:8080/uploads/<nombre-del-archivo>
```

El limite configurado para subida de archivos es de 10 MB.

## Endpoints Principales

| Modulo | Metodo | Ruta | Descripcion |
| --- | --- | --- | --- |
| Peleadores | GET | `/api/fighters` | Lista los peleadores |
| Peleadores | POST | `/api/fighters` | Registra un peleador con imagen mediante `multipart/form-data` |
| Peleadores | PATCH | `/api/fighters/{id}/status` | Activa o desactiva un peleador |
| Peleadores | PUT | `/api/fighters/edit` | Edita datos de un peleador |
| Peleas | GET | `/api/fight` | Lista las peleas |
| Peleas | GET | `/api/fight/free` | Lista peleas sin apuesta |
| Peleas | POST | `/api/fight` | Registra una pelea |
| Peleas | POST | `/api/fight/{fightId}/start` | Inicia una pelea |
| Peleas | POST | `/api/fight/result` | Registra el resultado de una pelea |
| Peleas | PUT | `/api/fight/edit` | Edita una pelea |
| Peleas | PATCH | `/api/fight/{id}/status` | Cancela o cambia el estado de una pelea |
| Apuestas | GET | `/api/bet` | Lista apuestas publicadas |
| Apuestas | POST | `/api/bet/{fightId}` | Publica una apuesta para una pelea |
| Apuestas | GET | `/api/bet/user` | Lista apuestas hechas por usuarios |
| Apuestas | POST | `/api/bet/stake` | Registra una apuesta de usuario |
| Presupuesto | GET | `/api/budget` | Consulta el presupuesto |
| Presupuesto | POST | `/api/budget` | Incrementa el presupuesto |
| Pagos | GET | `/api/pay` | Lista retornos |
| Pagos | GET | `/api/pay/bill` | Lista facturas |
| Pagos | POST | `/api/pay` | Paga un retorno de apuesta |

## Estructura del Proyecto

```text
src/main/java/com/knockbet/backend_knockbet
+-- Config          Configuracion CORS y recursos estaticos
+-- Controllers     Controladores REST
+-- Events          Eventos de negocio
+-- Exeptions       Manejador global de excepciones
+-- Models          Entidades, enums y DTOs
+-- Reglas          Reglas y metricas de negocio
+-- Repository      Repositorios JPA
+-- Services        Logica de negocio
```

## Flujo General de Ejecucion

1. El cliente frontend o Postman consume los endpoints REST.
2. Los controladores reciben las peticiones HTTP.
3. Los servicios aplican las reglas de negocio.
4. Los repositorios guardan y consultan datos en MySQL usando JPA.
5. Si se sube una imagen, se almacena en `uploads/` y se devuelve una URL publica.
6. En los flujos de apuestas y resultados, el sistema puede generar eventos, retornos, facturas y correos.
