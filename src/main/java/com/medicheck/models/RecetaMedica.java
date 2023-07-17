package com.medicheck.models;

public class RecetaMedica {
    private int id;
    private int idConsulta;
    private int idMedicina;
    private String dosis;

    public RecetaMedica(int id, int idConsulta, int idMedicina, String dosis) {
        this.id = id;
        this.idConsulta = idConsulta;
        this.idMedicina = idMedicina;
        this.dosis = dosis;


    }

    public RecetaMedica() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public int getIdMedicina() {
        return idMedicina;
    }

    public void setIdMedicina(int idMedicina) {
        this.idMedicina = idMedicina;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }
}
