-- Table: USUARIO

-- comentario: Usuarios registrados en el sistema
--
--
--


-- DROP TABLE usuario;
-- SELECT * FROM usuario;

CREATE TABLE usuario (
  id_usuario 		INTEGER NOT NULL DEFAULT nextval('public.usuario_id_seq'::text),
  usuario 			VARCHAR(20) NOT NULL,
  clave 			VARCHAR(20) NOT NULL,
  descripcion 		VARCHAR(100)
);


ALTER TABLE usuario OWNER TO dba_inmobiliaria;
GRANT ALL ON usuario TO dba_inmobiliaria;
GRANT ALL ON usuario TO usuario_inmobiliaria;
GRANT ALL ON usuario TO public;


ALTER TABLE usuario
	ADD PRIMARY KEY (
		id_usuario);


-- Sequence: usuario_id_seq

-- DROP SEQUENCE usuario_id_seq;

CREATE SEQUENCE usuario_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 16
  CACHE 1;
ALTER TABLE usuario_id_seq OWNER TO dba_inmobiliaria;



-- ************************************************************************
-- Foreign Key: 


-- ************************************************************************

-- Modificaciones
ALTER TABLE usuario ADD COLUMN administrador BOOLEAN;


-- índices ---------------------------------------------------------------------
CREATE INDEX usuario_idx_usuario ON usuario (usuario);
