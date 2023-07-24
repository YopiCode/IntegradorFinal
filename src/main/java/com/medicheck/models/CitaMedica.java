package com.medicheck.models;

import java.sql.Time;
import java.util.Date;

public class CitaMedica {
    private int id;
    private Date fecha;
    private Time hora;
    private int idPaciente;
    private int idMedico;

    public CitaMedica(int id, Date fecha, Time hora, int idPaciente, int idMedico) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
    }

    @Override
    public String toString() {
        return "CitaMedica{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", idPaciente=" + idPaciente +
                ", idMedico=" + idMedico +
                '}';
    }

    public CitaMedica() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }


}
