-- Tabla: contrato

-- Representa los contratos que tenga la inmobiliaria
-- 
-- Los intereses por pago fuera de término se van a levantar desde una tabla de sistema
-- La comisión a cobrar al propietario puede ser un porcentaje del alquiler o un monto fijo
-- La comisión al inquilino es lo que se le cobra por el contrato, generalmente es un mes más,
-- a este movimiento le corresponde una factura (A - B) --> tener en cuenta si no va en CONTRATO_NOVEDAD
--
--
--

-- DROP TABLE contrato;
SELECT * FROM contrato;

CREATE TABLE contrato (
	id_contrato 		INTEGER NOT NULL,
	fecha_desde			DATE,
	fecha_hasta 		DATE,
	fecha_extension		DATE,
	observaciones		TEXT,
	monto 				DECIMAL(18, 2),
	cantidad_cuota 		SMALLINT,
	id_inmueble			INTEGER,
	comision_prop_porc	DECIMAL(9, 2),
	comision_prop_fija	DECIMAL(9, 2),
	comision_inquilino	DECIMAL(9, 2),
	fecha_alta			TIMESTAMP NOT NULL 
);

--INSERT INTO contrato VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE contrato OWNER TO dba_inmobiliaria;
GRANT ALL ON contrato TO dba_inmobiliaria;
GRANT ALL ON contrato TO usuario_inmobiliaria;
GRANT ALL ON contrato TO public;


ALTER TABLE contrato ADD PRIMARY KEY (id_contrato);
ALTER TABLE contrato ALTER COLUMN id_contrato SET DEFAULT nextval('public.contrato_id_seq'::text);

-- Sequence: contrato_id_seq

-- DROP SEQUENCE contrato_id_seq;

CREATE SEQUENCE contrato_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE contrato_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: contrato__inmueble

-- ALTER TABLE contrato DROP CONSTRAINT contrato__inmueble;

ALTER TABLE contrato
  ADD CONSTRAINT contrato__inmueble FOREIGN KEY (id_inmueble) REFERENCES inmueble (id_inmueble) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE contrato
  ADD CONSTRAINT contrato__facturero FOREIGN KEY (id_facturero) 
  REFERENCES facturero (id_facturero) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones ---------------------------------------------------------------------
ALTER TABLE contrato ADD COLUMN id_facturero INTEGER;
UPDATE contrato SET id_facturero = 1;
ALTER TABLE contrato ALTER COLUMN id_facturero SET NOT NULL;

ALTER TABLE contrato ADD COLUMN fecha_rescision Date;

/* Ahora se van a identificar los CONTRATOS en COMERCIALES y NO COMERCIALES 26/05/2014 */
ALTER TABLE contrato ADD COLUMN comercial BOOLEAN DEFAULT false;

-- índices ---------------------------------------------------------------------
CREATE INDEX contrato_idx1 ON contrato (id_inmueble);
