-- Table: edificio



-- DROP TABLE edificio;
-- SELECT * FROM edificio;

CREATE TABLE edificio (
  id_edificio integer NOT NULL,
  descripcion character varying(100) NOT NULL,
  observaciones text,
  dpto_cantidad smallint,
  CONSTRAINT edificio_pk PRIMARY KEY (id_edificio)
);


ALTER TABLE edificio OWNER TO dba_inmobiliaria;
GRANT ALL ON edificio TO dba_inmobiliaria;
GRANT ALL ON edificio TO usuario_inmobiliaria;
GRANT ALL ON edificio TO public;


-- Sequence: edificio_id_seq

-- DROP SEQUENCE edificio_id_seq;

CREATE SEQUENCE edificio_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER TABLE edificio_id_seq OWNER TO dba_inmobiliaria;

SELECT setval('public.edificio_id_seq', 7, true);

ALTER TABLE edificio ALTER COLUMN id_edificio SET DEFAULT nextval('public.edificio_id_seq'::text);

-- ************************************************************************
-- Foreign Key: persona__localidad

-- ALTER TABLE persona DROP CONSTRAINT persona__localidad;

ALTER TABLE persona
  ADD CONSTRAINT persona__localidad FOREIGN KEY (id_localidad) REFERENCES localidad (id_localidad) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones
ALTER TABLE persona ADD COLUMN observaciones VARCHAR(255);


-- índices ---------------------------------------------------------------------
-- CREATE INDEX persona_idx_apellido ON persona (apellido);

