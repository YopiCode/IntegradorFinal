<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Citas Medicas de Hoy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style2.css">
</head>
<body class="home">
<jsp:include page="${pageContext.request.contextPath}/components/header2.jsp" />
<main>
    <form action="cancelarCita" method="post">
        <table>
            <thead>
            <tr>
                <th>Medico</th>
                <th>Fecha</th>
                <th>hora</th>
            </tr>
            </thead>
            <tbody>
                ${citasHoy}
            </tbody>
        </table>
    </form>

    <form action="/generarReporte" method="get">
        <button type="submit" class="btnReporte">Generar Reporte</button>
    </form>

</main>
</body>
</html>
