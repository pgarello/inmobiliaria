-- Tabla: impuesto

-- Representa los diferentes impuestos con que trabaja la inmobiliaria
-- 
-- el CAMPO mensualidad representa con que periodicidad mensual se paga el
-- impuesto. (Por ej. mensual [1] bimestral[2] semestral[6])
-- 
-- Se puede agregar un campo para representar la jurisdicción del impuesto
-- MUNICIPAL - PROVINCIAL -NACIONAL
--
--

-- DROP TABLE impuesto;
SELECT * FROM impuesto;

CREATE TABLE impuesto (
	id_impuesto 		INTEGER NOT NULL,
	descripcion			VARCHAR(100),
	descripcion_corta	VARCHAR(10),
	mensualidad 		SMALLINT
);

--INSERT INTO impuesto VALUES(1,'Tasa General de Inmueble', 'TGI', 1);
--INSERT INTO impuesto VALUES(2,'Impuesto Inmobiliario', 'API', 3);
--INSERT INTO impuesto VALUES(3,'Aguas Santafesinas', 'AGUAS', 2);
--INSERT INTO impuesto VALUES(5,'Varios', 'VARIO', 2);

ALTER TABLE impuesto OWNER TO dba_inmobiliaria;
GRANT ALL ON impuesto TO dba_inmobiliaria;
--GRANT ALL ON impuesto TO usuario;
GRANT ALL ON impuesto TO public;


ALTER TABLE impuesto ADD PRIMARY KEY (id_impuesto);
ALTER TABLE impuesto ALTER COLUMN id_impuesto SET DEFAULT nextval('public.impuesto_id_seq'::text);

-- Sequence: contrato_id_seq

-- DROP SEQUENCE contrato_id_seq;

CREATE SEQUENCE impuesto_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE impuesto_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: contrato__inmueble

-- ALTER TABLE contrato DROP CONSTRAINT contrato__inmueble;


-- Modificaciones ---------------------------------------------------------------------


-- índices ---------------------------------------------------------------------

