package com.medicheck.controllers;

import com.medicheck.dao.*;
import com.medicheck.models.CitaMedica;
import com.medicheck.models.Medico;
import com.medicheck.models.Paciente;
import com.medicheck.utils.Conexion;
import com.medicheck.utils.GenerarCita;
import com.medicheck.utils.Reflect;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/registrarCita"
        , "/cancelarCita",
        "/historial"
        , "/pedircita"
        , "/listacita"
        , "/citasmedicas"
        , "/generarReporte"
        , "/generarHistorial"
        ,"/generarCita"})
public class ServletCitaMedica extends HttpServlet {
    Reflect<CitaMedica> reflect = new Reflect<>(CitaMedica.class);
    CitaMedicaDao dao = new CitaMedicaDao();
    EspecialidadDao especialidadDao = new EspecialidadDao();
    PacienteDao pacienteDao = new PacienteDao();
    MedicoDao medicoDao = new MedicoDao();
    HorarioDao horarioDao = new HorarioDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        RequestDispatcher req;
        HttpSession session = request.getSession();
        if (url.equals("/pedircita") || url.equals("/listacita")) {
            Paciente paciente = (Paciente) session.getAttribute("paciente");
            int idPaciente = paciente.getId();
            int idEspecialidad = 0;
            int idMedico = 0;
            Date fecha = null;
            Time hora = null;
            switch (url) {
                case "/pedircita" -> {
                    if (session.getAttribute("idEspecialidad") == null) {
                        request.setAttribute("especialidades", listaEspecialiad());
                    } else if (session.getAttribute("idEspecialidad") != null) {
                        idEspecialidad = (int) session.getAttribute("idEspecialidad");
                        request.setAttribute("especialidades", especialidadElegida(idEspecialidad));

                        if (session.getAttribute("idMedico") == null) {
                            request.setAttribute("medicos", listaMedicos(idEspecialidad));
                        } else if (session.getAttribute("idMedico") != null) {
                            idMedico = (int) session.getAttribute("idMedico");
                            request.setAttribute("medicos", medicoElegido(idMedico));

                            if (session.getAttribute("fecha") == null) {
                                request.setAttribute("fechas", listaFechas(idMedico));
                            } else if (session.getAttribute("fecha") != null) {
                                fecha = (Date) session.getAttribute("fecha");
                                request.setAttribute("fechas", fechaElegido(fecha));

                                if (session.getAttribute("hora") == null) {
                                    request.setAttribute("horas", listaHorasLibres(fecha, idMedico));
                                }
                            }
                        }
                    }

                    url = "citas" + url;
                }
                case "/listacita" -> {
                    url = "citas" + url;
                    req = request.getRequestDispatcher("views/" + url + ".jsp");
                    request.setAttribute("citas", tablaCitasPaciente(idPaciente));
                    req.forward(request, response);
                }
            }

        }
        if (url.equals("/citasmedicas")) {
            request.setAttribute("citasHoy", tablaCitasHoy());
        } else if (url.equals("/historial")) {
            request.setAttribute("citas", tablaCitas());
        } else if (url.equals("/generarReporte")) {
            generarReporte(response, 1);
        } else if (url.equals("/generarHistorial")) {
            generarReporte(response, 0);
        } else if (url.equals("/generarCita")) {
            generarReporte(response, 0);
        }
        req = request.getRequestDispatcher("views/" + url + ".jsp");
        req.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        if (url.equals("/registrarCita") || url.equals("/cancelarCita") || url.equals("/generarCita")) {
            Map<String, String[]> paramMap = request.getParameterMap();
            HttpSession session = request.getSession();
            CitaMedica citaForm = reflect.getObjectParam(paramMap);
            Paciente paciente = (Paciente) session.getAttribute("paciente");

            citaForm.setIdPaciente(paciente.getId());

            switch (url) {
                case "/registrarCita" -> {
                    if (session.getAttribute("idEspecialidad") == null) {
                        if (request.getParameter("idEspecialidad")==null|| Objects.equals(request.getParameter("idEspecialidad"), "null") || request.getParameter("idEspecialidad").isBlank()){
                            response.sendRedirect("/listacita");
                            return;
                        }
                        int idEspecialidad = Integer.parseInt(request.getParameter("idEspecialidad"));
                        session.setAttribute("idEspecialidad", idEspecialidad);
                        response.sendRedirect(request.getHeader("referer"));
                        return;
                    } else if (session.getAttribute("idMedico") == null) {
                        int idMedico = Integer.parseInt(request.getParameter("idMedico"));
                        session.setAttribute("idMedico", idMedico);
                        response.sendRedirect(request.getHeader("referer"));
                        return;
                    } else if (session.getAttribute("fecha") == null) {
                        String fecha = request.getParameter("fecha");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            session.setAttribute("fecha", dateFormat.parse(fecha));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        response.sendRedirect(request.getHeader("referer"));
                        return;
                    }

                    if (request.getParameter("hora") != null) {
                        String hora = request.getParameter("hora");
                        Date fecha = (Date) session.getAttribute("fecha");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        int idMedico = (Integer) session.getAttribute("idMedico");
                        try {
                            citaForm.setHora(new Time(timeFormat.parse(hora).getTime()));
                            citaForm.setFecha(fecha);
                            citaForm.setIdMedico(idMedico);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        dao.createCitaMedica(citaForm);
                        session.removeAttribute("idMedico");
                        session.removeAttribute("idEspecialidad");
                        session.removeAttribute("fecha");
                    }

                }
                case "/cancelarCita" -> dao.deleteCitaMedica(citaForm.getId());
                case "/generarCita" -> {
                    CitaMedica citaMedica = dao.getCitaMedicaById(citaForm.getId());
                    System.out.println("wenas");
                    GenerarCita cita = new GenerarCita();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    cita.setFecha(dateFormat.format(citaMedica.getFecha()));
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    cita.setHora(timeFormat.format(citaMedica.getHora()));
                    cita.setMedico(medicoDao.getMedicoById(citaMedica.getIdMedico()).getNombre());
                    int idEspecialidad = medicoDao.getMedicoById(citaMedica.getIdMedico()).getIdEspecialidad();
                    cita.setEspecialidad(especialidadDao.getEspecialidadById(idEspecialidad).getEspecialidad());
                    cita.setPaciente(paciente.getNombre());
                    cita.setDni(paciente.getDni());
                    generarCita(response, cita);
                }
            }

            response.sendRedirect("/listacita");
        }
    }

    public void generarCita(HttpServletResponse response, GenerarCita cita) {
        try (OutputStream outputStream = response.getOutputStream()) {
            JasperReport report = (JasperReport) JRLoader.loadObject(new File(Conexion.class.getClassLoader().getResource("reportes/citaMedica.jasper").getPath()));
            List<GenerarCita> citas = Collections.singletonList(cita);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(citas);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ds", ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=cita.pdf");
            JasperPrint jasperPrint = null;
            try {
                jasperPrint = JasperFillManager.fillReport(report, parameters, ds);
            } catch (JRException e) {
                throw new RuntimeException(e);
            }
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }


    public void generarReporte(HttpServletResponse response, int aux) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Citas");

        String titulo = "Reporte de Citas Médicas";

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);

        DataFormat dataFormat = workbook.createDataFormat();

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));

        CellStyle timeCellStyle = workbook.createCellStyle();
        timeCellStyle.setDataFormat(dataFormat.getFormat("HH:mm:ss"));

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(titulo);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

        String[] headers = {"Fecha", "Hora", "Paciente DNI", "Paciente Nombre", "Médico Nombre"};
        int rowNum = 2;
        Row headerRow = sheet.createRow(1);
        int colNum = 0;
        for (String header : headers) {
            Cell cell = headerRow.createCell(colNum++);
            cell.setCellValue(header);
            cell.setCellStyle(headerStyle);
        }
        for (int i = 0; i < headers.length; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }
        List<CitaMedica> citaMedicas = dao.getAllCitaMedica();
        if (aux == 1) {
            LocalDate fechaActual = LocalDate.now();
            LocalTime horaActual = LocalTime.now();
            citaMedicas = dao.getAllCitaMedica()
                    .stream()
                    .filter(item -> Objects.equals(LocalDate.parse(item.getFecha().toString()), fechaActual)
                            && LocalTime.parse(item.getHora().toString()).isAfter(horaActual)).collect(Collectors.toList());
        }

        for (CitaMedica cita : citaMedicas) {
            Row row = sheet.createRow(rowNum++);
            colNum = 0;
            Cell dateCell = row.createCell(colNum++);
            dateCell.setCellValue(cita.getFecha());
            dateCell.setCellStyle(dateCellStyle);

            Cell timeCell = row.createCell(colNum);
            timeCell.setCellValue(cita.getHora());
            timeCell.setCellStyle(timeCellStyle);

            row.createCell(colNum++).setCellValue(pacienteDao.getPacienteById(cita.getIdPaciente()).getDni());
            row.createCell(colNum++).setCellValue(pacienteDao.getPacienteById(cita.getIdPaciente()).getNombre());
            row.createCell(colNum++).setCellValue(medicoDao.getMedicoById(cita.getIdMedico()).getNombre());

            for (Cell cell : row) {
                cell.setCellStyle(dataStyle);
            }
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte.xlsx");
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String tablaCitas() {
        StringBuilder sb = new StringBuilder();
        dao.getAllCitaMedica()
                .forEach(item -> {
                    System.out.println(item);
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
                            .append("</tr>");
                });
        return sb.toString();
    }

    private String tablaCitasHoy() {
        StringBuilder sb = new StringBuilder();
        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();
        dao.getAllCitaMedica()
                .stream()
                .filter(item -> Objects.equals(LocalDate.parse(item.getFecha().toString()), fechaActual)
                        && LocalTime.parse(item.getHora().toString()).isAfter(horaActual))
                .forEach(item -> {
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
                            .append("</tr>");
                });
        return sb.toString();
    }

    private String tablaCitasPaciente(int idPaciente) {
        StringBuilder sb = new StringBuilder();
        dao.getAllCitaMedica()
                .stream().filter(item -> item.getIdPaciente() == idPaciente)
                .forEach(item -> {
                    System.out.println(item);
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
                            .append("<td><form action='generarCita' method='post'><button type='submit' name='id' value='")
                            .append(item.getId())
                            .append("' class='delete-button'>Imprimir</a></form></td>")
                            .append("<td><form action='cancelarCita' method='post'><button type='submit' name='id' value='")
                            .append(item.getId())
                            .append("' class='delete-button'>Borrar</a></form></td>")
                            .append("</tr>");
                });
        return sb.toString();
    }

    private String listaEspecialiad() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='btn_forms'>");
        especialidadDao.getAllEspecialidad()
                .forEach(item -> sb.append("<button type='submit' name='idEspecialidad' value='")
                        .append(item.getId())
                        .append("'>")
                        .append(item.getEspecialidad())
                        .append("</button>"));
        sb.append("</div>");
        return sb.toString();
    }

    private String especialidadElegida(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='btn_forms'>");
        String especialidad = especialidadDao.getEspecialidadById(id).getEspecialidad();
        sb.append("<h2>").append(especialidad).append("</h2>");
        sb.append("</div>");
        return sb.toString();
    }

    private String listaMedicos(int idEspecialidad) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='btn_forms'>");
        medicoDao.getAllMedico()
                .stream().filter(item -> item.getIdEspecialidad() == idEspecialidad)
                .forEach(item -> sb.append("<button type='submit' name='idMedico' value='")
                        .append(item.getId())
                        .append("'>")
                        .append(item.getNombre())
                        .append("</button>"));
        sb.append("</div>");
        return sb.toString();
    }

    private String medicoElegido(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='btn_forms'>");
        String medico = medicoDao.getMedicoById(id).getNombre();
        sb.append("<h2>").append(medico).append("</h2>");
        sb.append("</div>");
        return sb.toString();
    }

    private String listaFechas(int idMedico) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='btn_forms'>");
        Map<Date, Integer> cantidadCitas = new HashMap<>();
        Map<Date, List<Time>> horasOcupadas = new LinkedHashMap<>();
        HorarioDao horarioDao = new HorarioDao();
        CitaMedicaDao citaMedicaDao = new CitaMedicaDao();
        horarioDao.getAllHorario()
                .stream()
                .filter(item -> item.getIdMedico() == idMedico)
                .forEach(item -> {
                    long diferenciaMs = item.getHoraSalida().getTime() - item.getHoraIngreso().getTime();
                    long diferenciaMinutos = diferenciaMs / (60 * 1000);
                    long horas = diferenciaMinutos / 60;
                    cantidadCitas.put(item.getDia(), (int) (horas * 2));
                });
        citaMedicaDao.getAllCitaMedica()
                .stream()
                .filter(item -> item.getIdMedico() == idMedico)
                .forEach(item -> {
                    Date fecha = item.getFecha();
                    Time hora = item.getHora();
                    horasOcupadas.computeIfAbsent(fecha, k -> new ArrayList<>()).add(hora);
                });
        horarioDao.getAllHorario()
                .stream()
                .filter(item -> item.getIdMedico() == idMedico)
                .forEach(item -> {
                    List<Time> ocupada = horasOcupadas.get(item.getDia());
                    int cantidad = cantidadCitas.get(item.getDia());
                    int libre = cantidad;
                    if (ocupada != null){
                        libre = cantidad - ocupada.size();
                    }

                    if (libre > 0) {
                        sb.append("<button type='submit' name='fecha' value='")
                                .append(item.getDia())
                                .append("'>")
                                .append(item.getDia())
                                .append("</button>");
                    }
                });
        sb.append("</div>");
        return sb.toString();
    }

    private String fechaElegido(Date fecha) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='btn_forms'>");
        sb.append("<h2>").append(formatFecha(fecha)).append("</h2>");
        sb.append("</div>");
        return sb.toString();
    }


    private String formatFecha(Date fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(fecha);
    }

    private String listaHorasLibres(Date fecha, int idMedico) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='btn_forms'>");
        List<Time> horasCitas = new ArrayList<>();
        List<LocalTime> horasOcupadas = new ArrayList<>();
        HorarioDao horarioDao = new HorarioDao();
        CitaMedicaDao citaMedicaDao = new CitaMedicaDao();

        citaMedicaDao.getAllCitaMedica()
                .stream()
                .filter(item -> item.getFecha().equals(fecha) && item.getIdMedico() == idMedico)
                .forEach(item -> horasCitas.add(item.getHora()));

        LocalTime horaActual = LocalTime.now();

        horarioDao.getAllHorario()
                .stream()
                .filter(item -> item.getDia().equals(fecha) && item.getIdMedico() == idMedico)
                .forEach(item -> {
                    LocalTime horaAux = LocalTime.parse(item.getHoraIngreso().toString());
                    LocalTime horaSalida = LocalTime.parse(item.getHoraSalida().toString());
                    while (horaAux.isBefore(LocalTime.parse(item.getHoraSalida().toString()))) {
                        if (!horasOcupadas.contains(horaAux)) {
                            horasOcupadas.add(horaAux);
                        }
                        horaAux = horaAux.plusMinutes(30);
                    }

                });

        if (fecha.equals(new Date())) {
            horasOcupadas.stream()
                    .filter(item -> item.isAfter(horaActual))
                    .forEach(item -> {
                        if (!horasCitas.contains(Time.valueOf(item))) {
                            sb.append("<button type='submit' name='hora' value='")
                                    .append(Time.valueOf(item))
                                    .append("'>")
                                    .append(Time.valueOf(item))
                                    .append("</button>");
                        }
                    });
        } else {
            horasOcupadas.forEach(item -> {
                if (!horasCitas.contains(Time.valueOf(item))) {
                    sb.append("<button type='submit' name='hora' value='")
                            .append(Time.valueOf(item))
                            .append("'>")
                            .append(Time.valueOf(item))
                            .append("</button>");
                }
            });
        }
        sb.append("</div>");
        return sb.toString();
    }

}
