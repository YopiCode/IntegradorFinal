package com.medicheck.controllers;

import com.medicheck.dao.PacienteDao;
import com.medicheck.models.Paciente;
import com.medicheck.utils.Reflect;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/login","/deletePaciente","/updatePaciente", "/registrarPaciente", "/paciente/nuevo", "/paciente/lista", "/paciente/editar", "/paciente/perfil", "/cerrarSesion"})
public class ServletPaciente extends HttpServlet {
    Reflect<Paciente> reflect = new Reflect<>(Paciente.class);
    PacienteDao dao = new PacienteDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        String[] urlArray = url.split("/");
        String urlSplit = urlArray[1];
        RequestDispatcher req;
        HttpSession session = request.getSession();
        if (urlSplit.equals("paciente")){
            urlSplit = urlArray[2];
            if (urlSplit.equals("nuevo") || urlSplit.equals("lista") || urlSplit.equals("perfil") || urlSplit.equals("editar")) {
                switch (urlSplit){
                    case "lista" -> request.setAttribute("pacientes", tablaPaciente());
                    case "editar" -> {
                        int id = Integer.parseInt(request.getParameter("id"));
                        request.setAttribute("paciente", dao.getPacienteById(id));
                    }
                }
                req = request.getRequestDispatcher("/views/" + url + ".jsp");
                req.forward(request, response);
            }
        }

        if (url.equals("/cerrarSesion")) {
            session.removeAttribute("paciente");
            session.removeAttribute("btnSalir");
            response.sendRedirect("/");
        }
    }


    private String tablaPaciente(){
        StringBuilder sb = new StringBuilder();
        dao.getAllPaciente()
                .forEach(item->{
                    sb.append("<tr>")
                            .append("<td>")
                            .append(item.getNombre())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getApellidoP())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getApellidoM())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getDni())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getEdad())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getSexo())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getFechaNacimiento())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getTelefono())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getDireccion())
                            .append("</td>")
                            .append("<td><a href='/paciente/editar?id=")
                            .append(item.getId())
                            .append("' class='update-button'>Editar</a></td>")
                            .append("<form action='/deletePaciente' method='post'>")
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
        if (url.equals("/login") || url.equals("/registrarPaciente") || url.equals("/updatePaciente") || url.equals("/deletePaciente")) {
            Map<String, String[]> paramMap = request.getParameterMap();
            Paciente pacienteForm = reflect.getObjectParam(paramMap);
            HttpSession session = request.getSession();

            Paciente pacienteSesion = null;
            switch (url) {
                case "/login" -> pacienteSesion = dao.verificarLogin(pacienteForm);
                case "/registrarPaciente" -> pacienteSesion = dao.createPaciente(pacienteForm);
                case "/updatePaciente" -> pacienteSesion = dao.updatePaciente(pacienteForm);
                case "/deletePaciente" -> dao.deletePaciente(pacienteForm.getId());
            }

            if (pacienteSesion == null) {
                response.sendRedirect(request.getHeader("referer"));
                return;
            } else if (url.equals("/login")){
                session.setAttribute("paciente", pacienteSesion);
                session.setAttribute("btnSalir", btnSalir());
                response.sendRedirect("/paciente/perfil");
                return;
            }
            response.sendRedirect("/paciente/lista");
        }
    }

    private String btnSalir() {
        StringBuilder sb = new StringBuilder();
        sb.append("<a href='/cerrarSesion' class='delete-button'>Cerrar Sesion</a>");
        return sb.toString();
    }
}
