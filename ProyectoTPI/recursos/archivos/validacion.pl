% Regla: credenciales validas si coinciden legajo y hash
credenciales_validas(LegajoIngresado, HashIngresado, LegajoUsuario, HashUsuario) :-
    LegajoIngresado = LegajoUsuario,
    HashIngresado = HashUsuario.