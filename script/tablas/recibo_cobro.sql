-- Tabla: recibo_cobro

-- Representa la cabecera de un RECIBO DE COBRO
-- LIQUIDACION COBRANZA
-- 
-- Se le emite a un INQUILINO
--

-- DROP TABLE recibo_cobro;
SELECT * FROM recibo_cobro;

CREATE TABLE recibo_cobro (
	id_recibo_cobro 	INTEGER NOT NULL,
	id_persona			INTEGER NOT NULL,
	numero				INTEGER,
	fecha_emision		TIMESTAMP NOT NULL,
	leyenda				VARCHAR(200),
	id_usuario			INTEGER NOT NULL
);

--INSERT INTO recibo_cobro VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE recibo_cobro OWNER TO dba_inmobiliaria;
GRANT ALL ON recibo_cobro TO dba_inmobiliaria;
GRANT ALL ON recibo_cobro TO usuario_inmobiliaria;
GRANT ALL ON recibo_cobro TO public;


ALTER TABLE recibo_cobro ADD PRIMARY KEY (id_recibo_cobro);
ALTER TABLE recibo_cobro ALTER COLUMN id_recibo_cobro SET DEFAULT nextval('public.recibo_cobro_id_seq'::text);

-- Sequence: recibo_cobro_id_seq

-- DROP SEQUENCE recibo_cobro_id_seq;

CREATE SEQUENCE recibo_cobro_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE recibo_cobro_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: recibo_cobro__persona
-- ALTER TABLE recibo_cobro DROP CONSTRAINT recibo_cobro__persona;
ALTER TABLE recibo_cobro
  ADD CONSTRAINT recibo_cobro__persona FOREIGN KEY (id_persona) REFERENCES persona (id_persona) ON UPDATE NO ACTION ON DELETE NO ACTION;

-- Foreign Key: recibo_cobro__usuario
-- ALTER TABLE recibo_cobro DROP CONSTRAINT recibo_cobro__usuario;
ALTER TABLE recibo_cobro
  ADD CONSTRAINT recibo_cobro__usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones ---------------------------------------------------------------------


-- índices ---------------------------------------------------------------------
CREATE UNIQUE INDEX recibo_cobro_idx1 ON recibo_cobro (numero);
