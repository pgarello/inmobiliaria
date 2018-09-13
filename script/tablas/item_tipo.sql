-- Table: ITEM_TIPO

-- comentario: Tipos de item de los comprobantes que entrega la empresa
-- ¿Difiere de novedad_tipo? Se agregan los tipos 7 / 8
-- 
-- 		1  ALQUILER
-- 		2  COMISION ALQUILER
-- 		3  COMISION CONTRATO
-- 		4  COMISION VENTA
--		5  IMPUESTOS
--		6  IVA
--		7  INTERESES
--		8  VARIOS
--		9  COMISION RESCISION
--		10 RETENCION GANANCIAS

-- DROP TABLE item_tipo;
-- SELECT * FROM item_tipo;

CREATE TABLE item_tipo (
	id_item_tipo SMALLINT NOT NULL ,
	descripcion VARCHAR (30) NOT NULL,
	descripcion_corta VARCHAR (10)
);

-- INSERT
INSERT INTO item_tipo VALUES(1,'ALQUILER','Alq');
INSERT INTO item_tipo VALUES(2,'COMISION ALQUILER','CAlq');
INSERT INTO item_tipo VALUES(3,'COMISION CONTRATO','CCto');
INSERT INTO item_tipo VALUES(4,'COMISION VENTA','CVta');
INSERT INTO item_tipo VALUES(5,'IMPUESTO','Imp');
INSERT INTO item_tipo VALUES(6,'IVA','IVA');
INSERT INTO item_tipo VALUES(7,'INTERESES','Int');
INSERT INTO item_tipo VALUES(8,'VARIOS','Var');
INSERT INTO item_tipo VALUES(9,'COMISION RESCISION','CRes');
INSERT INTO item_tipo VALUES(10,'RETENCION GANANCIAS','RGan');

ALTER TABLE item_tipo OWNER TO dba_inmobiliaria;
GRANT ALL ON item_tipo TO dba_inmobiliaria;
GRANT ALL ON item_tipo TO usuario_inmobiliaria;
GRANT ALL ON item_tipo TO public;


ALTER TABLE item_tipo
	ADD PRIMARY KEY (
		id_item_tipo);

