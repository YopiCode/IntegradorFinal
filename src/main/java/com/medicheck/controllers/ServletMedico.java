package com.medicheck.controllers;

import com.medicheck.dao.EspecialidadDao;
import com.medicheck.dao.MedicoDao;
import com.medicheck.models.Medico;
import com.medicheck.utils.Reflect;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/loginMedico",
        "/registrarMedico",
        "/deleteMedico",
        "/updateMedico",
        "/home",
        "/cerrarSesionMedico",
        "/medico/nuevo",
        "/medico/lista",
        "/medico/editar",
        "/medico/login"})
public class ServletMedico extends HttpServlet {

    Reflect<Medico> reflect = new Reflect<>(Medico.class);
    MedicoDao dao = new MedicoDao();
    EspecialidadDao especialidadDao = new EspecialidadDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        String[] urlArray = url.split("/");
        String urlSplit = urlArray[1];
        RequestDispatcher req;
        HttpSession session = request.getSession();
        if (urlSplit.equals("medico")) {
            urlSplit = urlArray[2];
            if (urlSplit.equals("nuevo") || urlSplit.equals("lista") || urlSplit.equals("editar") || urlSplit.equals("login")) {
                switch (urlSplit) {
                    case "nuevo" ->{
                        request.setAttribute("especialidades", listaEspecialiad());
                    }
                    case "lista" -> request.setAttribute("medicos", tablaMedicos());
                    case "editar" -> {
                        int id = Integer.parseInt(request.getParameter("id"));
                        Medico medico = dao.getMedicoById(id);
                        request.setAttribute("especialidades", listaEspecialiadEdit(medico.getIdEspecialidad()));
                        request.setAttribute("medico", medico);
                    }
                }
                req = request.getRequestDispatcher("/views/" + url + ".jsp");
                req.forward(request, response);
            }
        }

        if (url.equals("/home")) {
            req = request.getRequestDispatcher("views/home.jsp");
            req.forward(request, response);
        }
        if (url.equals("/cerrarSesionMedico")) {
            session.removeAttribute("medico");
            response.sendRedirect("/medico/login");
        }
    }

    private String tablaMedicos() {
        StringBuilder sb = new StringBuilder();
        dao.getAllMedico()
                .forEach(item -> {
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
                            .append(item.getTelefono())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getDireccion())
                            .append("</td>")
                            .append("<td><a href='/medico/editar?id=")
                            .append(item.getId())
                            .append("' class='update-button'>Editar</a></td>")
                            .append("<form action='/deleteMedico' method='post'>")
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
        if (url.equals("/loginMedico") || url.equals("/registrarMedico") || url.equals("/updateMedico") || url.equals("/deleteMedico")) {
            Map<String, String[]> paramMap = request.getParameterMap();
            Medico medico = reflect.getObjectParam(paramMap);
            HttpSession session = request.getSession();

            Medico medicoSesion = null;
            switch (url) {
                case "/loginMedico" -> medicoSesion = dao.verificarLogin(medico);
                case "/registrarMedico" -> medicoSesion = dao.createMedico(medico);
                case "/updateMedico" -> medicoSesion = dao.updateMedico(medico);
                case "/deleteMedico" -> dao.deleteMedico(medico.getId());
            }

            if (medicoSesion == null) {
                System.out.println("wenas");
                response.sendRedirect(request.getHeader("referer"));
                return;
            } else if (url.equals("/loginMedico")) {
                session.setAttribute("medico", medicoSesion);
                response.sendRedirect("/home");
                return;
            }
            response.sendRedirect("/medico/nuevo");
        }
    }

    private String listaEspecialiad() {
        StringBuilder sb = new StringBuilder();
        especialidadDao.getAllEspecialidad()
                .forEach(item -> sb.append("<option value='")
                        .append(item.getId())
                        .append("'>")
                        .append(item.getEspecialidad())
                        .append("</option>"));
        return sb.toString();
    }

    private String listaEspecialiadEdit(int id) {
        StringBuilder sb = new StringBuilder();
        especialidadDao.getAllEspecialidad()
                .forEach(item -> {
                    sb.append("<option value='")
                            .append(item.getId()).append("'");
                    if (item.getId() == id) {
                        sb.append(" selected");
                    }
                    sb.append(">")
                            .append(item.getEspecialidad())
                            .append("</option>");
                });
        return sb.toString();
    }
}
