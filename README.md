# Carvajal Comentarios y Publicaciones

Una aplicación web estilo red social que permite la interacción entre usuarios por medio de comentarios en publicaciones, similar a Facebook. Los usuarios pueden crear, editar, y eliminar sus propias publicaciones, así como comentar en las publicaciones de otros.

## Características

- Autenticación de usuarios (Login, Registro, Recuperación de contraseña)
- Creación y gestión de publicaciones y comentarios
- Notificaciones por correo electrónico
- Microservicios con Spring Boot y Angular (Frontend)

## Tecnologías Utilizadas

- Backend: Java con Spring Boot
- Frontend: Angular
- Base de Datos: PostgreSQL
- Mensajería: ActiveMQ
- Otras: Docker, JMS, JWT para autenticación

## Requisitos

- Java JDK 17
- Maven
- PostgreSQL
- ActiveMQ
- Docker (opcional)

## Configuración

### Base de Datos

- PostgreSQL
- Credenciales:
    - Username: `postgres`
    - Password: `admin`

![Modelo Relacional](https://github.com/mangelmorenomoreno/carvajal-back/blob/feature/HU-ENTITY/resource/modeloRelacional.PNG)

## Script para Crear las Tablas de la Base de Datos

Para configurar la base de datos, ejecute el siguiente script SQL:

```sql
-- facetime.usuarios definition

-- Drop table

-- DROP TABLE facetime.usuarios;

CREATE TABLE facetime.usuarios (
	user_id serial4 NOT NULL,
	nombre varchar(100) NOT NULL,
	correo_electronico varchar(150) NOT NULL,
	fecha_creacion timestamptz NULL DEFAULT CURRENT_TIMESTAMP,
	apellido varchar(100) NULL,
	estado bool NULL,
	CONSTRAINT usuarios_correo_electronico_key UNIQUE (correo_electronico),
	CONSTRAINT usuarios_pkey PRIMARY KEY (user_id)
);

-- Permissions

ALTER TABLE facetime.usuarios OWNER TO postgres;
GRANT ALL ON TABLE facetime.usuarios TO postgres;


-- facetime.credenciales definition

-- Drop table

-- DROP TABLE facetime.credenciales;

CREATE TABLE facetime.credenciales (
	credencial_id serial4 NOT NULL,
	user_id int4 NOT NULL,
	hash_contraseña varchar(1000) NULL,
	token_reset_password varchar(1000) NULL,
	token_reset_expiry timestamptz NULL,
	CONSTRAINT credenciales_pkey PRIMARY KEY (credencial_id),
	CONSTRAINT fk_usuario FOREIGN KEY (user_id) REFERENCES facetime.usuarios(user_id)
);

-- Permissions

ALTER TABLE facetime.credenciales OWNER TO postgres;
GRANT ALL ON TABLE facetime.credenciales TO postgres;


-- facetime.publicaciones definition

-- Drop table

-- DROP TABLE facetime.publicaciones;

CREATE TABLE facetime.publicaciones (
	post_id bigserial NOT NULL,
	user_id int4 NOT NULL,
	titulo varchar(200) NULL,
	contenido text NOT NULL,
	fecha_publicacion timestamptz NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT publicaciones_pkey PRIMARY KEY (post_id),
	CONSTRAINT publicaciones_user_id_fkey FOREIGN KEY (user_id) REFERENCES facetime.usuarios(user_id)
);

-- Permissions

ALTER TABLE facetime.publicaciones OWNER TO postgres;
GRANT ALL ON TABLE facetime.publicaciones TO postgres;


-- facetime.comentarios definition

-- Drop table

-- DROP TABLE facetime.comentarios;

CREATE TABLE facetime.comentarios (
	comment_id bigserial NOT NULL,
	post_id int8 NULL,
	user_id int4 NOT NULL,
	contenido text NOT NULL,
	fecha_comentario timestamptz NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT comentarios_pkey PRIMARY KEY (comment_id),
	CONSTRAINT comentarios_post_id_fkey FOREIGN KEY (post_id) REFERENCES facetime.publicaciones(post_id),
	CONSTRAINT comentarios_user_id_fkey FOREIGN KEY (user_id) REFERENCES facetime.usuarios(user_id)
);

-- Permissions

ALTER TABLE facetime.comentarios OWNER TO postgres;
GRANT ALL ON TABLE facetime.comentarios TO postgres;


-- facetime.respuestas_comentarios definition

-- Drop table

-- DROP TABLE facetime.respuestas_comentarios;

CREATE TABLE facetime.respuestas_comentarios (
	respuesta_id bigserial NOT NULL,
	comment_id int8 NOT NULL,
	user_id int4 NOT NULL,
	contenido text NOT NULL,
	fecha_respuesta timestamptz NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT respuestas_comentarios_pkey PRIMARY KEY (respuesta_id),
	CONSTRAINT respuestas_comentarios_comment_id_fkey FOREIGN KEY (comment_id) REFERENCES facetime.comentarios(comment_id),
	CONSTRAINT respuestas_comentarios_user_id_fkey FOREIGN KEY (user_id) REFERENCES facetime.usuarios(user_id)
);

-- Permissions

ALTER TABLE facetime.respuestas_comentarios OWNER TO postgres;
GRANT ALL ON TABLE facetime.respuestas_comentarios TO postgres;
```

### ActiveMQ

- URL: `tcp://localhost:61616`
- User: `admin`
- Password: `admin`

## Instalación y Ejecución

Instrucciones para ejecutar la aplicación.

1. Clona el repositorio:
   git clone https://github.com/mangelmorenomoreno/carvajal-back.git
2. Navega al directorio del proyecto:
   cd [Nombre del Directorio]
3. Ejecuta el backend (Spring Boot):
   cd backend
   mvn spring-boot:run
4. Ejecuta el frontend (Angular):
   cd frontend
   ng serve
5. Abre tu navegador y visita `http://localhost:4200` para acceder a la interfaz de usuario.

## Documentación API

La documentación de la API está disponible en: `http://localhost:8000/cavajal/api/v1/swagger-ui/index.html`

## Estrategia de Ramificación

Se utiliza GitFlow como estrategia de ramificación. Por favor, revisa el flujo de ramas para contribuir al proyecto.

## Pruebas

- Pruebas unitarias y de integración están disponibles en el módulo de tests.
- Ejecutar pruebas:
  mvn test

## Contribuir

Para contribuir al proyecto, por favor sigue las instrucciones de ramificación y envía Pull Requests para revisión.

## Autores

- Miguel Angel Moreno Moreno
