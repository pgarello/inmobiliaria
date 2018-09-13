-- Tabla: novedad_tipo

-- Tipifica todas las novedades a ser liquidadas por la inmobiliaria en un contrato
-- 		ALQUILER
-- 		COMISION ALQUILER
-- 		COMISION CONTRATO
-- 		IMPUESTO
--		COMISION VENTA
--		COMISION IMPUESTOS

-- DROP TABLE novedad_tipo;
SELECT * FROM novedad_tipo;

CREATE TABLE novedad_tipo (
	id_novedad_tipo 	INTEGER NOT NULL,
	descripcion 		VARCHAR(100) NOT NULL
);

--INSERT INTO novedad_tipo VALUES(1,'ALQUILER');
--INSERT INTO novedad_tipo VALUES(2,'COMISION ALQUILER');
--INSERT INTO novedad_tipo VALUES(3,'COMISION CONTRATO');
--INSERT INTO novedad_tipo VALUES(4,'COMISION VENTA');
--INSERT INTO novedad_tipo VALUES(5,'IMPUESTO');
--INSERT INTO novedad_tipo VALUES(6,'IVA');
--INSERT INTO novedad_tipo VALUES(7,'COMISION RESCISION');

ALTER TABLE novedad_tipo OWNER TO dba_inmobiliaria;
GRANT ALL ON novedad_tipo TO dba_inmobiliaria;
GRANT ALL ON novedad_tipo TO usuario_inmobiliaria;
GRANT ALL ON novedad_tipo TO public;


ALTER TABLE novedad_tipo ADD PRIMARY KEY (id_novedad_tipo);