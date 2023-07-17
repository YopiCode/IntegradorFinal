package com.medicheck.dao;

import com.medicheck.models.Paciente;
import com.medicheck.utils.Crud;

import java.util.List;

public class PacienteDao {
    Crud<Paciente> pacienteCrud;

    public PacienteDao() {
        pacienteCrud = new Crud<>(Paciente.class);
    }

    public Paciente createPaciente(Paciente paciente) {
        return pacienteCrud.create(paciente);
    }

    public Paciente updatePaciente(Paciente paciente) {
        return pacienteCrud.update(paciente);
    }

    public List<Paciente> getAllPaciente() {
        return pacienteCrud.readAll();
    }

    public Paciente getPacienteById(int id){
        return pacienteCrud.findById(id);
    }
    public Paciente verificarLogin(Paciente paciente) {
        return pacienteCrud.findByFields(paciente, "correo", "contrasena");
    }

    public void deletePaciente(int id) {
        pacienteCrud.delete(id);
    }

}
