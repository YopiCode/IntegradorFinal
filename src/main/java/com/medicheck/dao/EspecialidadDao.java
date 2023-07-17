package com.medicheck.dao;

import com.medicheck.models.ConsultaMedica;
import com.medicheck.models.Especialidad;
import com.medicheck.models.Paciente;
import com.medicheck.utils.Crud;

import java.util.List;

public class EspecialidadDao {
    Crud<Especialidad> especialidadCrud;

    public EspecialidadDao() {
        especialidadCrud = new Crud<>(Especialidad.class);
    }

    public Especialidad createEspecialidad(Especialidad especialidad) {
        return especialidadCrud.create(especialidad);
    }

    public Especialidad updateEspecialidad(Especialidad especialidad) {
        return especialidadCrud.update(especialidad);
    }

    public List<Especialidad> getAllEspecialidad() {
        return especialidadCrud.readAll();
    }

    public Especialidad getEspecialidadById(int id){
        return especialidadCrud.findById(id);
    }
    public void deleteEspecialidad(int id) {
        especialidadCrud.delete(id);
    }
}
