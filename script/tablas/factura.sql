-- Tabla: factura

-- Una factura se emite contra una PERSONA que esté en sistema
-- La cabecera de las FACTURAS
-- Copio todos los datos que se incluyen en la impresión de la factura por las dudas que se cambie alguno y varíe
-- la factura impresa de la que muetra el sistema.

-- CAMPOS
-- id_inscripcion_iva: 1 CF | 2 MONOTRIBUTISTA | 3 RI | 4 EXENTO
-- factura_tipo: 1 A | 2 B
-- cuit_dni
-- id_persona: Es la clave de la tabla persona a la que le estoy FACTURANDO (puede ser propietario - inquilino - cliente)
-- anulada: VERDADERO/FALSO

-- DROP TABLE factura;
SELECT * FROM factura;

CREATE TABLE factura (
	id_factura 			INTEGER NOT NULL,
	
	numero				INTEGER NOT NULL,
	factura_tipo		SMALLINT NOT NULL,
	fecha_emision		TIMESTAMP NOT NULL,
	
	id_persona			INTEGER NOT NULL,
	
	id_inscripcion_iva	SMALLINT NOT NULL,
	cliente				VARCHAR(100) NOT NULL,
	cuit_dni			VARCHAR(20) NOT NULL,
	domicilio			VARCHAR(100) NOT NULL,
	
	id_usuario			SMALLINT NOT NULL,
	leyenda 			VARCHAR(200) DEFAULT '',
	anulada 			BOOLEAN NOT NULL
	
);

--INSERT INTO factura VALUES(1, 1000, 2, '2012-11-28', 1, 1, 'Pablo Garello', 'DNI 23.726.150', 'Las Heras 5539-Sta.Fe', 1, now());

ALTER TABLE factura OWNER TO dba_inmobiliaria;
GRANT ALL ON factura TO dba_inmobiliaria;
GRANT ALL ON factura TO usuario_inmobiliaria;
GRANT ALL ON factura TO public;


ALTER TABLE factura ADD PRIMARY KEY (id_factura);
ALTER TABLE factura ALTER COLUMN id_factura SET DEFAULT nextval('public.factura_id_seq'::text);

-- Modificaciones a la estructura


-- Sequence: factura_id_seq
-- DROP SEQUENCE factura_id_seq;
CREATE SEQUENCE factura_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE factura_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: factura__persona
-- ALTER TABLE factura DROP CONSTRAINT factura__persona;
ALTER TABLE factura
  ADD CONSTRAINT factura__persona FOREIGN KEY (id_persona) REFERENCES persona (id_persona) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE factura
  ADD CONSTRAINT factura__usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones ---------------------------------------------------------------------
ALTER TABLE factura ADD COLUMN leyenda VARCHAR(300);

-- índices ---------------------------------------------------------------------
CREATE UNIQUE INDEX factura_idx1 ON factura (factura_tipo, numero);
