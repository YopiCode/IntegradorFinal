<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lista</title>
    <link rel="stylesheet" href="../../resources/css/style.css">
</head>
<body>
<%@include file="../../components/header.jsp" %>
<main>
    <table>
        <thead>
        <tr>
            <th>Medico</th>
            <th>Fecha</th>
            <th>hora</th>
            <th>Generar</th>
            <th>Cancelar</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            ${citas}
        </tr>
        </tbody>
    </table>
</main>
</body>
</html>
