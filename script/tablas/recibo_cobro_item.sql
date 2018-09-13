-- Tabla: recibo_cobro_item

-- Representa los items de un RECIBO DE COBRO
-- 
--
--
--

-- DROP TABLE recibo_cobro_item;
SELECT * FROM recibo_cobro_item;

CREATE TABLE recibo_cobro_item (
	id_recibo_cobro_item 	INTEGER NOT NULL,
	id_recibo_cobro			INTEGER NOT NULL,
	id_novedad				INTEGER,
	monto 					DECIMAL(18, 2) NOT NULL,
	descripcion				VARCHAR(200),
	id_item_tipo			SMALLINT NOT NULL
);

--INSERT INTO recibo_cobro_item VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE recibo_cobro_item OWNER TO dba_inmobiliaria;
GRANT ALL ON recibo_cobro_item TO dba_inmobiliaria;
GRANT ALL ON recibo_cobro_item TO usuario_inmobiliaria;
GRANT ALL ON recibo_cobro_item TO public;


ALTER TABLE recibo_cobro_item ADD PRIMARY KEY (id_recibo_cobro_item);
ALTER TABLE recibo_cobro_item ALTER COLUMN id_recibo_cobro_item SET DEFAULT nextval('public.recibo_cobro_item_id_seq'::text);

-- Sequence: recibo_cobro_item_id_seq

-- DROP SEQUENCE recibo_cobro_item_id_seq;

CREATE SEQUENCE recibo_cobro_item_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE recibo_cobro_item_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: recibo_cobro_item__novedad
-- ALTER TABLE recibo_cobro_item DROP CONSTRAINT recibo_cobro_item__novedad;
ALTER TABLE recibo_cobro_item
  ADD CONSTRAINT recibo_cobro_item__novedad FOREIGN KEY (id_novedad) REFERENCES contrato_novedad_cobro (id_contrato_novedad_cobro) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: recibo_cobro_item__recibo
-- ALTER TABLE recibo_cobro_item DROP CONSTRAINT recibo_cobro_item__recibo;
ALTER TABLE recibo_cobro_item
  ADD CONSTRAINT recibo_cobro_item__recibo FOREIGN KEY (id_recibo_cobro) REFERENCES recibo_cobro (id_recibo_cobro) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: recibo_cobro_item__tipo
-- ALTER TABLE recibo_cobro_item DROP CONSTRAINT recibo_cobro_item__tipo;
ALTER TABLE recibo_cobro_item
  ADD CONSTRAINT recibo_cobro_item__tipo FOREIGN KEY (id_item_tipo) REFERENCES item_tipo (id_item_tipo) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones ---------------------------------------------------------------------


-- índices ---------------------------------------------------------------------
CREATE INDEX recibo_cobro_item_idx1 ON recibo_cobro_item (id_novedad);
