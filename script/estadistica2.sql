-- Alquileres que se pagaron y no se cobraron
select rpi.monto, cnp.monto, cnc.monto, p.apellido, i.direccion_calle, i.direccion_nro, c.id_contrato
from recibo_pago rp
  join recibo_pago_item rpi using (id_recibo_pago)
    join contrato_novedad_pago cnp on (rpi.id_novedad = cnp.id_contrato_novedad_pago)
      join contrato_novedad_cobro cnc on (cnp.id_contrato = cnc.id_contrato and
					  cnp.periodo_mes = cnc.periodo_mes and
					  cnp.periodo_anio = cnc.periodo_anio and
					  cnp.id_novedad_tipo = cnc.id_novedad_tipo)
        left join recibo_cobro_item rci on (rci.id_novedad = cnc.id_contrato_novedad_cobro)
      
      join contrato c on (cnp.id_contrato = c.id_contrato)
	      join inmueble as i on(c.id_inmueble = i.id_inmueble)
			join persona as p on (i.id_propietario = p.id_persona)
where --rp.fecha_emision > '2014-04-30' 
cnp.periodo_anio = 2014 and cnp.periodo_mes = 6
and rpi.id_item_tipo = 1
and rci.id_recibo_cobro is null
order by p.apellido --periodo_anio, periodo_mes, rpi.monto
--limit 10

-- crear los siguientes índices para acelerar la búsqueda
CREATE INDEX recibo_cobro_item_idx1 ON recibo_cobro_item (id_novedad);
CREATE INDEX contrato_novedad_cobro_idx2 ON contrato_novedad_cobro (id_contrato);
CREATE INDEX recibo_pago_item_idx1 ON recibo_pago_item (id_recibo_pago);
CREATE INDEX recibo_pago_item_idx2 ON recibo_pago_item (id_item_tipo);
CREATE INDEX recibo_pago_idx2 ON recibo_pago (fecha_emision);

select * 
from contrato 
join inmueble as i using(id_inmueble)
join persona as p on (i.id_propietario = p.id_persona)
where id_contrato = 460