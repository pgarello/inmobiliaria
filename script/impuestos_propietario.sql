-- PROPIETARIOS en los que difiere lo que se le cobra por comisión
select count(*), id_propietario
from contrato
join inmueble using(id_inmueble)
group by id_propietario
having max(comision_prop_porc) > min(comision_prop_porc)
order by id_propietario

-- Arreglo de datos para impuestos
select * from contrato_novedad_pago where /*id_persona = 277 and*/ id_novedad_tipo = 5

-- update contrato_novedad_pago 
set monto = monto * -1
where id_persona = 277 and id_novedad_tipo = 5