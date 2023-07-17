package com.medicheck.dao;

import com.medicheck.models.Medicina;
import com.medicheck.models.Medico;
import com.medicheck.models.Paciente;
import com.medicheck.utils.Crud;

import java.util.List;

public class MedicoDao {
    Crud<Medico> medicoCrud;

    public MedicoDao() {
        medicoCrud = new Crud<>(Medico.class);
    }

    public Medico createMedico(Medico medico) {
        return medicoCrud.create(medico);
    }

    public Medico updateMedico(Medico medico) {
        return medicoCrud.update(medico);
    }

    public List<Medico> getAllMedico() {
        return medicoCrud.readAll();
    }
    public List<Medico> getAllMedicoByIdEspecialidad(int id) {
        Medico medico = new Medico();
        medico.setIdEspecialidad(id);
        return medicoCrud.findByAllFields(medico, "idEspecialidad");
    }
    public Medico verificarLogin(Medico medico) {
        return medicoCrud.findByFields(medico, "usuario", "contrasena");
    }
    public Medico getMedicoById(int id){
        return  medicoCrud.findById(id);
    }

    public void deleteMedico(int id) {
        medicoCrud.delete(id);
    }
}
