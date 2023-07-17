<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Horario - Editar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style2.css">
</head>
<body class="home">
<jsp:include page="${pageContext.request.contextPath}/components/header2.jsp"/>
<main>
    <div class="window">
        <a class="btn_window activate" href="/horario/nuevo">Nuevo +</a>
        <a class="btn_window"  href="/horario/lista">Lista </a>
    </div>
    <form action="/updateHorario" method="post">
        <div class="form_campos_registro">
            <label for="dia" class="form_label">Dia: </label>
            <input type="date" name="dia" id="dia" value="${horario.dia}">
        </div>
        <div class="form_campos_registro">
            <label for="horaIngreso" class="form_label">Hora Ingreso: </label>
            <input type="time" name="horaIngreso" id="horaIngreso" value="${horario.horaIngreso}">
        </div>
        <div class="form_campos_registro">
            <label for="horaSalida" class="form_label">Hora Salida: </label>
            <input type="time" name="horaSalida" id="horaSalida" value="${horario.horaSalida}">
        </div>
        <div class="form_campos_registro">
            <label for="medico" class="form_label">Medico</label>
            <select name="idMedico" id="medico" class="form_select">
                ${medicos}
            </select>
        </div>
        <div class="form_campos_registro">
            <label for="consultorio" class="form_label">Consultorio: </label>
            <select name="idConsultorio" id="consultorio" class="form_select">
                ${consultorios}
            </select>
        </div>
        <div class="form_campos_registro">
            <label for="idTurno" class="form_label">Turno: </label>
            <select name="idTurno" id="idTurno" class="form_select">
                ${turnos}
            </select>
        </div>
        <div class="botones">
            <button type="submit" class="btn_submit" name="id" value="${horario.id}">Editar</button>
            <a href="/home" class="btn_cancelar">Cancelar</a>
        </div>
    </form>
</main>
</body>
</html>
