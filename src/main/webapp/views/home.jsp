<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inicio</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style2.css">
</head>
<body class="home">
<jsp:include page="${pageContext.request.contextPath}/components/header2.jsp" />
<main>
    <div class="cards_home">
        <div>
            <a class="card_home" href="/paciente/nuevo">
                <p>Pacientes</p>
                <img src="../resources/img/imgPacientes.png" alt="">
            </a>
            <a class="card_home" href="/especialidad/nuevo">
                <p>Especialidades</p>
                <img src="../resources/img/imgEspecialidades.png" alt="">
            </a>
        </div>
        <div>
            <a class="card_home" href="/medico/nuevo">
                <p>Medicos</p>
                <img src="../resources/img/imgMedicos.png" alt="">
            </a>
            <a class="card_home" href="/horario/nuevo">
                <p>Horario</p>
                <img src="../resources/img/listaCitas.png" alt="">
            </a>
        </div>

    </div>
</main>
</body>
</html>
