<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Medico - Editar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style2.css">
</head>
<body class="home">
<jsp:include page="${pageContext.request.contextPath}/components/header2.jsp"/>
<main>
    <div class="window">
        <a class="btn_window activate" href="/medico/nuevo">Nuevo +</a>
        <a class="btn_window"  href="/medico/lista">Lista </a>
    </div>
    <form action="/updateMedico" method="post">
        <div class="form_campos_registro">
            <label for="nombre" class="form_label">Nombre: </label>
            <input type="text" name="nombre" id="nombre" value="${medico.nombre}">
        </div>
        <div class="form_campos_registro">
            <label for="apellidoP" class="form_label">A. Paterno: </label>
            <input type="text" name="apellidoP" id="apellidoP" value="${medico.apellidoP}">
        </div>
        <div class="form_campos_registro">
            <label for="apellidoM" class="form_label">A. Materno: </label>
            <input type="text" name="apellidoM" id="apellidoM" value="${medico.apellidoM}">
        </div>
        <div class="form_campos_registro">
            <label for="dni" class="form_label">DNI: </label>
            <input type="text" name="dni" id="dni" value="${medico.dni}">
        </div>
        <div class="form_campos_registro">
            <label for="telefono" class="form_label">Telefono: </label>
            <input type="text" name="telefono" id="telefono" value="${medico.telefono}">
        </div>
        <div class="form_campos_registro">
            <label for="especialidad" class="form_label">Especialdad</label>
            <select name="idEspecialidad" id="especialidad" class="form_select">
                ${especialidades}
            </select>
        </div>
        <div class="form_campos_registro">
            <label for="direccion" class="form_label">Direccion: </label>
            <input type="direccion" name="direccion" id="direccion" value="${medico.direccion}">
        </div>
        <div class="form_campos_registro">
            <label for="usuario" class="form_label">Usuario: </label>
            <input type="usuario" name="usuario" id="usuario" value="${medico.usuario}">
        </div>
        <div class="form_campos_registro">
            <label for="contrasena" class="form_label">Contraseña: </label>
            <input type="password" name="contrasena" id="contrasena" value="${medico.contrasena}">
        </div>
        <div class="botones">
            <button type="submit" class="btn_submit" name="id" value="${medico.id}">Editar</button>
            <a href="/home" class="btn_cancelar">Cancelar</a>
        </div>
    </form>
</main>
</body>
</html>
