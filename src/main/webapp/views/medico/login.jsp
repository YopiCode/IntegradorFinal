<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MediCheck - Medico</title>
    <link rel="stylesheet" href="../../resources/css/style.css">
</head>
<body>
<header>
    <div>
        <a href="/medico/login"><img src="../../resources/img/logo.png" alt="logo"></a>
        <h1>MEDICHECK</h1>
    </div>
</header>

<main class="logins">
    <div class="img_fondos">
        <img src="../../resources/img/imgLoginMedico.jpg" alt="loginPaciente">
        <div class="fondo_gris"></div>
    </div>
    <div class="card_form">
        <div class="card_form_absolute">
            <div class="titulo_card"><p>INICIAR SESIÓN</p></div>
            <form action="/loginMedico" class="formulario" method="post">
                <div class="form_campos">
                    <label for="correo" class="form_label">Ingrese Usuario</label>
                    <input type="text" id="correo" name="usuario" class="form_input">
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
