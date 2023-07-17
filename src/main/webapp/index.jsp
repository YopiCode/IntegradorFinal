<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="resources/css/style.css">
    <title>Medicheck</title>
</head>
<body>
<%@include file="components/header.jsp"%>
<main class="logins">
    <div class="img_fondos">
        <img src="resources/img/imgLoginPaciente.jpg" alt="loginPaciente">
        <div class="fondo_gris"></div>
    </div>
    <div class="card_form">
        <div class="card_form_absolute">
            <div class="titulo_card"><p>INICIAR SESIÓN</p></div>
            <form action="login" class="formulario" method="post">
                <div class="form_campos">
                    <label for="correo" class="form_label">Ingrese Correo</label>
                    <input type="text" id="correo" name="correo" class="form_input">
                </div>
                <div class="form_campos">
                    <label for="pass" class="form_label">Ingrese Contraseña</label>
                    <input type="password" id="pass" name="contrasena" class="form_input">
                </div>
                <button type="submit" class="btn_login">Ingresar</button>
            </form>
        </div>
    </div>
</main>
</body>
</html>