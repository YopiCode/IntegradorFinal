package com.medicheck.controllers;

import com.medicheck.dao.EspecialidadDao;
import com.medicheck.models.CitaMedica;
import com.medicheck.models.Especialidad;
import com.medicheck.models.Medico;
import com.medicheck.models.Paciente;
import com.medicheck.utils.Reflect;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet(urlPatterns = {"/registrarEspecialidad"
        , "/updateEspecialidad"
        , "/deleteEspecialidad"
        , "/especialidad/lista"
        , "/especialidad/nuevo"
        , "/especialidad/editar"})
public class ServletEspecialidad extends HttpServlet {
    Reflect<Especialidad> reflect = new Reflect<>(Especialidad.class);
    EspecialidadDao dao = new EspecialidadDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        String[] urlArray = url.split("/");
        String urlSplit = urlArray[2];
        RequestDispatcher req;
        if (urlSplit.equals("lista") || urlSplit.equals("nuevo") || urlSplit.equals("editar")){
            switch (urlSplit){
                case "lista" -> request.setAttribute("especialidades", tablaEspecalidad());
                case "editar" -> {
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("especialidad", dao.getEspecialidadById(id));
                }
            }
            req = request.getRequestDispatcher("/views" + url + ".jsp");
            req.forward(request, response);
        }
    }

    private String tablaEspecalidad(){
        StringBuilder sb = new StringBuilder();
        dao.getAllEspecialidad()
                .forEach(item->{
                    sb.append("<tr>")
                            .append("<td>")
                            .append(item.getEspecialidad())
                            .append("</td>")
                            .append("<td><a href='/especialidad/editar?id=")
                            .append(item.getId())
                            .append("' class='update-button'>Editar</a></td>")
                            .append("<form action='/deleteEspecialidad' method='post'>")
                            .append("<td><button type='submit' name='id' value='")
                            .append(item.getId())
                            .append("' class='delete-button'>Borrar</button></td>")
                            .append("</form>")
                            .append("</tr>");
                });
        return sb.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        if (url.equals("/updateEspecialidad") || url.equals("/registrarEspecialidad") || url.equals("/deleteEspecialidad")){
            Map<String, String[]> paramMap = request.getParameterMap();
            Especialidad citaForm = reflect.getObjectParam(paramMap);
            switch (url){
                case "/registrarEspecialidad" -> dao.createEspecialidad(citaForm);
                case "/updateEspecialidad" -> dao.updateEspecialidad(citaForm);
                case "/deleteEspecialidad" -> dao.deleteEspecialidad(citaForm.getId());
            }

            response.sendRedirect("/especialidad/lista");
        }
    }
}
