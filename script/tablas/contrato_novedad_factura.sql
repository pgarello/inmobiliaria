-- Tabla: contrato_novedad_factura

/* 
Representa las novedades para ser facturadas de un contrato
Es el ingreso de la inmobiliaria:
	1 - Comisiones por la administración del inmueble en alquiler - COMISION ALQUILER (Propietario)
	2 - Comisión por un nuevo contrato - COMISION CONTRATO (Inquilino)
	3 - Tasaciones contra una PERSONA
	4 - Servicios en general, por ejemplo por administración de consorcio, administración de impuestos

*/

-- DROP TABLE contrato_novedad_factura;
SELECT * FROM contrato_novedad_factura;

CREATE TABLE contrato_novedad_factura (
	id_novedad_factura 	INTEGER NOT NULL,
	
	id_contrato			INTEGER,
	id_persona			INTEGER NOT NULL,
	id_novedad_tipo		SMALLINT NOT NULL,
	periodo_mes			SMALLINT,
	periodo_anio		SMALLINT,
	contrato_cuota		SMALLINT,
	monto 				DECIMAL(18, 2),
	fecha_vencimiento	DATE,
	fecha_alta			TIMESTAMP NOT NULL 
);

--INSERT INTO contrato_novedad_factura VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE contrato_novedad_factura OWNER TO dba_inmobiliaria;
GRANT ALL ON contrato_novedad_factura TO dba_inmobiliaria;
GRANT ALL ON contrato_novedad_factura TO usuario_inmobiliaria;
GRANT ALL ON contrato_novedad_factura TO public;


ALTER TABLE contrato_novedad_factura ADD PRIMARY KEY (id_novedad_factura);
ALTER TABLE contrato_novedad_factura ALTER COLUMN id_novedad_factura SET DEFAULT nextval('public.novedad_factura_id_seq'::text);

-- Sequence: novedad_factura_id_seq

-- DROP SEQUENCE novedad_factura_id_seq;

CREATE SEQUENCE novedad_factura_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE novedad_factura_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: contrato_novedad_factura__contrato
-- ALTER TABLE contrato_novedad_factura DROP CONSTRAINT contrato_novedad_factura__contrato;
ALTER TABLE contrato_novedad_factura
  ADD CONSTRAINT contrato_novedad_factura__contrato FOREIGN KEY (id_contrato) REFERENCES contrato (id_contrato) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: contrato_novedad_factura__persona
-- ALTER TABLE contrato_novedad_factura DROP CONSTRAINT contrato_novedad_factura__persona;
ALTER TABLE contrato_novedad_factura
  ADD CONSTRAINT contrato_novedad_factura__persona FOREIGN KEY (id_persona) REFERENCES persona (id_persona) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: contrato_novedad_factura__tipo
-- ALTER TABLE contrato_novedad_factura DROP CONSTRAINT contrato_novedad_factura__tipo;
ALTER TABLE contrato_novedad_factura
  ADD CONSTRAINT contrato_novedad_factura__tipo FOREIGN KEY (id_novedad_tipo) REFERENCES novedad_tipo (id_novedad_tipo) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones ---------------------------------------------------------------------


-- índices ---------------------------------------------------------------------
--CREATE INDEX contrato_novedad_pago_idx1 ON contrato_novedad_pago (id_inmueble);
