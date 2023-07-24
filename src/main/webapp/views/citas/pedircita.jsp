<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Pedir Cita</title>
    <link rel="stylesheet" href="../../resources/css/style.css">
</head>
<body>
<%@include file="../../components/header.jsp" %>
<main>
    <div class="datos_paciente">
        <table>
            <tbody>
                <tr>
                    <td>Nombre: </td>
                    <td class="text-gris">${paciente.nombre}</td>
                    <td>Sexo: </td>
                    <td class="text-gris">${paciente.sexo}</td>
                </tr>
                <tr>
                    <td>DMO: </td>
                    <td class="text-gris">${paciente.dni}</td>
                    <td>Edad: </td>
                    <td class="text-gris">${paciente.edad}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <hr>
    <div class="form_cita">
        <form action="/registrarCita" method="post">
            <div class="form_pedir_cita">
                <h2>Especialidad</h2>
                ${especialidades}
            </div>
            <div class="form_pedir_cita">
                <h2>Medico</h2>
                ${medicos}
            </div>
            <div class="form_pedir_cita">
                <h2>Fecha</h2>
                ${fechas}
            </div>
            <div class="form_pedir_cita">
                <h2>hora</h2>
                ${horas}
            </div>
            <div class="botones">
                <a href="/paciente/perfil" class="btn_cancelar">Cancelar</a>
            </div>
        </form>
    </div>
</main>
</body>
</html>
