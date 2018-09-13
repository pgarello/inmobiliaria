select *
from contrato
where fecha_desde < now() and fecha_hasta > now()

-- CONTRATOS MAL CARGADOS !!! 11 contratos
select *
from contrato_novedad_pago 
where periodo_mes = 6 and periodo_anio = 2013
and id_novedad_tipo = 2 and monto = 0


-- SUMATORIA DE COMISIONES $ 24922.20 - lo que debería haber cobrado
select sum(monto)
from contrato_novedad_pago 
where periodo_mes = 6 and periodo_anio = 2013
and id_novedad_tipo = 2
group by id_novedad_tipo

-- Lo que cobré realmente
select sum(rpi.monto)
from recibo_pago_item as rpi
	join contrato_novedad_pago as cnp on (rpi.id_novedad = cnp.id_contrato_novedad_pago)
where periodo_mes = 2 and periodo_anio = 2016
and id_item_tipo = 2
group by id_item_tipo

-- TODO EN UNO y las diferencias
select periodo_mes, periodo_anio, 
	(sum(rpi.monto) * -1) as cobrado, 
	(sum(cnp.monto)*-1) as cobrar, 
	(sum(rpi.monto) - sum(cnp.monto)) as dif
from contrato_novedad_pago as cnp
	left join recibo_pago_item as rpi on (cnp.id_contrato_novedad_pago = rpi.id_novedad and id_item_tipo = 2)
where id_novedad_tipo = 2 -- COMISION ALQUILER
--and periodo_mes = 6 and periodo_anio = 2013 
group by periodo_mes, periodo_anio
having sum(rpi.monto) <> 0 --and periodo_mes = 6 and periodo_anio = 2013 
order by periodo_anio, periodo_mes

-- Que me muestre el ingreso por comisiones de alquiler
select periodo_mes, periodo_anio, sum(monto)
from contrato_novedad_pago 
where id_novedad_tipo = 2
group by periodo_mes, periodo_anio
order by periodo_anio, periodo_mes


-- lo que se tenía que liquidar y no se liquido (NOVEDADES vs LIQUIDACIONES)
select sum(cnp.monto), 
sum(rpi.monto)
from contrato_novedad_pago as cnp
left join recibo_pago_item as rpi on (rpi.id_novedad = cnp.id_contrato_novedad_pago and rpi.id_item_tipo = cnp.id_novedad_tipo )
where periodo_mes = 5 and periodo_anio = 2014
and id_novedad_tipo = 2 -- id_item_tipo = 2 
and id_recibo_pago is null


-- Cuanto COBRE recibo cobranza
select sum(rpi.monto)--,id_item_tipo
from recibo_cobro_item as rpi
	left join contrato_novedad_cobro as cnp on (rpi.id_novedad = cnp.id_contrato_novedad_cobro)
where periodo_mes = 5 and periodo_anio = 2014
--and id_item_tipo = 2
group by id_item_tipo

-- NECESITO SABER QUE PAGUE Y NO COBRE


-- INFORME CONTADORA OCTUBRE 2016
-- 01-06-2015 al 31-05-2016

-- total para cobrar
select cnc.id_novedad_tipo, max(nt.descripcion) ,sum(monto)
from contrato_novedad_cobro as cnc
join novedad_tipo as nt using(id_novedad_tipo)
where fecha_vencimiento > '2016-01-31' and fecha_vencimiento < '2016-03-01'
group by cnc.id_novedad_tipo

-- No cobrado nada
select cnc.id_novedad_tipo, max(nt.descripcion) ,sum(cnc.monto)
from contrato_novedad_cobro as cnc
	left join recibo_cobro_item as rci on (rci.id_novedad = cnc.id_contrato_novedad_cobro)
	join novedad_tipo as nt using(id_novedad_tipo)
	join contrato c on (cnc.id_contrato = c.id_contrato)
where fecha_vencimiento > '2016-01-31' and fecha_vencimiento < '2016-03-01'
and id_recibo_cobro_item is null -- esto es los q no tengan ningun recibo
and c.fecha_rescision is null
group by cnc.id_novedad_tipo

-- total cobrado
select
cnc.id_novedad_tipo, max(nt.descripcion) ,sum(rci.monto)
from contrato_novedad_cobro as cnc
join novedad_tipo as nt using(id_novedad_tipo)
	join recibo_cobro_item as rci on (rci.id_novedad = cnc.id_contrato_novedad_cobro)
		join recibo_cobro as rc on (rci.id_recibo_cobro = rc.id_recibo_cobro )
where fecha_vencimiento > '2016-01-31' and fecha_vencimiento < '2016-03-01'
/*and cnc.id_novedad_tipo = 3*/ and id_item_tipo <> 6 -- saco los IVA
group by cnc.id_novedad_tipo