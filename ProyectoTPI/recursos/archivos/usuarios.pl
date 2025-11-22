% Base de conocimiento de usuarios UTN FRVM
% usuario(legajo, nombre, password, rol)

usuario('12345', 'Juan Perez', 'pass123', administrador).
usuario('67890', 'Maria Lopez', 'pass456', docente).
usuario('11111', 'Carlos Gomez', 'pass789', alumno).
usuario('22222', 'Ana Martinez', 'pass111', alumno).

% Regla: Validar usuario
validar_usuario(Legajo, Password, Nombre, Rol) :-
    usuario(Legajo, Nombre, Password, Rol).

% Regla: Obtener usuarios por rol
usuarios_por_rol(Rol, Legajo, Nombre) :-
    usuario(Legajo, Nombre, _, Rol).

% Permisos por rol
permiso(administrador, crear_curso).
permiso(administrador, eliminar_curso).
permiso(administrador, gestionar_usuarios).
permiso(docente, crear_curso).
permiso(docente, calificar).
permiso(alumno, inscribirse).
permiso(alumno, ver_notas).

% Verificar permisos
tiene_permiso(Legajo, Accion) :-
    usuario(Legajo, _, _, Rol),
    permiso(Rol, Accion).