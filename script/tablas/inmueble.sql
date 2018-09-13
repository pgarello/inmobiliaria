-- Table: INMUEBLE

-- comentario: Son todos los inmuebles registrados en la inmobiliaria
--
-- id_propietario: ojo con esta relación, no esta claro el tema del propietario y puedo no saberlo
-- por ejemplo si se trata de una propiedad que la maneja otra inmobiliaria
-- tener en cuenta la tabla actores
-- 03/03/2015 Ahora vamos a manejar varios propietarios con un porcentaje
--
--
-- ¿donde tengo persistido el valor con que se ofrece, venta o alquiler?
-- Por ejemplo si quiero tener un registro de las propiedades que se están publicitando.
--
-- En el modelo de dato también tengo el dato si esta reservada la propiedad (quien la reservo, monto y observación)
--
-- Contacto con el dueño de la propiedad (un empleado o algún conocido)
-- 
-- A futuro puedo tener grabadas las fotos de la propiedad en la bd¿?
-- Metros cuadrados de la propiedad, superficie cubierta, cant. de dormitorios, telefono, etc.


-- DROP TABLE inmueble;
-- SELECT * FROM inmueble;

CREATE TABLE inmueble (
	id_inmueble 		INTEGER NOT NULL ,
	id_inmueble_tipo 	SMALLINT NOT NULL ,
	id_localidad 		INTEGER NOT NULL ,
	direccion_calle 	VARCHAR(100) NULL ,
	direccion_nro 		VARCHAR(10) NULL ,
	direccion_edificio 	VARCHAR(50) NULL ,
	direccion_piso 		VARCHAR(10) NULL ,
	direccion_dpto 		VARCHAR(10) NULL ,
	observaciones 		VARCHAR(300) NULL ,
	id_propietario 		INTEGER NULL 
);

--INSERT INTO inmueble VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE inmueble OWNER TO dba_inmobiliaria;
GRANT ALL ON inmueble TO dba_inmobiliaria;
GRANT ALL ON inmueble TO usuario_inmobiliaria;
GRANT ALL ON inmueble TO public;


ALTER TABLE inmueble
	ADD PRIMARY KEY (
		id_inmueble);


-- Sequence: inmueble_id_seq

-- DROP SEQUENCE inmueble_id_seq;

CREATE SEQUENCE inmueble_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE inmueble_id_seq OWNER TO dba_inmobiliaria;

ALTER TABLE inmueble ALTER COLUMN id_inmueble SET DEFAULT nextval('public.inmueble_id_seq'::text);


-- Foreign Key: inmueble__localidad
-- ALTER TABLE inmueble DROP CONSTRAINT inmueble__localidad;

ALTER TABLE inmueble
  ADD CONSTRAINT inmueble__localidad FOREIGN KEY (id_localidad) REFERENCES localidad (id_localidad) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: inmueble__inmueble_tipo
-- ALTER TABLE inmueble DROP CONSTRAINT inmueble__inmueble_tipo;

ALTER TABLE inmueble
  ADD CONSTRAINT inmueble__inmueble_tipo FOREIGN KEY (id_inmueble_tipo) REFERENCES inmueble_tipo (id_inmueble_tipo) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Foreign Key: inmueble__propietario
-- ALTER TABLE inmueble DROP CONSTRAINT inmueble__propietario;

ALTER TABLE inmueble
  ADD CONSTRAINT inmueble__propietario FOREIGN KEY (id_propietario) REFERENCES persona (id_persona) ON UPDATE NO ACTION ON DELETE NO ACTION;




-- Modificaciones
ALTER TABLE inmueble 

-- índices ---------------------------------------------------------------------
CREATE INDEX persona_idx_apellido
   ON persona (apellido);
ALTER TABLE persona CLUSTER ON persona_idx_apellido;


-- procesos --------------------------------------------------------------------

/* Cambiar el propietario de un inmueble con CONTRATO */

