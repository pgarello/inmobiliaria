-- Tabla: inmueble_propietario

-- Son todos los propietario de una propiedad, tienen un procentaje sobre la propiedad
-- 1 solo propietario - 100% 
--

-- DROP TABLE inmueble_propietario;
-- SELECT * FROM inmueble_propietario;

CREATE TABLE inmueble_propietario (
	id_inmueble			INTEGER NOT NULL,
	porcentaje			DECIMAL(5,2) NOT NULL,
	id_persona			INTEGER NOT NULL
);

--INSERT INTO inmueble_propietario VALUES(1,100, 1);

ALTER TABLE inmueble_propietario OWNER TO dba_inmobiliaria;
GRANT ALL ON inmueble_propietario TO dba_inmobiliaria;
GRANT ALL ON inmueble_propietario TO usuario_inmobiliaria;
GRANT ALL ON inmueble_propietario TO public;


ALTER TABLE inmueble_propietario
	ADD PRIMARY KEY (id_inmueble, id_persona);


-- Foreign Key: inmueble_propietario__inmueble
-- ALTER TABLE inmueble_propietario DROP CONSTRAINT inmueble_propietario__inmueble;
ALTER TABLE inmueble_propietario
  ADD CONSTRAINT inmueble_propietario__inmueble FOREIGN KEY (id_inmueble) REFERENCES inmueble (id_inmueble) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: inmueble_propietario__persona
-- ALTER TABLE contrato_actor DROP CONSTRAINT inmueble_propietario__persona;
ALTER TABLE inmueble_propietario
  ADD CONSTRAINT inmueble_propietario__persona FOREIGN KEY (id_persona) REFERENCES persona (id_persona) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones

-- índices ---------------------------------------------------------------------
CREATE INDEX inmueble_propietario_idx1
   ON inmueble_propietario (id_inmueble);
ALTER TABLE inmueble_propietario CLUSTER ON inmueble_propietario_idx1;
