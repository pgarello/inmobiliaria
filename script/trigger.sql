CREATE SEQUENCE public.log_contrato_novedad_cobro_id_seq;

CREATE TABLE log_contrato_novedad_cobro  ( 

	id_log_contrato_novedad_cobro	int4 NOT NULL,
    operacion varchar(10) NOT NULL,
    fecha timestamp NOT NULL,

    id_contrato_novedad_cobro int4 NOT NULL, 
	id_contrato              	int4 NOT NULL,
	id_persona               	int4 NOT NULL,
	id_novedad_tipo          	int2 NOT NULL,
	periodo_mes              	int2 NULL,
	periodo_anio             	int2 NULL,
	contrato_cuota           	int2 NULL,
	monto                    	numeric(18,2) NULL,
	fecha_vencimiento        	date NULL,
	fecha_alta               	timestamp NOT NULL,
	descripcion              	varchar(200) NULL,
	id_impuesto              	int2 NULL,
	impuesto_cuota           	int2 NULL,
	impuesto_anio            	int2 NULL,
	PRIMARY KEY(id_log_contrato_novedad_cobro)
);

CREATE OR REPLACE FUNCTION "public"."sp_contrato_novedad_cobro" () RETURNS trigger AS
$BODY$BEGIN

        IF (TG_OP = 'DELETE') THEN
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO "log_contrato_novedad_cobro" 
                SELECT nextval('public."log_contrato_novedad_cobro_id_seq"'),  
                'UPDATE', 
                now(), 
                NEW.id_contrato_novedad_cobro, 
                NEW.id_contrato,
                NEW.id_persona,
                NEW.id_novedad_tipo,
                NEW.periodo_mes,
                NEW.periodo_anio,
                NEW.contrato_cuota,
                NEW.monto,
                NEW.fecha_vencimiento,
                NEW.fecha_alta,
                NEW.descripcion,
                NEW.id_impuesto,
                NEW.impuesto_cuota,
                new.impuesto_anio ;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$BODY$
LANGUAGE 'plpgsql';

CREATE TRIGGER "trigger_contrato_novedad_cobro"
	 AFTER UPDATE ON contrato_novedad_cobro FOR EACH ROW
	 EXECUTE PROCEDURE sp_contrato_novedad_cobro();