-- CONTRATOS VIGENTES
select *
from contrato
where fecha_desde <= now()
and fecha_hasta >= now() -- 151
and fecha_rescision is null -- 139

-- cobranza "CONTRATOS CON DEUDA"
select 
p.apellido || ', ' || p.nombres as "PROPIETARIO", 
p2.apellido || ', ' || p2.nombres as "INQUILINO", 
i.direccion_calle || ' / ' || i.direccion_nro || ' / ' || i.direccion_piso || ' / ' || i.direccion_dpto as "INMUEBLE",
cnc.monto
,*
from contrato as c
    join inmueble as i using(id_inmueble)
    join contrato_novedad_cobro as cnc using(id_contrato) -- 4534
        left join recibo_cobro_item as rci on(cnc.id_contrato_novedad_cobro = rci.id_novedad)
    join contrato_actor as ca on(c.id_contrato = ca.id_contrato and ca.id_actor_tipo = 2 and ca.fecha_baja is null) -- 'PROPIETARIO'
        join persona as p on(ca.id_persona = p.id_persona)
    join contrato_actor as ca2 on(c.id_contrato = ca2.id_contrato and ca2.id_actor_tipo = 1 and ca2.fecha_baja is null) -- 'INQUILINO'
        join persona as p2 on(ca2.id_persona = p2.id_persona)
where fecha_desde <= now()
    and fecha_hasta >= now() -- 151
    and fecha_rescision is null -- 139
    and rci.id_recibo_cobro_item is null -- 2943
    and cnc.fecha_vencimiento <= '2022-01-01' -- 1274
    and cnc.id_novedad_tipo = 1 -- 807
    and periodo_mes = 6 and periodo_anio = 2021
order by cnc.monto desc -- 9
limit 10



