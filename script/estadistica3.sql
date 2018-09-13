select *
from contrato
where fecha_desde < now() and fecha_hasta > now() --1
and fecha_rescision is null --2
and comision_prop_porc > 0 --3

-- 30/09/2015		20/12/2015					26/05/2016		18/10/2016
-- 1 252 registros	257							267				268
-- 2 216 registros	218							224				227
-- 3 196 registros	198 (sobre estos se cobra)	203				206

-- Suma de lo que se cobró de comisiones de contrato en un período de tiempo
select sum(monto)
from recibo_cobro as rc
join recibo_cobro_item as rci on (rc.id_recibo_cobro = rci.id_recibo_cobro)
where fecha_emision >= '2016-01-01' and fecha_emision < '2017-01-01'
and id_item_tipo = 3 -- comision contrato

-- 2013 $  65.753,74
-- 2014 $ 211.552,45
-- 2015 $ 329.253,88
-- 2016 $ 203.710,51


select sum(monto) * -1
from recibo_pago as rc
join recibo_pago_item as rci on (rc.id_recibo_pago = rci.id_recibo_pago)
where fecha_emision >= '2015-01-01' and fecha_emision < '2016-01-01'
and id_item_tipo = 2 -- comision alquiler

-- 2013 $ 194.127,84
-- 2014 $ 443.957,67
-- 2015 $ 565.168,85
-- 2016 $ 263.492,43