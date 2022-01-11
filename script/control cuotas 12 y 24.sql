select *
from contrato
where fecha_desde < now() and fecha_hasta > now() -- 188

select cnc.* --sum(cnc.monto)
from contrato
join contrato_novedad_cobro as cnc using(id_contrato)
where id_contrato = 1538
and id_novedad_tipo = 1

select p.apellido || ', ' || p.nombres as Propietario, i.direccion_calle, i.direccion_nro,
direccion_piso, direccion_dpto, coalesce (e.descripcion,'') AS EDIFICIO
from contrato c 
	join inmueble as i on(c.id_inmueble = i.id_inmueble)
		left join edificio as e on (i.id_edificio = e.id_edificio)
		join persona as p on (i.id_propietario = p.id_persona)
where id_contrato in (
    select id_contrato
    from contrato
    join contrato_novedad_cobro as cnc using(id_contrato)
    where fecha_desde < now() and fecha_hasta > now() -- 188
    and id_novedad_tipo = 1
    and fecha_vencimiento >= '2021-10-01' and fecha_vencimiento < '2021-11-01'
    and contrato_cuota in (12,24)
)

select p.apellido || ', ' || p.nombres as Propietario, i.direccion_calle, i.direccion_nro,
direccion_piso as piso, direccion_dpto as dpto, 
coalesce (e.descripcion,'') AS EDIFICIO, contrato_cuota as cuota
from contrato c 
	join inmueble as i on(c.id_inmueble = i.id_inmueble)
		left join edificio as e on (i.id_edificio = e.id_edificio)
		join persona as p on (i.id_propietario = p.id_persona)
	join contrato_novedad_cobro as cnc on (c.id_contrato = cnc.id_contrato) 
where fecha_desde < now() and fecha_hasta > now() -- 188
    and id_novedad_tipo = 1
    and fecha_vencimiento >= '2021-09-01' and fecha_vencimiento < '2021-10-01'
    and contrato_cuota in (12,24)

