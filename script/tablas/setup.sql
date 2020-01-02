-- Tabla: setup

-- Mantiene los contadores e índices únicos gestionados por el programa, no por la base de datos
-- 
-- Tener en cuenta que voy a manejar factureros (mas de uno) y que voy a gestionar el número con 
-- esta tabla
--

-- DROP TABLE setup;
SELECT * FROM setup;

CREATE TABLE setup (
	id_setup 			INTEGER NOT NULL,
	fecha_actualizacion	TIMESTAMP,
	tabla 				VARCHAR(100) NOT NULL,
	id_facturero		SMALLINT,
	indice				BIGINT NOT NULL,
	observaciones		TEXT
);

--INSERT INTO setup VALUES(1, now(), 'recibo_cobro', 1, 5001, '');
--INSERT INTO setup VALUES(2, now(), 'recibo_cobro', 2, 10001, 'Facturero Romina');

-- Ahora lo voy a utilizar para guardar el % de IVA actual
-- el vigente es el que tiene la fecha de actualizacion NULA
--INSERT INTO setup VALUES(3, null, 'IVA', null, 21, 'Porcentaje Actual de IVA');

-- Guardo los datos para el cálculo de la RETENCION DE GANANCIAS 12/01/2015
--INSERT INTO setup VALUES(4, null, 'GANANCIA-EXENTO', null, 1200, 'Es el monto que está EXENTO del pago de GANANCIAS');
--INSERT INTO setup VALUES(5, null, 'GANANCIA-INSCRIPTO', null, 6, 'Es el porcentaje que se le retiene si está Inscripto en GANANCIAS');
--INSERT INTO setup VALUES(6, null, 'GANANCIA-NO_INSCRIPTO', null, 28, 'Es el porcentaje que se le retiene si NO está Inscripto en GANANCIAS');

-- Seteo los permisos de acceso y seguridad
ALTER TABLE setup OWNER TO dba_inmobiliaria;
GRANT ALL ON setup TO dba_inmobiliaria;
--GRANT ALL ON contrato TO usuario;
GRANT ALL ON setup TO public;


ALTER TABLE setup ADD PRIMARY KEY (id_setup);

-- Foreign Key: contrato__inmueble

-- ALTER TABLE contrato DROP CONSTRAINT contrato__inmueble;

--ALTER TABLE contrato
--  ADD CONSTRAINT contrato__inmueble FOREIGN KEY (id_inmueble) REFERENCES inmueble (id_inmueble) ON UPDATE NO ACTION ON DELETE NO ACTION;




-- Modificaciones ---------------------------------------------------------------------

-- Actualización del monto de EXENTO DE GANANCIAS
INSERT INTO setup VALUES( (SELECT max(id_setup)+1 FROM setup) , null, 'GANANCIA-EXENTO', null, 7120, 'Es el monto que está EXENTO del pago de GANANCIAS');
UPDATE setup SET fecha_actualizacion = now() WHERE id_setup = 4;

INSERT INTO setup VALUES( (SELECT max(id_setup)+1 FROM setup) , null, 'GANANCIA-EXENTO', null, 11200, 'Es el monto que está EXENTO del pago de GANANCIAS');
UPDATE setup SET fecha_actualizacion = now() WHERE id_setup = 7;

-- índices ---------------------------------------------------------------------
-- CREATE INDEX contrato_idx1 ON contrato (id_inmueble);
