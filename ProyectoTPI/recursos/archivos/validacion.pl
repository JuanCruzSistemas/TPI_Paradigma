% Los datos son correctos si coinciden con los hash guardados
credenciales_validas(LegajoIngresado, HashIngresado, LegajoUsuario, HashUsuario) :-
    LegajoIngresado = LegajoUsuario,
    HashIngresado = HashUsuario.