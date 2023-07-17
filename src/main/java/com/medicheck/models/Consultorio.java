package com.medicheck.models;

public class Consultorio {
    private int id;
    private String consultorio;

    public Consultorio(int id, String consultorio) {
        this.id = id;
        this.consultorio = consultorio;
    }

    public Consultorio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }
}
