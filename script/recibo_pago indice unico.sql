CREATE UNIQUE INDEX recibo_pago_idx1 ON recibo_pago (numero);

select numero, count(*), max(id_recibo_pago)
from recibo_pago
group by numero
having count(*) > 1
order by count(*) desc

-- update recibo_pago
set numero = numero + 10000000
where id_recibo_pago in (
	select max(id_recibo_pago)
	from recibo_pago
	group by numero
	having count(*) > 1 )
	
-- update recibo_pago
set numero = numero + 20000000
where id_recibo_pago in (
	select max(id_recibo_pago)
	from recibo_pago
	group by numero
	having count(*) > 1 )
	
-- update recibo_pago
set numero = numero + 30000000
where id_recibo_pago in (
	select max(id_recibo_pago)
	from recibo_pago
	group by numero
	having count(*) > 1 )