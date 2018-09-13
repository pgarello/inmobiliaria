-- Table: localidad

-- comentario: Localidades del pais
-- 
-- 
--
--
--
--
--

-- DROP TABLE localidad;
-- SELECT * FROM localidad;

CREATE TABLE localidad (
	id_localidad			INTEGER NOT NULL,
	descripcion				VARCHAR(100) NOT NULL,
	provincia				VARCHAR(50) NOT NULL,
	cp						VARCHAR(10));

INSERT INTO localidad VALUES(1,'Santa Fe', 'Santa Fe', '3000');
INSERT INTO localidad VALUES(2,'Santo Tome', 'Santa Fe', '');
INSERT INTO localidad VALUES(3,'Recreo', 'Santa Fe', '');
INSERT INTO localidad VALUES(4,'Paraná', 'Entre Ríos', '3100');
INSERT INTO localidad VALUES(5,'Concordia', 'Entre Ríos', '');

ALTER TABLE localidad OWNER TO dba_inmobiliaria;
GRANT ALL ON localidad TO dba_inmobiliaria;
GRANT ALL ON localidad TO usuario_inmobiliaria;
--REVOKE ALL ON localidad TO public;


ALTER TABLE localidad ADD PRIMARY KEY (id_localidad);



-- Modificaciones --------------------------------------------------------------


-- índices ---------------------------------------------------------------------
CREATE INDEX localidad_idx1 ON localidad (descripcion);
