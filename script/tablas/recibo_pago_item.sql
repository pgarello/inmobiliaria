-- Tabla: recibo_pago_item

-- Representa los items de un RECIBO DE PAGO
-- 
--
--
--

-- DROP TABLE recibo_pago_item;
SELECT * FROM recibo_pago_item;

CREATE TABLE recibo_pago_item (
	id_recibo_pago_item 	INTEGER NOT NULL,
	id_recibo_pago			INTEGER NOT NULL,
	id_novedad				INTEGER,
	monto 					DECIMAL(18, 2) NOT NULL,
	descripcion				VARCHAR(200),
	id_item_tipo			SMALLINT NOT NULL
);

--INSERT INTO recibo_pago_item VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE recibo_pago_item OWNER TO dba_inmobiliaria;
GRANT ALL ON recibo_pago_item TO dba_inmobiliaria;
GRANT ALL ON recibo_pago_item TO usuario_inmobiliaria;
GRANT ALL ON recibo_pago_item TO public;


ALTER TABLE recibo_pago_item ADD PRIMARY KEY (id_recibo_pago_item);
ALTER TABLE recibo_pago_item ALTER COLUMN id_recibo_pago_item SET DEFAULT nextval('public.recibo_pago_item_id_seq'::text);

-- Sequence: recibo_pago_item_id_seq

-- DROP SEQUENCE recibo_pago_item_id_seq;

CREATE SEQUENCE recibo_pago_item_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE recibo_pago_item_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: recibo_cobro_item__novedad
-- ALTER TABLE recibo_cobro_item DROP CONSTRAINT recibo_cobro_item__novedad;
ALTER TABLE recibo_pago_item
  ADD CONSTRAINT recibo_pago_item__novedad FOREIGN KEY (id_novedad) REFERENCES contrato_novedad_pago (id_contrato_novedad_pago) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: recibo_cobro_item__recibo
-- ALTER TABLE recibo_cobro_item DROP CONSTRAINT recibo_cobro_item__recibo;
ALTER TABLE recibo_pago_item
  ADD CONSTRAINT recibo_pago_item__recibo FOREIGN KEY (id_recibo_pago) REFERENCES recibo_pago (id_recibo_pago) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: recibo_cobro_item__tipo
-- ALTER TABLE recibo_cobro_item DROP CONSTRAINT recibo_cobro_item__tipo;
ALTER TABLE recibo_pago_item
  ADD CONSTRAINT recibo_pago_item__tipo FOREIGN KEY (id_item_tipo) REFERENCES item_tipo (id_item_tipo) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones ---------------------------------------------------------------------


-- índices ---------------------------------------------------------------------
CREATE INDEX recibo_pago_item_idx1 ON recibo_pago_item (id_recibo_pago);
CREATE INDEX recibo_pago_item_idx2 ON recibo_pago_item (id_item_tipo);
