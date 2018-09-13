-- Table: persona_tipo

-- comentario: Es el tipo de persona ante la ley?
-- 1: FISICA
-- 2: JURIDICA
-- 
--
--
--
--
--

-- DROP TABLE persona_tipo;
-- SELECT * FROM persona_tipo;

CREATE TABLE persona_tipo (
	id_persona_tipo			INTEGER NOT NULL,
	descripcion				VARCHAR(30) NOT NULL
);

--INSERT INTO persona VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE persona_tipo OWNER TO dba_inmobiliaria;
GRANT ALL ON persona_tipo TO dba_inmobiliaria;
GRANT ALL ON persona_tipo TO public;


ALTER TABLE persona_tipo
	ADD PRIMARY KEY (
		id_persona_tipo);




-- Foreign Key: 


-- Modificaciones: --------------------------------------------------------


-- índices: ---------------------------------------------------------------
