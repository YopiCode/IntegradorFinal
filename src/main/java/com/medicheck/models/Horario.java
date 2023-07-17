package com.medicheck.models;

import java.sql.Time;
import java.util.Date;

public class Horario {
    private int id;
    private Time horaIngreso;
    private Time horaSalida;
    private Date dia;
    private int idMedico;
    private int idConsultorio;
    private int idTurno;

    public Horario() {
    }

    public Horario(int id, Time horaIngreso, Time horaSalida, Date dia, int idMedico, int idConsultorio, int idTurno) {
        this.id = id;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
        this.dia = dia;
        this.idMedico = idMedico;
        this.idConsultorio = idConsultorio;
        this.idTurno = idTurno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(Time horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(int idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }
}
