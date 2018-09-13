-- Tabla: contrato_actor

-- Representa como participa una persona/entidad en un contrato
-- Pueden ser como:	INQUILINO		1
-- 					PROPIETARIO		2
--					GARANTE
--
-- Le agregamos fecha de baja, si un propietario cambia entonces le ponemos fecha de baja
-- y damos de alta un nuevo propietario con fecha_baja en nulo ....
-- 
-- Otra alternativa es tener un campo que represente en que porcentaje es un propietario, puede
-- haber propiedades con 2 o 3 propietarios, en ese caso habría que liquidarle en función del
-- porcentaje !!! 
--

-- DROP TABLE contrato_actor;
-- SELECT * FROM contrato_actor;

CREATE TABLE contrato_actor (
	id_contrato				INTEGER NOT NULL,
	id_actor_tipo			SMALLINT NOT NULL,
	id_persona				INTEGER NOT NULL
);

--INSERT INTO contrato_actor VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE contrato_actor OWNER TO dba_inmobiliaria;
GRANT ALL ON contrato_actor TO dba_inmobiliaria;
GRANT ALL ON contrato_actor TO usuario_inmobiliaria;
GRANT ALL ON contrato_actor TO public;


ALTER TABLE contrato_actor
	ADD PRIMARY KEY (id_contrato, id_actor_tipo, id_persona);




-- Foreign Key: contrato_actor__contrato

-- ALTER TABLE contrato_actor DROP CONSTRAINT contrato_actor__contrato;

ALTER TABLE contrato_actor
  ADD CONSTRAINT contrato_actor__contrato FOREIGN KEY (id_contrato) REFERENCES contrato (id_contrato) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: contrato_actor__persona

-- ALTER TABLE contrato_actor DROP CONSTRAINT contrato_actor__persona;

ALTER TABLE contrato_actor
  ADD CONSTRAINT contrato_actor__persona FOREIGN KEY (id_persona) REFERENCES persona (id_persona) ON UPDATE NO ACTION ON DELETE NO ACTION;



-- Modificaciones
ALTER TABLE contrato_actor ADD COLUMN fecha_baja DATE;
ALTER TABLE contrato_actor ADD COLUMN porcentaje DECIMAL(5,2);

-- índices ---------------------------------------------------------------------
CREATE INDEX persona_idx_apellido
   ON persona (apellido);
ALTER TABLE persona CLUSTER ON persona_idx_apellido;
