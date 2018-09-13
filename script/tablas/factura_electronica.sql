-- Va a resprentar todos los registros que se levantan desde la AFIP para ser informados
-- y linkeados con los datos del sistema

CREATE TABLE factura_electronica (

	id_factura_electronica	INTEGER NOT NULL,
	
	comprobante_fecha		TIMESTAMP NOT NULL,
	comprobante_tipo		SMALLINT NOT NULL, -- esta tipado VER tabla para JOIN
	comprobante_ptovta		SMALLINT NOT NULL,
	comprobante_numero		INTEGER NOT NULL,
	
	cod_documento			SMALLINT NOT NULL,
	cuit_cuil_doc			VARCHAR(13) NOT NULL,
	cliente					VARCHAR(100) NOT NULL,
	
	importe_total			DECIMAL(18, 2) NOT NULL,
	importe_neto_grav		DECIMAL(18, 2) NOT NULL, -- sobre lo que aplico IVA
	importe_no_grav			DECIMAL(18, 2) NOT NULL,
	importe_op_exentas		DECIMAL(18, 2) NOT NULL,
	importe_retencion		DECIMAL(18, 2) NOT NULL, -- es lo que se cobro de IVA
	
	alicuota_iva			DECIMAL(5 , 2) NOT NULL -- es el porcentaje 0% o 21%, puede ser con decimales a futuro
	
);

-- Sequence: factura_electronica_id_seq
-- DROP SEQUENCE factura_electronica_id_seq;
CREATE SEQUENCE factura_electronica_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE factura_electronica_id_seq OWNER TO dba_inmobiliaria;
