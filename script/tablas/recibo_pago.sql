-- Tabla: recibo_pago

-- Representa la cabecera de un RECIBO DE PAGO
-- LIQUIDACION PAGO
-- 
-- Se le emite a un PROPIETARIO
--

-- DROP TABLE recibo_pago;
SELECT * FROM recibo_pago;

CREATE TABLE recibo_pago (
	id_recibo_pago 		INTEGER NOT NULL,
	id_persona			INTEGER NOT NULL,
	numero				INTEGER,
	fecha_emision		TIMESTAMP NOT NULL,
	leyenda				VARCHAR(200),
	id_usuario			INTEGER NOT NULL
);

--INSERT INTO recibo_cobro VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE recibo_pago OWNER TO dba_inmobiliaria;
GRANT ALL ON recibo_pago TO dba_inmobiliaria;
GRANT ALL ON recibo_pago TO usuario_inmobiliaria;
GRANT ALL ON recibo_pago TO public;


ALTER TABLE recibo_pago ADD PRIMARY KEY (id_recibo_pago);
ALTER TABLE recibo_pago ALTER COLUMN id_recibo_pago SET DEFAULT nextval('public.recibo_pago_id_seq'::text);

-- Sequence: recibo_pago_id_seq

-- DROP SEQUENCE recibo_pago_id_seq;

CREATE SEQUENCE recibo_pago_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE recibo_pago_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: recibo_pago__persona
-- ALTER TABLE recibo_pago DROP CONSTRAINT recibo_pago__persona;
ALTER TABLE recibo_pago
  ADD CONSTRAINT recibo_pago__persona FOREIGN KEY (id_persona) REFERENCES persona (id_persona) ON UPDATE NO ACTION ON DELETE NO ACTION;

-- Foreign Key: recibo_cobro__usuario
-- ALTER TABLE recibo_pago DROP CONSTRAINT recibo_pago__usuario;
ALTER TABLE recibo_pago
  ADD CONSTRAINT recibo_pago__usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones ---------------------------------------------------------------------


-- índices ---------------------------------------------------------------------
CREATE UNIQUE INDEX recibo_pago_idx1 ON recibo_pago (numero);
CREATE INDEX recibo_pago_idx2 ON recibo_pago (fecha_emision);
