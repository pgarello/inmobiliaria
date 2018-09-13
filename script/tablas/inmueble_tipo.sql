-- Table: INMUEBLE_TIPO

-- comentario: Tipos de inmuebles que maneja la inmobiliaria
--
--
--

-- DROP TABLE inmueble_tipo;
-- SELECT * FROM inmueble_tipo;

CREATE TABLE inmueble_tipo (
	id_inmueble_tipo SMALLINT NOT NULL ,
	descripcion VARCHAR (30) NOT NULL,
	descripcion_corta VARCHAR (10)
);

-- INSERT
INSERT INTO inmueble_tipo VALUES(1,'Casa', 'Casa');
INSERT INTO inmueble_tipo VALUES(2,'Departamento', 'Dpto');
INSERT INTO inmueble_tipo VALUES(3,'Campo', 'Campo');
INSERT INTO inmueble_tipo VALUES(4,'Terreno', 'Tno');
INSERT INTO inmueble_tipo VALUES(5,'Local Comercial', 'LC');
INSERT INTO inmueble_tipo VALUES(6,'Oficina', 'Of');
INSERT INTO inmueble_tipo VALUES(7,'Duplex', '');
INSERT INTO inmueble_tipo VALUES(8,'Galpón', '');
INSERT INTO inmueble_tipo VALUES(9,'Cochera', '');


ALTER TABLE inmueble_tipo OWNER TO dba_inmobiliaria;
GRANT ALL ON inmueble_tipo TO dba_inmobiliaria;
GRANT ALL ON inmueble_tipo TO usuario_inmobiliaria;
GRANT ALL ON inmueble_tipo TO public;


ALTER TABLE inmueble_tipo
	ADD PRIMARY KEY (
		id_inmueble_tipo);

