<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Medico - Lista</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style2.css">
</head>
<body class="home">
<jsp:include page="${pageContext.request.contextPath}/components/header2.jsp" />
<main>
    <div class="window">
        <a class="btn_window" href="/medico/nuevo">Nuevo +</a>
        <a class="btn_window"  href="/medico/lista">Lista </a>
    </div>
    <table>
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Apellido P-</th>
            <th>Apellido M-</th>
            <th>DNI</th>
            <th>TELEFONO</th>
            <th>DIRECCION</th>
            <th>Editar</th>
            <th>Borrar</th>
        </tr>
        </thead>
        <tbody>
        ${medicos}
        </tbody>
    </table>
</main>
</body>
</html>
