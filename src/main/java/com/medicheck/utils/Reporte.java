package com.medicheck.utils;

import com.medicheck.dao.CitaMedicaDao;
import com.medicheck.dao.MedicoDao;
import com.medicheck.dao.PacienteDao;
import com.medicheck.models.CitaMedica;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

public class Reporte {
    public static CitaMedicaDao dao = new CitaMedicaDao();
    public static MedicoDao medicoDao = new MedicoDao();
    public static PacienteDao pacienteDao = new PacienteDao();

    public static void generarReporte(HttpServletResponse response, int aux) {
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

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(titulo);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

        String[] headers = {"Fecha", "Paciente DNI", "Paciente Nombre", "Médico Nombre", "Hora"};
        int rowNum = 2;
        Row headerRow = sheet.createRow(1);
        int colNum = 0;
        for (String header : headers) {
            Cell cell = headerRow.createCell(colNum++);
            cell.setCellValue(header);
            cell.setCellStyle(headerStyle);
        }
        List<CitaMedica> citaMedicas = dao.getAllCitaMedica();
        if (aux == 1){
            citaMedicas = dao.getAllCitasByFecha(new Date());
        }

        for (CitaMedica cita : citaMedicas) {
            Row row = sheet.createRow(rowNum++);
            colNum = 0;
            row.createCell(colNum++).setCellValue(cita.getFecha());
            row.createCell(colNum++).setCellValue(pacienteDao.getPacienteById(cita.getIdPaciente()).getDni());
            row.createCell(colNum++).setCellValue(pacienteDao.getPacienteById(cita.getIdPaciente()).getNombre());
            row.createCell(colNum++).setCellValue(medicoDao.getMedicoById(cita.getIdMedico()).getNombre());
            row.createCell(colNum).setCellValue(cita.getHora());
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


}
