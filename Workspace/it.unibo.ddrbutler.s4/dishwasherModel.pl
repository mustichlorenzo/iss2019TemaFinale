%%STATE OF THE MAITRE

state(dishwasher, idle, unknown). %%initial state

action(dishwasher, put, QNT) :- changeModelDishwasher(dishwasher, put, QNT).
action(dishwasher, take, QNT) :- changeModelDishwasher(dishwasher, take, QNT).

changeModelDishwasher(NAME, VALUE, QNT) :- 
	replaceRule(state(NAME, _, _), state(NAME, VALUE, QNT)).