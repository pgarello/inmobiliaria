-- 29/09/2016
CREATE INDEX contrato_novedad__idx4
   ON contrato_novedad_cobro (fecha_vencimiento ASC NULLS LAST);
