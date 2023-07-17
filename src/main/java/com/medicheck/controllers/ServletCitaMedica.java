package com.medicheck.controllers;

import com.medicheck.dao.CitaMedicaDao;
import com.medicheck.dao.EspecialidadDao;
import com.medicheck.dao.HorarioDao;
import com.medicheck.dao.MedicoDao;
import com.medicheck.models.CitaMedica;
import com.medicheck.models.Horario;
import com.medicheck.models.Medico;
import com.medicheck.models.Paciente;
import com.medicheck.utils.EnviarEmail;
import com.medicheck.utils.Reflect;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.medicheck.utils.Reporte.generarReporte;

@WebServlet(urlPatterns = {"/registrarCita"
        , "/cancelarCita",
        "/historial"
        , "/pedircita"
        , "/listacita"
        , "/citasmedicas"
        , "/generarReporte"
        ,"/generarHistorial"})
public class ServletCitaMedica extends HttpServlet {
    Reflect<CitaMedica> reflect = new Reflect<>(CitaMedica.class);
    CitaMedicaDao dao = new CitaMedicaDao();
    EspecialidadDao especialidadDao = new EspecialidadDao();
    MedicoDao medicoDao = new MedicoDao();
    HorarioDao horarioDao = new HorarioDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        RequestDispatcher req;
        HttpSession session = request.getSession();
        if (url.equals("/pedircita") || url.equals("/listacita")) {
            int idPaciente = ((Paciente) session.getAttribute("paciente")).getId();
            switch (url) {
                case "/pedircita" -> {
                    request.setAttribute("especialidades", listaEspecialiad());
                    request.setAttribute("medicos", listaMedicos());
//                    request.setAttribute("fechas", listaFechas());
//                    request.setAttribute("horas", listaHorasLibres(new Date()));
                    url = "citas" + url;
                }
                case "/listacita" -> {
                    request.setAttribute("citas", tablaCitasPaciente(idPaciente));
                    url = "citas" + url;
                }
            }

        }
        if (url.equals("/citasmedicas")) {
            Date hoy = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                List<CitaMedica> citasHoy = dao.getAllCitasByFecha(dateFormat.parse(dateFormat.format(hoy)));
                System.out.println(citasHoy);
                request.setAttribute("citasHoy", tablaCitasHoy(citasHoy));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else if (url.equals("/historial")) {
            request.setAttribute("citas", tablaCitas());
        } else if (url.equals("/generarReporte")) {
            generarReporte(response,1);
        }else if (url.equals("/generarHistorial")) {
            generarReporte(response,0);
        }
        req = request.getRequestDispatcher("views/" + url + ".jsp");
        req.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        if (url.equals("/registrarCita") || url.equals("/cancelarCita")) {
            Map<String, String[]> paramMap = request.getParameterMap();
            HttpSession session = request.getSession();
            CitaMedica citaForm = reflect.getObjectParam(paramMap);

            if (session.getAttribute("paciente")!=null){
                int id = ((Paciente) session.getAttribute("paciente")).getId();
                citaForm.setIdPaciente(id);
            }

            switch (url) {
                case "/registrarCita" -> {
                    dao.createCitaMedica(citaForm);
                    Paciente paciente = (Paciente) session.getAttribute("paciente");
                    EnviarEmail enviarEmail = new EnviarEmail();
                    enviarEmail.enviarMensaje(paciente.getCorreo());
                }
                case "/cancelarCita" -> dao.deleteCitaMedica(citaForm.getId());
            }

            response.sendRedirect(request.getContextPath() + "/listacita");
        }
    }

    private String tablaCitas() {
        StringBuilder sb = new StringBuilder();
        dao.getAllCitaMedica()
                .forEach(item -> {
                    System.out.println(item);
                    Medico medico = medicoDao.getMedicoById(item.getIdMedico());
                    sb.append("<td>")
                            .append(medico.getNombre())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getFecha())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getHora())
                            .append("</td>")
                            .append("<td><button type='submit' name='id' value='")
                            .append(item.getId())
                            .append("' class='delete-button'>Borrar</button></td>");
                });
        return sb.toString();
    }

    private String tablaCitasHoy(List<CitaMedica> citas) {
        StringBuilder sb = new StringBuilder();
        citas.forEach(item -> {
                    Medico medico = medicoDao.getMedicoById(item.getIdMedico());
                    sb.append("<tr>")
                            .append("<td>")
                            .append(medico.getNombre())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getFecha())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getHora())
                            .append("</td>")
                            .append("<td><button type='submit' name='id' value='")
                            .append(item.getId())
                            .append("' class='delete-button'>Borrar</button></td>")
                            .append("</tr>");
                });
        return sb.toString();
    }

    private String tablaCitasPaciente(int idPaciente) {
        StringBuilder sb = new StringBuilder();
        dao.getAllCitasById(idPaciente)
                .forEach(item -> {
                    System.out.println(item);
                    //Medico medico = medicoDao.getMedicoById(item.getIdMedico());
                    sb.append("<td>")
                            .append(item.getFecha())
                            .append("</td>")
                            .append("<td>")
                            .append(item.getHora())
                            .append("</td>");
                });
        return sb.toString();
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

    private String listaFechas() {
        StringBuilder sb = new StringBuilder();
        List<Horario> listaHorarios = horarioDao.getAllHorario();
        List<CitaMedica> listaCitas = dao.getAllCitaMedica();
        Set<Date> fechasOcupadas = new HashSet<>();
        for (CitaMedica cita : listaCitas) {
            System.out.println(cita.getFecha());
            fechasOcupadas.add(cita.getFecha());
        }
        Date hoy = new Date();
        for (Horario horario : listaHorarios) {
            Date fechaHorario = horario.getDia();
            if (!fechaHorario.before(hoy) && !fechasOcupadas.contains(fechaHorario)) {
                sb.append("<option value='")
                        .append(formatFecha(fechaHorario))
                        .append("'>")
                        .append(formatFecha(fechaHorario))
                        .append("</option>");
            }
        }
        return sb.toString();
    }

    private String formatFecha(Date fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(fecha);
    }

    private String listaHorasLibres(Date fechaSeleccionada) {
        StringBuilder sb = new StringBuilder();
        List<Horario> listaHorarios = horarioDao.getAllHorario();
        List<CitaMedica> listaCitas = dao.getAllCitaMedica();
        Set<Time> horasOcupadas = new HashSet<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaSeleccionada);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date fechaSeleccionadaSinHora = calendar.getTime();

        // Agregar todas las horas ocupadas por citas médicas para la fecha seleccionada a la lista
        for (CitaMedica cita : listaCitas) {
            if (cita.getFecha().equals(fechaSeleccionada)) {
                horasOcupadas.add(cita.getHora());
            }
        }

        for (Horario horario : listaHorarios) {
            Date fechaHorario = horario.getDia();
            Time horaIngreso = horario.getHoraIngreso();
            Time horaSalida = horario.getHoraSalida();
            // Verificar si el horario es para la fecha seleccionada, no está ocupado y tiene al menos 30 minutos disponibles
            if (fechaHorario.equals(fechaSeleccionadaSinHora) && !horasOcupadas.contains(horaIngreso)) {

                sb.append("<option value='")
                        .append(formatHora(horaIngreso))
                        .append("'>")
                        .append(formatHora(horaIngreso))
                        .append("</option>");
            }
        }
        return sb.toString();
    }

    private boolean tiene30MinutosDisponibles(Date fechaHorario, Time horaIngreso, Time horaSalida) {
        // Convertir la fecha y las horas a milisegundos
        long fechaMillis = fechaHorario.getTime();
        long horaIngresoMillis = horaIngreso.getTime();
        long horaSalidaMillis = horaSalida.getTime();

        // Calcular la diferencia en milisegundos entre la hora de ingreso y la hora de salida
        long diferenciaHorasMillis = horaSalidaMillis - horaIngresoMillis;

        // Convertir 30 minutos a milisegundos
        long treintaMinutosMillis = 30 * 60 * 1000;

        // Verificar si la fecha es futura y la diferencia de tiempo es mayor o igual a 30 minutos
        return fechaMillis >= System.currentTimeMillis() && diferenciaHorasMillis >= treintaMinutosMillis;
    }

    private String formatHora(Time hora) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(hora);
    }
}
