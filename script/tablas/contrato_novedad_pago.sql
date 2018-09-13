-- Tabla: contrato_novedad_pago

-- Representa las novedades para ser liquidadas al PROPIETARIO de un contrato
-- 
-- id_persona: 	se utiliza para no tener que ir a buscar el dato hasta el contrato
--				Siempre en esta tabla es el Propietario del inmueble
--
-- fecha_vencimiento:	en el caso de impuestos cual es el vencimiento asociado al monto
--
-- Pgarello 27-01-2011
-- Agrego las columnas necesarias para identificar a que impuesto se refiere la novedad
-- impuesto_cuota
-- impuesto_año
-- 
-- * 	Ver en el caso de que se trate de un convenio de pago, ahora la cuota es relativa a un convenio
-- 		de pago 
--
-- 


-- DROP TABLE contrato_novedad_pago;
SELECT * FROM contrato_novedad_pago;

CREATE TABLE contrato_novedad_pago (
	id_contrato_novedad_pago 	INTEGER NOT NULL,
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

--INSERT INTO contrato_novedad_pago VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE contrato_novedad_pago OWNER TO dba_inmobiliaria;
GRANT ALL ON contrato_novedad_pago TO dba_inmobiliaria;
GRANT ALL ON contrato_novedad_pago TO usuario_inmobiliaria;
GRANT ALL ON contrato_novedad_pago TO public;


ALTER TABLE contrato_novedad_pago ADD PRIMARY KEY (id_contrato_novedad_pago);
ALTER TABLE contrato_novedad_pago ALTER COLUMN id_contrato_novedad_pago SET DEFAULT nextval('public.contrato_novedad_pago_id_seq'::text);

-- Sequence: contrato_novedad_pago_id_seq

-- DROP SEQUENCE contrato_novedad_pago_id_seq;

CREATE SEQUENCE contrato_novedad_pago_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE contrato_novedad_pago_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: contrato_novedad_pago__contrato
-- ALTER TABLE contrato_novedad_pago DROP CONSTRAINT contrato_novedad_pago__contrato;
ALTER TABLE contrato_novedad_pago
  ADD CONSTRAINT contrato_novedad_pago__contrato FOREIGN KEY (id_contrato) REFERENCES contrato (id_contrato) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: contrato_novedad_pago__persona
-- ALTER TABLE contrato_novedad_pago DROP CONSTRAINT contrato_novedad_pago__persona;
ALTER TABLE contrato_novedad_pago
  ADD CONSTRAINT contrato_novedad_pago__persona FOREIGN KEY (id_persona) REFERENCES persona (id_persona) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: contrato_novedad_pago__tipo
-- ALTER TABLE contrato_novedad_pago DROP CONSTRAINT contrato_novedad_pago__tipo;
ALTER TABLE contrato_novedad_pago
  ADD CONSTRAINT contrato_novedad_pago__tipo FOREIGN KEY (id_novedad_tipo) REFERENCES novedad_tipo (id_novedad_tipo) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: contrato_novedad_pago__impuesto
-- ALTER TABLE contrato_novedad_pago DROP CONSTRAINT contrato_novedad_pago__impuesto;
ALTER TABLE contrato_novedad_pago
  ADD CONSTRAINT contrato_novedad_pago__impuesto FOREIGN KEY (id_impuesto) REFERENCES impuesto (id_impuesto) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones ---------------------------------------------------------------------

/* Agrego una columna para la descripción de la novedad. Por ejemplo en un impuesto, cual es el impuesto que se paga */
ALTER TABLE contrato_novedad_pago ADD COLUMN descripcion VARCHAR(200);

/* Agrego columnas para poder identificar el impuesto */
ALTER TABLE contrato_novedad_pago ADD COLUMN id_impuesto SMALLINT;
ALTER TABLE contrato_novedad_pago ADD COLUMN impuesto_cuota SMALLINT;
ALTER TABLE contrato_novedad_pago ADD COLUMN impuesto_anio SMALLINT;

/* Se cambia en nombre de la columna */
ALTER TABLE contrato_novedad_pago RENAME fecha_vencimiento  TO fecha_liquidacion;
COMMENT ON COLUMN contrato_novedad_pago.fecha_liquidacion IS 'Es la fecha a partir de la cual le liquido la novedad al propietario';


-- índices ---------------------------------------------------------------------
CREATE INDEX contrato_novedad_pago_idx1 ON contrato_novedad_pago (id_inmueble);


-- Procesos --------------------------------------------------------------------

/* Inserta por cada movimiento 2 que el periodo sea a partir del 12/2012 un registro del tipo 6 (IVA) con el 21% sobre la comisión */
INSERT INTO contrato_novedad_pago 
(id_contrato, id_persona, id_novedad_tipo, periodo_mes, periodo_anio, contrato_cuota, monto, fecha_liquidacion, fecha_alta)
	SELECT id_contrato, id_persona, 6, periodo_mes, periodo_anio, contrato_cuota, monto*0.21, fecha_liquidacion, now()
	FROM contrato_novedad_pago
	WHERE id_novedad_tipo = 2 and ((periodo_mes = 12 and periodo_anio = 2012) or (periodo_anio > 2012))
	ORDER BY periodo_anio, periodo_mes

update contrato_novedad_pago
set fecha_liquidacion = ('''15-' || periodo_mes || '-' || periodo_anio || '''')::date
where fecha_liquidacion is null

/* 23/07/2013 Ver si puedo tener un índice único para detectar la inserción de impuestos por duplicado */

