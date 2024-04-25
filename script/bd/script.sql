-- 29/09/2016
CREATE INDEX contrato_novedad__idx4
   ON contrato_novedad_cobro (fecha_vencimiento ASC NULLS LAST);

   
-- 14/04/2024
-- Nueva columna
alter table contrato
add column meses_revision smallint NULL;

-- Actualizo los INDICES de la tabla CONTRATO
drop index contrato_idx2;

CREATE INDEX contrato_idx2
   ON contrato (fecha_desde ASC NULLS LAST);
   
CREATE INDEX contrato_idx3
   ON contrato (fecha_hasta ASC NULLS LAST);
   
CREATE INDEX contrato_idx4
   ON contrato (fecha_rescision ASC NULLS LAST);