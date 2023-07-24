<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Horarios - Lista</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style2.css">
</head>
<body class="home">
<jsp:include page="${pageContext.request.contextPath}/components/header2.jsp" />
<main>
    <div class="window">
        <a class="btn_window" href="/horario/nuevo">Nuevo +</a>
        <a class="btn_window"  href="/horario/lista">Lista </a>
    </div>
    <table>
        <thead>
        <tr>
            <th>Dia</th>
            <th>Hora Ingreso</th>
            <th>Hora Salida</th>
            <th>Medico-</th>
            <th>Consultorio</th>
            <th>Editar</th>
            <th>Borrar</th>
        </tr>
        </thead>
        <tbody>
        ${horarios}
        </tbody>
    </table>
</main>
</body>
</html>
