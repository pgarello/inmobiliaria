-- User: "dba_inmobiliaria"

-- DROP USER dba_inmobiliaria;

CREATE USER dba_inmobiliaria
  WITH SYSID 102
  ENCRYPTED PASSWORD 'md5551533c5fe296ab67fc92527b7ebecfc'
  CREATEDB CREATEUSER;

CREATE USER usuario_inmobiliaria PASSWORD 'acuario';




-- Database: inmobiliaria

-- DROP DATABASE inmobiliaria;

CREATE DATABASE inmobiliaria
  WITH OWNER = dba_inmobiliaria
       --ENCODING = 'LATIN1'
       TABLESPACE = pg_default;
GRANT ALL ON DATABASE inmobiliaria TO public;
GRANT ALL ON DATABASE inmobiliaria TO dba_inmobiliaria;


-- Limpieza de datos de un backup
begin;

SET CONSTRAINTS ALL DEFERRED;

delete from recibo_cobro_item;
delete from recibo_cobro;

delete from recibo_pago_item;
delete from recibo_pago;

delete from contrato_actor;
delete from contrato_novedad_cobro;
delete from contrato_novedad_factura;
delete from contrato_novedad_pago;
delete from contrato;

commit;
rollback;  