# Proyecto de Automatización de Migración a Moodle

## Descripción

Este proyecto tiene como objetivo automatizar la migración de la plataforma de la universidad a
Moodle mediante el desarrollo de web services. Estos servicios permiten automatizar procesos clave
como la creación de grupos, creación de usuarios, matrícula, desmatrícula de grupos y extracción de
notas.

## Características

### 1. Creación de Grupos

- **Funcionalidad:** Permite la creación automática de grupos en Moodle.
- **Características Específicas:**
    - Definición de nombres y descripciones de los grupos.
    - Asignación de grupos a cursos específicos.
    - Configuración de roles dentro de los grupos.

### 2. Creación de Usuarios

- **Funcionalidad:** Automatiza la creación de perfiles de usuarios (estudiantes y docentes) en
  Moodle.
- **Características Específicas:**
    - Importación de datos de usuarios desde la base de datos de la universidad.
    - Asignación de roles y permisos correspondientes.
    - Envío de notificaciones a los usuarios creados con sus credenciales.

### 3. Matrícula de Estudiantes y Docentes

- **Funcionalidad:** Automatiza la inscripción de estudiantes y docentes en cursos y grupos.
- **Características Específicas:**
    - Inscripción masiva de usuarios en cursos.
    - Administración de roles y permisos dentro de cada curso.
    - Soporte para matrículas en múltiples cursos y grupos temáticos.

### 4. Desmatrícula de Grupos

- **Funcionalidad:** Permite la eliminación automatizada de estudiantes y docentes de los grupos y
  cursos.
- **Características Específicas:**
    - Eliminación masiva de usuarios de cursos y grupos.
    - Mantenimiento de registros históricos de desmatrículas.
    - Generación de reportes de desmatrícula.

### 5. Extracción de Notas

- **Funcionalidad:** Facilita la exportación de notas y calificaciones de los estudiantes desde
  Moodle.
- **Características Específicas:**
    - Exportación de calificaciones en diferentes formatos (CSV, Excel, etc.).
    - Integración con sistemas de gestión académica para la sincronización de notas.
    - Generación de reportes detallados de desempeño académico.

## Beneficios de la Automatización

- **Eficiencia:** Reducción del tiempo y esfuerzo necesarios para realizar estas tareas manualmente.
- **Precisión:** Minimización de errores humanos en el proceso de migración y gestión.
- **Consistencia:** Aseguramiento de que todos los datos y procesos sigan un estándar uniforme.
- **Escalabilidad:** Capacidad para manejar grandes volúmenes de datos y usuarios sin problemas.
- **Integración:** Fácil integración con otros sistemas y bases de datos de la universidad.

## Tecnologías Utilizadas

- Backend: Java con Spring Boot
- Base de Datos: ORACLE
- Mensajería: ActiveMQ
- Otras: Docker, JMS, JWT para autenticación

## Requisitos

- Java JDK 17
- Gradle
- Oracle
- ActiveMQ
- Docker (opcional)

## Configuración

### Base de Datos

- Oracle
- Credenciales:
    - Username: ``
    - Password: ``

![Modelo Relacional](https://github.com/mangelmorenomoreno/moodle/blob/main/resource/ModeloRelacionalCampos.png)

## Script para Crear las Tablas de la Base de Datos

Para configurar la base de datos, ejecute el siguiente script SQL:

```sql
CREATE TABLE camposaprendizajeudec.aud_matriculamoodle (
    mamo_id            NUMBER(30) NOT NULL,
    usmo_id            NUMBER(30) NOT NULL,
    grse_id            NUMBER(30) NOT NULL,
    romo_id            NUMBER(30) NOT NULL,
    mamo_registradopor VARCHAR2(30 BYTE),
    mamo_fechacambio   TIMESTAMP,
    mamo_operacion     VARCHAR2(1 BYTE) DEFAULT 'I' NOT NULL
)
PCTFREE 10 PCTUSED 40 TABLESPACE camposaprendizajeudec LOGGING
    STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
    )
NO INMEMORY;

CREATE TABLE camposaprendizajeudec.categoriasmoodle (
    camo_id            NUMBER NOT NULL,
    mate_codigomateria VARCHAR2(100 BYTE),
    pens_id            NUMBER,
    unid_id            NUMBER,
    peun_id            NUMBER,
    camo_registradopor VARCHAR2(255 BYTE),
    camo_idmoodle      NUMBER,
    camo_fechacambio   DATE
)
PCTFREE 10 PCTUSED 40 TABLESPACE camposaprendizajeudec LOGGING
    STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
    )
NO INMEMORY;

CREATE UNIQUE INDEX camposaprendizajeudec.pk_categoriasmoodle ON
    camposaprendizajeudec.categoriasmoodle (
        camo_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

ALTER TABLE camposaprendizajeudec.categoriasmoodle
    ADD CONSTRAINT pk_categoriasmoodle PRIMARY KEY ( camo_id )
        USING INDEX camposaprendizajeudec.pk_categoriasmoodle;

CREATE TABLE camposaprendizajeudec.gruposemilla (
    grse_id            NUMBER(30) NOT NULL,
    semo_id            NUMBER(30) NOT NULL,
    grup_id            NUMBER(30),
    peun_id            NUMBER(30),
    inst_id            NUMBER(30) NOT NULL,
    grse_idmoodle      VARCHAR2(30 BYTE) NOT NULL,
    grse_nombrecorto   VARCHAR2(200 BYTE) NOT NULL,
    grse_nombrelargo   VARCHAR2(300 BYTE) NOT NULL,
    grse_registradopor VARCHAR2(30 BYTE),
    grse_fechacambio   TIMESTAMP
)
PCTFREE 10 PCTUSED 40 TABLESPACE camposaprendizajeudec LOGGING
    STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
    )
NO INMEMORY;

CREATE UNIQUE INDEX camposaprendizajeudec.gruposemillas_pk ON
    camposaprendizajeudec.gruposemilla (
        grse_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

ALTER TABLE camposaprendizajeudec.gruposemilla
    ADD CONSTRAINT gruposemillas_pk PRIMARY KEY ( grse_id )
        USING INDEX camposaprendizajeudec.gruposemillas_pk;

CREATE TABLE camposaprendizajeudec.instanciamoodle (
    inst_id            NUMBER(30) NOT NULL,
    inst_nombre        VARCHAR2(100 BYTE) NOT NULL,
    inst_descripcion   VARCHAR2(300 BYTE) NOT NULL,
    inst_estado        VARCHAR2(1 BYTE) NOT NULL,
    inst_url           VARCHAR2(500 BYTE) NOT NULL,
    inst_registradopor VARCHAR2(30 BYTE),
    inst_fechacambio   TIMESTAMP
)
PCTFREE 10 PCTUSED 40 TABLESPACE camposaprendizajeudec LOGGING
    STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
    )
NO INMEMORY;

CREATE UNIQUE INDEX camposaprendizajeudec.instanciamoodle_pk ON
    camposaprendizajeudec.instanciamoodle (
        inst_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

ALTER TABLE camposaprendizajeudec.instanciamoodle
    ADD CONSTRAINT instanciamoodle_pk PRIMARY KEY ( inst_id )
        USING INDEX camposaprendizajeudec.instanciamoodle_pk;

CREATE TABLE camposaprendizajeudec.matriculamoodle (
    mamo_id            NUMBER(30) NOT NULL,
    usmo_id            NUMBER(30) NOT NULL,
    grse_id            NUMBER(30) NOT NULL,
    romo_id            NUMBER(30) NOT NULL,
    mamo_registradopor VARCHAR2(30 BYTE),
    mamo_fechacambio   TIMESTAMP
)
PCTFREE 10 PCTUSED 40 TABLESPACE camposaprendizajeudec LOGGING
    STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
    )
NO INMEMORY;

CREATE UNIQUE INDEX camposaprendizajeudec.matriculamoodle_pk ON
    camposaprendizajeudec.matriculamoodle (
        mamo_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

CREATE UNIQUE INDEX camposaprendizajeudec.usuariomatricula_uk ON
    camposaprendizajeudec.matriculamoodle (
        usmo_id
    ASC,
        grse_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

ALTER TABLE camposaprendizajeudec.matriculamoodle
    ADD CONSTRAINT matriculamoodle_pk PRIMARY KEY ( mamo_id )
        USING INDEX camposaprendizajeudec.matriculamoodle_pk;

ALTER TABLE camposaprendizajeudec.matriculamoodle
    ADD CONSTRAINT usuariomatricula_uk UNIQUE ( usmo_id,
                                                grse_id )
        USING INDEX camposaprendizajeudec.usuariomatricula_uk;

CREATE TABLE camposaprendizajeudec.notasmoodle (
    nomo_id               NUMBER(30) NOT NULL,
    peun_id               NUMBER(30) NOT NULL,
    grse_idmoodle         NUMBER(30) NOT NULL,
    usmo_idmoodle         NUMBER(30) NOT NULL,
    nomo_itemname         VARCHAR2(3000 BYTE) NOT NULL,
    nomo_course           VARCHAR2(3000 BYTE) NOT NULL,
    nomo_finalgradecourse VARCHAR2(10 BYTE) NOT NULL,
    nomo_category         VARCHAR2(3000 BYTE),
    nomo_registradopor    VARCHAR2(30 BYTE),
    nomo_fechacambio      TIMESTAMP,
    nomo_username         VARCHAR2(2000 BYTE)
)
PCTFREE 10 PCTUSED 40 TABLESPACE camposaprendizajeudec LOGGING
    STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
    )
NO INMEMORY;

CREATE UNIQUE INDEX camposaprendizajeudec.notasmoodle_pk ON
    camposaprendizajeudec.notasmoodle (
        nomo_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

CREATE UNIQUE INDEX camposaprendizajeudec.notasmoodle__un ON
    camposaprendizajeudec.notasmoodle (
        usmo_idmoodle
    ASC,
        grse_idmoodle
    ASC,
        peun_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

ALTER TABLE camposaprendizajeudec.notasmoodle
    ADD CONSTRAINT notasmoodle_pk PRIMARY KEY ( nomo_id )
        USING INDEX camposaprendizajeudec.notasmoodle_pk;

CREATE TABLE camposaprendizajeudec.rolmoodle (
    romo_id            NUMBER(30) NOT NULL,
    romo_nombre        VARCHAR2(100 BYTE) NOT NULL,
    romo_descripcion   VARCHAR2(300 BYTE),
    romo_estado        VARCHAR2(1 BYTE) NOT NULL,
    romo_registradopor VARCHAR2(30 BYTE),
    romo_fechacambio   TIMESTAMP
)
PCTFREE 10 PCTUSED 40 TABLESPACE camposaprendizajeudec LOGGING
    STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
    )
NO INMEMORY;

CREATE UNIQUE INDEX camposaprendizajeudec.rolmoodle_pk ON
    camposaprendizajeudec.rolmoodle (
        romo_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

ALTER TABLE camposaprendizajeudec.rolmoodle
    ADD CONSTRAINT rolmoodle_pk PRIMARY KEY ( romo_id )
        USING INDEX camposaprendizajeudec.rolmoodle_pk;

CREATE TABLE camposaprendizajeudec.semillamoodle (
    semo_id            NUMBER(30) NOT NULL,
    cadi_id            NUMBER(30),
    cai_id             NUMBER(30),
    pens_id            NUMBER(30),
    semo_idmoodle      VARCHAR2(30 BYTE) NOT NULL,
    semo_nombrelargo   VARCHAR2(500 BYTE) NOT NULL,
    semo_nombrecorto   VARCHAR2(500 BYTE) NOT NULL,
    mate_codigomateria VARCHAR2(100 BYTE),
    semo_registradopor VARCHAR2(30 BYTE),
    semo_fechacambio   TIMESTAMP,
    semo_idcategoria   VARCHAR2(30 BYTE),
    semi_estado        VARCHAR2(2 BYTE)
)
PCTFREE 10 PCTUSED 40 TABLESPACE camposaprendizajeudec LOGGING
    STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
    )
NO INMEMORY;

CREATE UNIQUE INDEX camposaprendizajeudec.semillasmoodle_pk ON
    camposaprendizajeudec.semillamoodle (
        semo_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

ALTER TABLE camposaprendizajeudec.semillamoodle
    ADD CONSTRAINT semillasmoodle_pk PRIMARY KEY ( semo_id )
        USING INDEX camposaprendizajeudec.semillasmoodle_pk;

CREATE TABLE camposaprendizajeudec.tipousuario (
    tium_id            NUMBER(30) NOT NULL,
    tium_nombre        VARCHAR2(100 BYTE) NOT NULL,
    tium_descripcion   VARCHAR2(300 BYTE) NOT NULL,
    tium_registradopor VARCHAR2(30 BYTE),
    tium_fechacambio   TIMESTAMP
)
PCTFREE 10 PCTUSED 40 TABLESPACE camposaprendizajeudec LOGGING
    STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
    )
NO INMEMORY;

CREATE UNIQUE INDEX camposaprendizajeudec.tipousuario_pk ON
    camposaprendizajeudec.tipousuario (
        tium_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

ALTER TABLE camposaprendizajeudec.tipousuario
    ADD CONSTRAINT tipousuario_pk PRIMARY KEY ( tium_id )
        USING INDEX camposaprendizajeudec.tipousuario_pk;

CREATE TABLE camposaprendizajeudec.tmp_usuariosmoodle (
    idmoodle       VARCHAR2(50 BYTE),
    username       VARCHAR2(50 BYTE),
    identificacion VARCHAR2(50 BYTE)
)
PCTFREE 10 PCTUSED 40 TABLESPACE camposaprendizajeudec LOGGING
    STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
    )
NO INMEMORY;

CREATE TABLE camposaprendizajeudec.usuariomoodle (
    usmo_id            NUMBER(30) NOT NULL,
    inst_id            NUMBER(30) NOT NULL,
    tius_id            NUMBER(30) NOT NULL,
    pege_id            NUMBER(30) NOT NULL,
    usmo_usuario       VARCHAR2(100 BYTE) NOT NULL,
    area_id            NUMBER(30),
    prog_id            NUMBER(30),
    estp_id            NUMBER(30),
    unid_id            NUMBER(30),
    usmo_registradopor VARCHAR2(30 BYTE),
    usmo_fechacambio   TIMESTAMP,
    usmo_idmoodle      VARCHAR2(30 BYTE)
)
PCTFREE 10 PCTUSED 40 TABLESPACE camposaprendizajeudec LOGGING
    STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
    )
NO INMEMORY;

CREATE UNIQUE INDEX camposaprendizajeudec.usuarioinstancia_uk ON
    camposaprendizajeudec.usuariomoodle (
        inst_id
    ASC,
        pege_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

CREATE UNIQUE INDEX camposaprendizajeudec.usuariomoodle_pk ON
    camposaprendizajeudec.usuariomoodle (
        usmo_id
    ASC )
        TABLESPACE camposaprendizajeudec PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

ALTER TABLE camposaprendizajeudec.usuariomoodle
    ADD CONSTRAINT usuariomoodle_pk PRIMARY KEY ( usmo_id )
        USING INDEX camposaprendizajeudec.usuariomoodle_pk;

ALTER TABLE camposaprendizajeudec.usuariomoodle
    ADD CONSTRAINT usuarioinstancia_uk UNIQUE ( inst_id,
                                                pege_id )
        USING INDEX camposaprendizajeudec.usuarioinstancia_uk;

ALTER TABLE camposaprendizajeudec.gruposemilla
    ADD CONSTRAINT gruposemillas_instanciamoodle_fk FOREIGN KEY ( inst_id )
        REFERENCES camposaprendizajeudec.instanciamoodle ( inst_id )
    NOT DEFERRABLE;

ALTER TABLE camposaprendizajeudec.gruposemilla
    ADD CONSTRAINT gruposemillas_semillasmoodle_fk FOREIGN KEY ( semo_id )
        REFERENCES camposaprendizajeudec.semillamoodle ( semo_id )
    NOT DEFERRABLE;

ALTER TABLE camposaprendizajeudec.matriculamoodle
    ADD CONSTRAINT matriculamoodle_gruposemi_fk FOREIGN KEY ( grse_id )
        REFERENCES camposaprendizajeudec.gruposemilla ( grse_id )
    NOT DEFERRABLE;

ALTER TABLE camposaprendizajeudec.matriculamoodle
    ADD CONSTRAINT matriculamoodle_rolmoodle_fk FOREIGN KEY ( romo_id )
        REFERENCES camposaprendizajeudec.rolmoodle ( romo_id )
    NOT DEFERRABLE;

ALTER TABLE camposaprendizajeudec.matriculamoodle
    ADD CONSTRAINT table_6_usuariomoodle_fk FOREIGN KEY ( usmo_id )
        REFERENCES camposaprendizajeudec.usuariomoodle ( usmo_id )
    NOT DEFERRABLE;

ALTER TABLE camposaprendizajeudec.usuariomoodle
    ADD CONSTRAINT usuariomoodle_instmoodle_fk FOREIGN KEY ( inst_id )
        REFERENCES camposaprendizajeudec.instanciamoodle ( inst_id )
    NOT DEFERRABLE;

ALTER TABLE camposaprendizajeudec.usuariomoodle
    ADD CONSTRAINT usuariomoodle_tipousuario_fk FOREIGN KEY ( tius_id )
        REFERENCES camposaprendizajeudec.tipousuario ( tium_id )
    NOT DEFERRABLE;

CREATE OR REPLACE TRIGGER camposaprendizajeudec.tr_camposaprendizajeudec_i_mat AFTER
    INSERT ON camposaprendizajeudec.matriculamoodle
    FOR EACH ROW
BEGIN
    INSERT INTO camposaprendizajeudec.aud_matriculamoodle (
        mamo_id,
        mamo_registradopor,
        grse_id,
        mamo_fechacambio,
        romo_id,
        usmo_id,
        mamo_operacion
    ) VALUES (
        :new.mamo_id,
        :new.mamo_registradopor,
        :new.grse_id,
        :new.mamo_fechacambio,
        :new.romo_id,
        :new.usmo_id,
        'I'
    );

END;
/

CREATE OR REPLACE TRIGGER camposaprendizajeudec.tr_camposaprendizajeudec_u_mat AFTER
    UPDATE ON camposaprendizajeudec.matriculamoodle
    FOR EACH ROW
BEGIN
    INSERT INTO camposaprendizajeudec.aud_matriculamoodle (
        mamo_id,
        mamo_registradopor,
        grse_id,
        mamo_fechacambio,
        romo_id,
        usmo_id,
        mamo_operacion
    ) VALUES (
        :new.mamo_id,
        :new.mamo_registradopor,
        :new.grse_id,
        :new.mamo_fechacambio,
        :new.romo_id,
        :new.usmo_id,
        'U'
    );

END;
/

CREATE SEQUENCE camposaprendizajeudec.s_camo_id START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER camposaprendizajeudec.tro_inc_categoriasmoodle BEFORE
    INSERT ON camposaprendizajeudec.categoriasmoodle
    FOR EACH ROW
    WHEN ( new.camo_id IS NULL )
BEGIN
    :new.camo_id := camposaprendizajeudec.s_camo_id.nextval;
END;
/

CREATE SEQUENCE camposaprendizajeudec.s_grse_id START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER camposaprendizajeudec.tro_inc_gruposemilla BEFORE
    INSERT ON camposaprendizajeudec.gruposemilla
    FOR EACH ROW
    WHEN ( new.grse_id IS NULL )
BEGIN
    :new.grse_id := camposaprendizajeudec.s_grse_id.nextval;
END;
/

CREATE SEQUENCE camposaprendizajeudec.s_inst_id START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER camposaprendizajeudec.tro_inc_instanciamoodle BEFORE
    INSERT ON camposaprendizajeudec.instanciamoodle
    FOR EACH ROW
    WHEN ( new.inst_id IS NULL )
BEGIN
    :new.inst_id := camposaprendizajeudec.s_inst_id.nextval;
END;
/

CREATE SEQUENCE camposaprendizajeudec.s_mamo_id START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER camposaprendizajeudec.tro_inc_matriculamoodle BEFORE
    INSERT ON camposaprendizajeudec.matriculamoodle
    FOR EACH ROW
    WHEN ( new.mamo_id IS NULL )
BEGIN
    :new.mamo_id := camposaprendizajeudec.s_mamo_id.nextval;
END;
/

CREATE SEQUENCE camposaprendizajeudec.s_nomo_id START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER camposaprendizajeudec.tro_inc_notasmoodle BEFORE
    INSERT ON camposaprendizajeudec.notasmoodle
    FOR EACH ROW
    WHEN ( new.nomo_id IS NULL )
BEGIN
    :new.nomo_id := camposaprendizajeudec.s_nomo_id.nextval;
END;
/

CREATE SEQUENCE camposaprendizajeudec.s_romo_id START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER camposaprendizajeudec.tro_inc_rolmoodle BEFORE
    INSERT ON camposaprendizajeudec.rolmoodle
    FOR EACH ROW
    WHEN ( new.romo_id IS NULL )
BEGIN
    :new.romo_id := camposaprendizajeudec.s_romo_id.nextval;
END;
/

CREATE SEQUENCE camposaprendizajeudec.s_semo_id START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER camposaprendizajeudec.tro_inc_semillamoodle BEFORE
    INSERT ON camposaprendizajeudec.semillamoodle
    FOR EACH ROW
    WHEN ( new.semo_id IS NULL )
BEGIN
    :new.semo_id := camposaprendizajeudec.s_semo_id.nextval;
END;
/

CREATE SEQUENCE camposaprendizajeudec.s_tium_id START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER camposaprendizajeudec.tro_inc_tipousuario BEFORE
    INSERT ON camposaprendizajeudec.tipousuario
    FOR EACH ROW
    WHEN ( new.tium_id IS NULL )
BEGIN
    :new.tium_id := camposaprendizajeudec.s_tium_id.nextval;
END;
/

CREATE SEQUENCE camposaprendizajeudec.s_usmo_id START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER camposaprendizajeudec.tro_inc_usuariomoodle BEFORE
    INSERT ON camposaprendizajeudec.usuariomoodle
    FOR EACH ROW
    WHEN ( new.usmo_id IS NULL )
BEGIN
    :new.usmo_id := camposaprendizajeudec.s_usmo_id.nextval;
END;
/
```

### ActiveMQ

- URL: `tcp://localhost:61616`
- User: `admin`
- Password: `admin`

## Instalación y Ejecución

Instrucciones para ejecutar la aplicación.

1. Clona el repositorio:
   git clone https://github.com/mangelmorenomoreno/moodle.git
2. Navega al directorio del proyecto:
   cd [Nombre del Directorio]
3. Ejecuta el backend (Spring Boot)

## Documentación API

La documentación de la API está disponible
en: `http://localhost:8000/ucundinamarca/api/v1swagger-ui/index.html`

## Estrategia de Ramificación

Se utiliza GitFlow como estrategia de ramificación. Por favor, revisa el flujo de ramas para
contribuir al proyecto.

## Pruebas

- Pruebas unitarias y de integración están disponibles en el módulo de tests.
- Ejecutar pruebas:
  mvn test

## Contribuir

Para contribuir al proyecto, por favor sigue las instrucciones de ramificación y envía Pull Requests
para revisión.

## Autores

- Miguel Angel Moreno Moreno
