select
	*
from
	menus as P
left join orders_menus as O 
	on P.id = O.menus_id
where
	order_id = 1;