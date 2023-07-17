<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Perfil</title>
    <link rel="stylesheet" href="../../resources/css/style.css">
</head>
<body>
<%@include file="../../components/header.jsp" %>

<main>
    <div class="perfil">
        <div class="perfil_info">
            <img src="../../resources/img/userImg.png" alt="">
            <table>
                <tbody>
                <tr>
                    <td>Nombre:</td>
                    <td>${paciente.nombre} ${paciente.apellidoP}</td>
                </tr>
                <tr>
                    <td>DNI:</td>
                    <td>${paciente.dni}</td>
                </tr>
                <tr>
                    <td>Edad:</td>
                    <td>${paciente.edad} años</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="perfil_citas">
            <h2>Citas</h2>
            <hr>
            <div class="perfil_hrefs">
                <a class="perfil_href" href="/pedircita">
                    <img src="../../resources/img/cita.png" alt="">
                    <p>Pedir Cita</p>
                </a>
                <a class="perfil_href" href="/listacita">
                    <img src="../../resources/img/listaCitas.png" alt="">
                    <p>Ver Mis Citas</p>
                </a>
            </div>
        </div>
    </div>
</main>
</body>
</html>
