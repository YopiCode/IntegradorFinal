package com.medicheck.controllers;

import com.medicheck.dao.ConsultorioDao;
import com.medicheck.dao.HorarioDao;
import com.medicheck.dao.MedicoDao;
import com.medicheck.models.Consultorio;
import com.medicheck.models.Especialidad;
import com.medicheck.models.Horario;
import com.medicheck.models.Medico;
import com.medicheck.utils.Reflect;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.Console;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet(urlPatterns = {"/registrarHorario"
        , "/updateHorario"
        , "/deleteHorario"
        , "/horario/lista"
        , "/horario/nuevo"
        , "/horario/editar"})
public class ServletHorario extends HttpServlet {

    Reflect<Horario> reflect = new Reflect<>(Horario.class);
    HorarioDao dao = new HorarioDao();
    MedicoDao medicoDao = new MedicoDao();
    ConsultorioDao consultorioDao = new ConsultorioDao();

    String[] turnos = {"Manaña", "Tarde", "Noche"};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        String[] urlArray = url.split("/");
        String urlSplit = urlArray[2];
        RequestDispatcher req;
        if (urlSplit.equals("lista") || urlSplit.equals("nuevo") || urlSplit.equals("editar")) {
            switch (urlSplit) {
                case "nuevo" -> {
                    request.setAttribute("consultorios", listaConsultorio());
                    request.setAttribute("turnos", listaTurnos());
                    request.setAttribute("medicos", listaMedicos());
                }
                case "lista" -> request.setAttribute("horarios", tablaHorarios());
                case "editar" -> {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Horario horario = dao.getHorarioById(id);
                    request.setAttribute("consultorios", listaConsultorioEdit(horario.getIdConsultorio()));
                    request.setAttribute("turnos", listaTurnosEdit(horario.getIdTurno()));
                    request.setAttribute("medicos", listaMedicosEdit(horario.getIdMedico()));
                    request.setAttribute("horario", horario);
                }
            }
            req = request.getRequestDispatcher("/views" + url + ".jsp");
            req.forward(request, response);
        }
    }

    private String tablaHorarios() {
        StringBuilder sb = new StringBuilder();
        dao.getAllHorario()
                .forEach(item -> {
                    Medico medico = medicoDao.getMedicoById(item.getIdMedico());
                    Consultorio consultorio = consultorioDao.getConsultorioById(item.getIdConsultorio());
                    sb.append("<tr>")
                            .append("<td>")
                            .append(item.getDia())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getHoraIngreso())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getHoraSalida())
                            .append("</td>")
                            .append("<td>")
                            .append(medico.getNombre())
                            .append("</td>")
                            .append("<td>")
                            .append(consultorio.getConsultorio())
                            .append("</td>")
                            .append("<td><a href='/horario/editar?id=")
                            .append(item.getId())
                            .append("' class='update-button'>Editar</a></td>")
                            .append("<form action='/deleteHorario' method='post'>")
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
        if (url.equals("/deleteHorario") || url.equals("/updateHorario") || url.equals("/registrarHorario")) {
            Map<String, String[]> paramMap = request.getParameterMap();
            Horario horarioForm = reflect.getObjectParam(paramMap);
            switch (url) {
                case "/registrarHorario" -> dao.createHorario(horarioForm);
                case "/updateHorario" -> dao.updateHorario(horarioForm);
                case "/deleteHorario" -> dao.deleteHorario(horarioForm.getId());
            }

            response.sendRedirect("/horario/lista");
        }
    }

    private String listaMedicos() {
        StringBuilder sb = new StringBuilder();
        medicoDao.getAllMedico()
                .forEach(item -> sb.append("<option value='")
                        .append(item.getId())
                        .append("'>")
                        .append(item.getNombre())
                        .append("</option>"));
        return sb.toString();
    }

    private String listaMedicosEdit(int id) {
        StringBuilder sb = new StringBuilder();
        medicoDao.getAllMedico().forEach(item -> {
            sb.append("<option value='").append(item.getId()).append("'");
            if (item.getId() == id) {
                sb.append(" selected");
            }
            sb.append(">").append(item.getNombre()).append("</option>");
        });
        return sb.toString();
    }


    private String listaConsultorio() {
        StringBuilder sb = new StringBuilder();
        System.out.println("entre");
        consultorioDao.getAllConsultorio()
                .forEach(item -> sb.append("<option value='")
                        .append(item.getId())
                        .append("'>")
                        .append(item.getConsultorio())
                        .append("</option>"));
        return sb.toString();
    }

    private String listaConsultorioEdit(int id) {
        StringBuilder sb = new StringBuilder();
        consultorioDao.getAllConsultorio().forEach(item -> {
            sb.append("<option value='").append(item.getId()).append("'");
            if (item.getId() == id) {
                sb.append(" selected");
            }
            sb.append(">")
                    .append(item.getConsultorio())
                    .append("</option>");
        });
        return sb.toString();
    }


    private String listaTurnos() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < turnos.length; i++) {
            sb.append("<option value='")
                    .append(i + 1)
                    .append("'>")
                    .append(turnos[i])
                    .append("</option>");
        }
        return sb.toString();
    }
    private String listaTurnosEdit(int id) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < turnos.length; i++) {
            sb.append("<option value='").append(i + 1).append("'");
            if (i + 1 == id) {
                sb.append(" selected");
            }
            sb.append(">")
                    .append(turnos[i])
                    .append("</option>");
        }
        return sb.toString();
    }

}
