package com.medicheck.models;

public class ConsultaMedica {
    private int id;
    private int idCita;
    private String diagnostico;

    public ConsultaMedica(int id, int idCita, String diagnostico) {
        this.id = id;
        this.idCita = idCita;
        this.diagnostico = diagnostico;
    }

    public ConsultaMedica() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
}
