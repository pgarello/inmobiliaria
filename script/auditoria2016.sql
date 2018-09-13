---------------------------------------------------------------------
-- propietarios
---------------------------------------------------------------------
select /*periodo_mes, periodo_anio*/ id_novedad_tipo, nt.descripcion ,sum(monto) * -1
from contrato_novedad_pago as cnp
join novedad_tipo as nt using(id_novedad_tipo)
where --fecha_liquidacion > '2016-01-31' and fecha_liquidacion < '2016-03-01' 
/*id_novedad_tipo = 2 and*/ periodo_anio = 2016 and periodo_mes = 1
group by id_novedad_tipo --periodo_mes, periodo_anio
order by id_novedad_tipo --periodo_anio, periodo_mes

-- LO QUE COBRE REALMENTE
select id_novedad_tipo, nt.descripcion, sum(rpi.monto) * -1
from recibo_pago_item as rpi
	join contrato_novedad_pago as cnp on (rpi.id_novedad = cnp.id_contrato_novedad_pago)
		join novedad_tipo as nt using(id_novedad_tipo)
where periodo_anio = 2016 and periodo_mes = 1 and id_item_tipo <> 6 /*and id_item_tipo = 2*/ -- COMISION DE ALQUILER
group by id_novedad_tipo --periodo_mes, periodo_anio
order by id_novedad_tipo --periodo_anio, periodo_mes

---------------------------------------------------------------------
-- inquilinos
---------------------------------------------------------------------

-- total para cobrar
select cnc.id_novedad_tipo, max(nt.descripcion) ,sum(monto)
from contrato_novedad_cobro as cnc
join novedad_tipo as nt using(id_novedad_tipo)
where fecha_vencimiento > '2015-12-31' and fecha_vencimiento < '2016-02-01'
group by cnc.id_novedad_tipo

-- total cobrado
select
cnc.id_novedad_tipo, max(nt.descripcion) ,sum(rci.monto)
from contrato_novedad_cobro as cnc
join novedad_tipo as nt using(id_novedad_tipo)
	join recibo_cobro_item as rci on (rci.id_novedad = cnc.id_contrato_novedad_cobro)
		join recibo_cobro as rc on (rci.id_recibo_cobro = rc.id_recibo_cobro )
where fecha_vencimiento > '2015-12-31' and fecha_vencimiento < '2016-02-01'
/*and cnc.id_novedad_tipo = 3*/ and id_item_tipo <> 6
group by cnc.id_novedad_tipo