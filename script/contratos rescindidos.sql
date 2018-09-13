-- Controla los contratos rescindidos que tengan movimientos posteriores a la fecha

select *
from contrato
join contrato_novedad_pago as cnp using(id_contrato)
join inmueble using(id_inmueble)
where fecha_rescision is not null
and cnp.fecha_liquidacion > fecha_rescision