-- Tabla: factura_item

-- Representa los items de una FACTURA --> Ver si es lo mismo una FACTURA ELECTRONICA ¿?¿?
-- Los items van a estar asociados a una LIQUIDACIÓN PAGO o LIQUIDACIÓN COBRO o NINGUNO
-- Por ahora vamos con el caso LIQUIDACIÓN PAGO -> linqueo con el id
-- 
-- id_item_tipo:
-- 
--

-- DROP TABLE factura_item;
SELECT * FROM factura_item;

CREATE TABLE factura_item (
	id_factura_item 		INTEGER NOT NULL,
	
	id_factura				INTEGER NOT NULL,
	
	id_novedad_factura		INTEGER, -- puedo facturar algo que no está en NOVEDADES ¿?
	id_recibo_pago_item		INTEGER, -- relación con la tabla recibo_pago_item
	id_recibo_cobro_item	INTEGER, -- relación con la tabla recibo_cobro_item

	monto 					DECIMAL(18, 2) NOT NULL,
	descripcion				VARCHAR(200),
	id_item_tipo			SMALLINT NOT NULL
);

--INSERT INTO factura_item VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE factura_item OWNER TO dba_inmobiliaria;
GRANT ALL ON factura_item TO dba_inmobiliaria;
GRANT ALL ON factura_item TO usuario_inmobiliaria;
GRANT ALL ON factura_item TO public;


ALTER TABLE factura_item ADD PRIMARY KEY (id_factura_item);
ALTER TABLE factura_item ALTER COLUMN id_factura_item SET DEFAULT nextval('public.factura_item_id_seq'::text);

-- Sequence: factura_item_id_seq

-- DROP SEQUENCE factura_item_id_seq;

CREATE SEQUENCE factura_item_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE factura_item_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: factura_item__novedad
-- ALTER TABLE factura_item DROP CONSTRAINT factura_item__novedad;
ALTER TABLE factura_item
  ADD CONSTRAINT factura_item__novedad FOREIGN KEY (id_novedad_factura) REFERENCES contrato_novedad_factura (id_novedad_factura) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: factura_item__factura
-- ALTER TABLE factura_item DROP CONSTRAINT factura_item__factura;
ALTER TABLE factura_item
  ADD CONSTRAINT factura_item__factura FOREIGN KEY (id_factura) REFERENCES factura (id_factura) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: factura_item__tipo
-- ALTER TABLE factura_item DROP CONSTRAINT factura_item__tipo;
ALTER TABLE factura_item
  ADD CONSTRAINT factura_item__tipo FOREIGN KEY (id_item_tipo) REFERENCES item_tipo (id_item_tipo) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: factura_item__pago
-- ALTER TABLE factura_item DROP CONSTRAINT factura_item__pago;
ALTER TABLE factura_item
  ADD CONSTRAINT factura_item__pago FOREIGN KEY (id_recibo_pago_item) REFERENCES recibo_pago_item (id_recibo_pago_item) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: factura_item__cobro
-- ALTER TABLE factura_item DROP CONSTRAINT factura_item__cobro;
ALTER TABLE factura_item
  ADD CONSTRAINT factura_item__cobro FOREIGN KEY (id_recibo_cobro_item) REFERENCES recibo_cobro_item (id_recibo_cobro_item) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Modificaciones ---------------------------------------------------------------------


-- índices ---------------------------------------------------------------------
--CREATE INDEX recibo_cobro_item_idx1 ON recibo_cobro_item (id_inmueble);
