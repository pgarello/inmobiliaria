-- Tabla: facturero

-- Representa todos los factureros con que trabaja el sistema
-- 
-- Problema !!!!!
-- En realidad tengo que tener una tabla mas ....
-- Contrato  (1 --> 1) Facturas (1 --> *) Factureros
--
-- Que pasa cuando se vence el facturero ¿?.
-- La marca de que razón social es la que emite la factura es solamente para fines informativos.

-- DROP TABLE facturero;
SELECT * FROM facturero;

CREATE TABLE facturero (
	id_facturero		INTEGER NOT NULL,
	fecha_alta			TIMESTAMP NOT NULL,
	fecha_vencimiento	DATE NOT NULL,
	fecha_impresion		DATE,
	numero_desde		BIGINT NOT NULL,
	numero_hasta		BIGINT NOT NULL,
	punto_de_venta		SMALLINT,
	indice_actual		BIGINT NOT NULL,
	razon_social		VARCHAR(100),
	observaciones		TEXT
);

--INSERT INTO facturero VALUES(1, now(), '2010-12-31', now(), 5001, 6000, 1, 5001, 'Alicia Gomez de Garello', '');
--INSERT INTO facturero VALUES(2, now(), '2010-12-31', now(), 10001, 11000, 1, 10001, 'Romina Cecilia Garello', '');

--DELETE FROM facturero where id_facturero = 1;

-- Seteo los permisos de acceso y seguridad
ALTER TABLE facturero OWNER TO dba_inmobiliaria;
GRANT ALL ON facturero TO dba_inmobiliaria;
--GRANT ALL ON facturero TO usuario;
GRANT ALL ON facturero TO public;


ALTER TABLE facturero ADD PRIMARY KEY (id_facturero);

-- Foreign Key: contrato__inmueble

-- ALTER TABLE contrato DROP CONSTRAINT contrato__inmueble;

--ALTER TABLE contrato
--  ADD CONSTRAINT contrato__inmueble FOREIGN KEY (id_inmueble) REFERENCES inmueble (id_inmueble) ON UPDATE NO ACTION ON DELETE NO ACTION;





-- Modificaciones ---------------------------------------------------------------------


-- índices ---------------------------------------------------------------------
-- CREATE INDEX contrato_idx1 ON contrato (id_inmueble);
