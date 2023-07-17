<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Especialidad - Editar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style2.css">
</head>
<body class="home">
<jsp:include page="${pageContext.request.contextPath}/components/header2.jsp"/>
<main>
    <div class="window">
        <a class="btn_window activate" href="/especialidad/nuevo">Nuevo +</a>
        <a class="btn_window"  href="/especialidad/lista">Lista </a>
    </div>
    <form action="/updateEspecialidad" method="post">
        <div class="form_campos_registro">
            <label for="especialidad" class="form_label">Especialdad: </label>
            <input type="text" name="especialidad" id="especialidad" value="${especialidad.especialidad}">
        </div>
        <div class="botones">
            <button type="submit" class="btn_submit" name="id" value="${especialidad.id}">Editar</button>
            <a href="/home" class="btn_cancelar">Cancelar</a>
        </div>
    </form>
</main>
</body>
</html>
