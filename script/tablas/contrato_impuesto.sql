-- Tabla: contrato_impuesto

-- Representa en que forma se pagaran los impuestos de la propiedad mientras dure
-- el contrato.
-- Pueden ser 100% el inquilino o propietario / o 50% cada uno
-- También queda asentado que impuestos seran tenidos en cuenta en el contrato
--
-- impuestos:	TASA MUNICIPAL			1
--				AGUA					2
--				IMPUESTO INMOBILIARIO	3


-- DROP TABLE contrato_impuesto;
SELECT * FROM contrato_impuesto;

CREATE TABLE contrato_impuesto (
	id_contrato 			INTEGER NOT NULL,
	id_impuesto				SMALLINT NOT NULL,
	porcentaje_inquilino 	SMALLINT NOT NULL,
	porcentaje_propietario	SMALLINT NOT NULL
);

--INSERT INTO contrato VALUES(1,'Garello', 'Pablo Andres', 0, 23726150, null, '0', '+', '1974-03-22', 'M', 'Domingo Silva', '1991', now(), '', '');

ALTER TABLE contrato_impuesto OWNER TO dba_inmobiliaria;
GRANT ALL ON contrato_impuesto TO dba_inmobiliaria;
GRANT ALL ON contrato_impuesto TO usuario_inmobiliaria;
GRANT ALL ON contrato_impuesto TO public;


ALTER TABLE contrato ADD PRIMARY KEY (id_contrato, id_impuesto);


-- Foreign Key: contrato_impuesto__contrato

-- ALTER TABLE contrato_impuesto DROP CONSTRAINT contrato_impuesto__contrato;

ALTER TABLE contrato_impuesto
  ADD CONSTRAINT contrato_impuesto__contrato FOREIGN KEY (id_contrato) REFERENCES contrato (id_contrato) ON UPDATE NO ACTION ON DELETE NO ACTION;




-- Modificaciones ---------------------------------------------------------------------


-- índices ---------------------------------------------------------------------
CREATE INDEX contrato_idx1 ON contrato (id_inmueble);
