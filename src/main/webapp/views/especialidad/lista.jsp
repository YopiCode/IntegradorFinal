<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Especialidad - Lista</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style2.css">
</head>
<body class="home">
<jsp:include page="${pageContext.request.contextPath}/components/header2.jsp" />
<main>
    <div class="window">
        <a class="btn_window" href="/especialidad/nuevo">Nuevo +</a>
        <a class="btn_window"  href="/especialidad/lista">Lista </a>
    </div>

        <table>
            <thead>
            <tr>
                <th>Especialidad</th>
                <th>Editar</th>
                <th>Borrar</th>
            </tr>
            </thead>
            <tbody>
                ${especialidades}
            </tbody>
        </table>
</main>
</body>
</html>
