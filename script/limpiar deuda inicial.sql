-- quiero limpiar las deudas anteriores al 2013

select *
from contrato_novedad_cobro cnc
left join recibo_cobro_item rci on (cnc.id_contrato_novedad_cobro = rci.id_novedad)
where periodo_anio < 2013
and id_recibo_cobro is null
order by id_contrato_novedad_cobro

-- update contrato_novedad_cobro
set monto = 0
where id_contrato_novedad_cobro in (
	select id_contrato_novedad_cobro
	from contrato_novedad_cobro cnc
	left join recibo_cobro_item rci on (cnc.id_contrato_novedad_cobro = rci.id_novedad)
	where periodo_anio < 2013
	and id_recibo_cobro is null)
	
	
-- ------------------------------------------------------------------------------------------

select *
from contrato_novedad_pago cnp
left join recibo_pago_item rpi on (cnp.id_contrato_novedad_pago = rpi.id_novedad)
where periodo_anio < 2013
and id_recibo_pago is null
order by id_contrato_novedad_pago

-- update contrato_novedad_pago
set monto = 0
where id_contrato_novedad_pago in (
	select id_contrato_novedad_pago
	from contrato_novedad_pago cnp
	left join recibo_pago_item rpi on (cnp.id_contrato_novedad_pago = rpi.id_novedad)
	where periodo_anio < 2013
	and id_recibo_pago is null
)