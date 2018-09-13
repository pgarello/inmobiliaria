-- Table: inscripcion_ganacias

-- POR AHORA NO SE VA A UTILIZAR - SE VA A CODIFICAR
--

-- DROP TABLE persona;
-- SELECT * FROM persona;

CREATE TABLE persona (
	id_persona				INTEGER NOT NULL,
	apellido				VARCHAR(30) NOT NULL,
	nombres					VARCHAR(30) NOT NULL,
	id_documento_tipo		SMALLINT NOT NULL,
	documento_nro			DECIMAL(9,0) NOT NULL,
	id_localidad			INTEGER,
	fecha_nacimiento		DATE,
	direccion				VARCHAR(200),
	fecha_alta				DATE
);

--INSERT INTO persona VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE persona OWNER TO dba_inmobiliaria;
GRANT ALL ON persona TO dba_inmobiliaria;
GRANT ALL ON persona TO usuario;
GRANT ALL ON persona TO public;


ALTER TABLE persona
	ADD PRIMARY KEY (
		id_persona);




-- Foreign Key: persona__localidad

-- ALTER TABLE persona DROP CONSTRAINT persona__localidad;

ALTER TABLE persona
  ADD CONSTRAINT persona__localidad FOREIGN KEY (id_localidad_nac) REFERENCES localidad (id_localidad) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: persona__tipo_documento

-- ALTER TABLE persona DROP CONSTRAINT persona__documento_tipo;

ALTER TABLE persona
  ADD CONSTRAINT persona__documento_tipo FOREIGN KEY (id_documento_tipo) REFERENCES documento_tipo (id_documento_tipo) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones


-- índices ---------------------------------------------------------------------
CREATE INDEX persona_idx_apellido
   ON persona (apellido);
ALTER TABLE persona CLUSTER ON persona_idx_apellido;
