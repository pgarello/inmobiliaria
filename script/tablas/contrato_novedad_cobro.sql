-- Tabla: contrato_novedad_cobro

-- Representa las novedades para ser liquidadas al 
-- INQUILINO de un contrato
-- 
-- Relación con la tabla PERSONA ¿?
--
--
--

-- DROP TABLE contrato_novedad_cobro;
SELECT * FROM contrato_novedad_cobro;

CREATE TABLE contrato_novedad_cobro (
	id_contrato_novedad_cobro 	INTEGER NOT NULL,
	id_contrato 				INTEGER NOT NULL,
	id_persona					INTEGER NOT NULL,
	id_novedad_tipo				SMALLINT NOT NULL,
	periodo_mes					SMALLINT,
	periodo_anio				SMALLINT,
	contrato_cuota				SMALLINT,
	monto 						DECIMAL(18, 2),
	fecha_vencimiento			DATE,
	fecha_alta					TIMESTAMP NOT NULL 
);

--INSERT INTO contrato_novedad_cobro VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE contrato_novedad_cobro OWNER TO dba_inmobiliaria;
GRANT ALL ON contrato_novedad_cobro TO dba_inmobiliaria;
GRANT ALL ON contrato_novedad_cobro TO usuario_inmobiliaria;
GRANT ALL ON contrato_novedad_cobro TO public;


ALTER TABLE contrato_novedad_cobro ADD PRIMARY KEY (id_contrato_novedad_cobro);
ALTER TABLE contrato_novedad_cobro ALTER COLUMN id_contrato_novedad_cobro SET DEFAULT nextval('public.contrato_novedad_cobro_id_seq'::text);

-- Sequence: contrato_novedad_cobro_id_seq

-- DROP SEQUENCE contrato_novedad_cobro_id_seq;

CREATE SEQUENCE contrato_novedad_cobro_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE contrato_novedad_cobro_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: contrato_novedad_cobro__contrato
-- ALTER TABLE contrato_novedad_cobro DROP CONSTRAINT contrato_novedad_cobro__contrato;
ALTER TABLE contrato_novedad_cobro
  ADD CONSTRAINT contrato_novedad_cobro__contrato FOREIGN KEY (id_contrato) REFERENCES contrato (id_contrato) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: contrato_novedad_cobro__persona
-- ALTER TABLE contrato_novedad_cobro DROP CONSTRAINT contrato_novedad_cobro__persona;
ALTER TABLE contrato_novedad_cobro
  ADD CONSTRAINT contrato_novedad_cobro__persona FOREIGN KEY (id_persona) REFERENCES persona (id_persona) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: contrato_novedad_cobro__tipo
-- ALTER TABLE contrato_novedad_cobro DROP CONSTRAINT contrato_novedad_cobro__tipo;
ALTER TABLE contrato_novedad_cobro
  ADD CONSTRAINT contrato_novedad_cobro__tipo FOREIGN KEY (id_novedad_tipo) REFERENCES novedad_tipo (id_novedad_tipo) ON UPDATE NO ACTION ON DELETE NO ACTION;

-- NO ANDUBO
-- Foreign Key: contrato_novedad_cobro__contrato_actor
-- ALTER TABLE contrato_novedad_cobro DROP CONSTRAINT contrato_novedad_cobro__contrato_actor;
ALTER TABLE contrato_novedad_cobro
  ADD CONSTRAINT contrato_novedad_cobro__contrato_actor FOREIGN KEY (id_contrato, id_persona, 1) 
  REFERENCES contrato_actor (id_contrato, id_persona, id_actor_tipo) 
  ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Modificaciones ---------------------------------------------------------------------

/* Agrego una columna para la descripción de la novedad. Por ejemplo en un impuesto, cual es el impuesto que se paga */
ALTER TABLE contrato_novedad_cobro ADD COLUMN descripcion VARCHAR(200);

/* Agrego columnas para poder identificar el impuesto */
ALTER TABLE contrato_novedad_cobro ADD COLUMN id_impuesto SMALLINT;
ALTER TABLE contrato_novedad_cobro ADD COLUMN impuesto_cuota SMALLINT;
ALTER TABLE contrato_novedad_cobro ADD COLUMN impuesto_anio SMALLINT;


-- índices ---------------------------------------------------------------------
CREATE INDEX contrato_novedad_cobro_idx1 ON contrato_novedad_cobro (id_inmueble);
CREATE INDEX contrato_novedad_cobro_idx2 ON contrato_novedad_cobro (id_contrato);
