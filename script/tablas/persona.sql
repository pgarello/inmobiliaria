-- Table: persona

-- comentario: Personas registradas en el sistema
--
-- sexo: 'M' 'F'
-- id_persona_tipo: 1 FISICA | 2 JURIDICA
-- id_documento_tipo: 1 DNI | 2 LC | 3 LE | 4 PAS
-- id_inscripcion_iva: 1 CF | 2 MONOTRIBUTISTA | 3 RI | 4 EXENTO
-- id_inscripcion_ganancia: 1 NO INSCRIPTO | 2 INSCRIPTO | 3 EXENTO
--
-- Relevar si se necesitan mas datos para un contrato
-- nacionalidad ¿?
--
--

/*
Persona FISICA
- apellido
- nombre
- documento
- cuit/cuil (no obligatorio)
- responsabilidad ante el IVA

Persona JURIDICA
- razon social
- cuit (obligatorio)
- responsabilidad ante el IVA

*/


-- DROP TABLE persona;
-- SELECT * FROM persona;

CREATE TABLE persona (
	id_persona				INTEGER NOT NULL,
	apellido				VARCHAR(50) NOT NULL,
	nombres					VARCHAR(50) NOT NULL,
	id_documento_tipo		SMALLINT NOT NULL,
	documento_nro			DECIMAL(9,0) NOT NULL,
	id_localidad			INTEGER,
	fecha_nacimiento		DATE,
	direccion				VARCHAR(200),	
	id_inscripcion_iva		SMALLINT NOT NULL,
	cuit					VARCHAR(13),
	telefono				VARCHAR(30),
	celular					VARCHAR(30),
	mail					VARCHAR(100),
	razon_social			VARCHAR(100),
	fecha_alta				DATE,
	id_persona_tipo			INTEGER NOT NULL,
	sexo					CHAR(1) NOT NULL
);


INSERT INTO persona VALUES(1,'Garello', 'Pablo Andres', 1, 23726150, 1, '1974-03-22', '4 de Enero 3697 Dpto 1', 1, '', '4535674', '155473225', 'pgarello@gmail.com', '', now(), 1, 'M');

ALTER TABLE persona OWNER TO dba_inmobiliaria;
GRANT ALL ON persona TO dba_inmobiliaria;
GRANT ALL ON persona TO usuario_inmobiliaria;
GRANT ALL ON persona TO public;


ALTER TABLE persona
	ADD PRIMARY KEY (
		id_persona);


-- Sequence: persona_id_seq

-- DROP SEQUENCE persona_id_seq;

CREATE SEQUENCE persona_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE persona_id_seq OWNER TO dba_inmobiliaria;



-- ************************************************************************
-- Foreign Key: persona__localidad

-- ALTER TABLE persona DROP CONSTRAINT persona__localidad;

ALTER TABLE persona
  ADD CONSTRAINT persona__localidad FOREIGN KEY (id_localidad) REFERENCES localidad (id_localidad) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- ************************************************************************
-- Foreign Key: persona__tipo_documento

-- ALTER TABLE persona DROP CONSTRAINT persona__documento_tipo;

ALTER TABLE persona
  ADD CONSTRAINT persona__documento_tipo FOREIGN KEY (id_documento_tipo) REFERENCES documento_tipo (id_documento_tipo) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones
	ALTER TABLE persona ADD COLUMN observaciones VARCHAR(255);

	-- agrego la columna para la inscripción en GANANCIAS 6/1/2014
	ALTER TABLE persona ADD COLUMN id_inscripcion_ganancia SMALLINT NOT NULL DEFAULT 1;

-- índices ---------------------------------------------------------------------
CREATE INDEX persona_idx_apellido ON persona (apellido);
CREATE INDEX persona_idx_documento ON persona (id_documento_tipo, documento_nro); 
CREATE INDEX persona_idx_localidad ON persona (id_localidad);
