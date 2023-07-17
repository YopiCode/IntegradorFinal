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
            <div class="form_campos_registro">
                <label for="medico" class="form_label">Medico: </label>
                <select name="idMedico" id="medico" class="form_select">
                    ${medicos}
                </select>
            </div>
            <div class="form_campos_registro">
                <label for="fecha" class="form_label">Fecha: </label>
                <input name="fecha" type="date" id="fecha" class="form_select" />
            </div>
            <div class="form_campos_registro">
                <label for="hora" class="form_label">hora: </label>
                <input name="hora" type="time" id="hora" class="form_select" />
            </div>
            <div class="botones">
                <button type="submit" class="btn_submit">Registrar</button>
                <a href="/paciente/perfil" class="btn_cancelar">Cancelar</a>
            </div>
        </form>
    </div>
</main>
</body>
</html>
