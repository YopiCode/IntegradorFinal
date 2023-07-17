<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Paaciente - Nuevo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style2.css">
</head>
<body class="home">
<jsp:include page="${pageContext.request.contextPath}/components/header2.jsp"/>
<main>
    <div class="window">
        <a class="btn_window activate" href="/paciente/nuevo">Nuevo +</a>
        <a class="btn_window"  href="/paciente/lista">Lista </a>
    </div>
    <form action="/registrarPaciente" method="post">
        <div class="form_campos_registro">
            <label for="nombre" class="form_label">Nombre: </label>
            <input type="text" name="nombre" id="nombre">
        </div>
        <div class="form_campos_registro">
            <label for="apellidoP" class="form_label">A. Paterno: </label>
            <input type="text" name="apellidoP" id="apellidoP">
        </div>
        <div class="form_campos_registro">
            <label for="apellidoM" class="form_label">A. Materno: </label>
            <input type="text" name="apellidoM" id="apellidoM">
        </div>
        <div class="form_campos_registro">
            <label for="dni" class="form_label">DNI: </label>
            <input type="text" name="dni" id="dni">
        </div>
        <div class="form_campos_registro">
            <label for="edad" class="form_label">Edad: </label>
            <input type="text" name="edad" id="edad">
        </div>
        <div class="form_campos_registro">
            <label for="sexo" class="form_label">Sexo: </label>
            <select name="sexo" id="sexo">
                <option value="M">Masculino</option>
                <option value="F">Feminino</option>
            </select>
        </div>
        <div class="form_campos_registro">
            <label for="fechaNacimiento" class="form_label">fechaNacimiento: </label>
            <input type="date" name="fechaNacimiento" id="fechaNacimiento">
        </div>
        <div class="form_campos_registro">
            <label for="telefono" class="form_label">Telefono: </label>
            <input type="tel" name="telefono" id="telefono">
        </div>
        <div class="form_campos_registro">
            <label for="direccion" class="form_label">Direccion: </label>
            <input type="direccion" name="direccion" id="direccion">
        </div>
        <div class="form_campos_registro">
            <label for="correo" class="form_label">Correo: </label>
            <input type="email" name="correo" id="correo">
        </div>
        <div class="form_campos_registro">
            <label for="contrasena" class="form_label">Contraseña: </label>
            <input type="password" name="contrasena" id="contrasena">
        </div>
        <div class="botones">
            <button type="submit" class="btn_submit">Registrar</button>
            <a href="/home" class="btn_cancelar">Cancelar</a>
        </div>
    </form>
</main>
</body>
</html>
