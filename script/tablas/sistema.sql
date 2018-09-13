-- Tabla: sistema

-- Todos los atributos que voy a usar en el mismo que parametrizan el programa
-- 
--
--
--

-- DROP TABLE sistema;
SELECT * FROM sistema;

CREATE TABLE sistema (
	id_contrato 		INTEGER NOT NULL,
	fecha_desde			DATE,
	fecha_hasta 		DATE,
	fecha_extension		DATE,
	observaciones		TEXT,
	monto 				DECIMAL(18, 2),
	cantidad_cuota 		SMALLINT,
	id_inmueble			INTEGER,
	comision_prop_porc	DECIMAL(9, 2),
	comision_prop_fija	DECIMAL(9, 2),
	comision_inquilino	DECIMAL(9, 2),
	fecha_alta			TIMESTAMP NOT NULL 
);

--INSERT INTO contrato VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE contrato OWNER TO dba_inmobiliaria;
GRANT ALL ON contrato TO dba_inmobiliaria;
--GRANT ALL ON contrato TO usuario;
GRANT ALL ON contrato TO public;


ALTER TABLE contrato ADD PRIMARY KEY (id_contrato);
ALTER TABLE contrato ALTER COLUMN id_contrato SET DEFAULT nextval('public.contrato_id_seq'::text);

-- Sequence: contrato_id_seq

-- DROP SEQUENCE contrato_id_seq;

CREATE SEQUENCE contrato_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE contrato_id_seq OWNER TO dba_inmobiliaria;



-- Foreign Key: contrato__inmueble

-- ALTER TABLE contrato DROP CONSTRAINT contrato__inmueble;

ALTER TABLE contrato
  ADD CONSTRAINT contrato__inmueble FOREIGN KEY (id_inmueble) REFERENCES inmueble (id_inmueble) ON UPDATE NO ACTION ON DELETE NO ACTION;




-- Modificaciones ---------------------------------------------------------------------


-- índices ---------------------------------------------------------------------
CREATE INDEX contrato_idx1 ON contrato (id_inmueble);
