package com.medicheck.utils;

public class GenerarCita {

    private String paciente;
    private String dni;
    private String fecha;
    private String hora;
    private String medico;
    private String especialidad;

    public GenerarCita(String paciente, String dni, String fecha, String hora, String medico, String especialidad) {
        this.paciente = paciente;
        this.dni = dni;
        this.fecha = fecha;
        this.hora = hora;
        this.medico = medico;
        this.especialidad = especialidad;
    }

    public GenerarCita() {
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
