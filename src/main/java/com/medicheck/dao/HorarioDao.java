package com.medicheck.dao;

import com.medicheck.models.Horario;
import com.medicheck.models.Paciente;
import com.medicheck.utils.Crud;

import java.util.List;

public class HorarioDao {

    Crud<Horario> horarioCrud;

    public HorarioDao() {
        horarioCrud = new Crud<>(Horario.class);
    }

    public Horario createHorario(Horario horario) {
        return horarioCrud.create(horario);
    }

    public Horario updateHorario(Horario horario) {
        return horarioCrud.update(horario);
    }

    public List<Horario> getAllHorario() {
        return horarioCrud.readAll();
    }

    public Horario getHorarioById(int id){
        return horarioCrud.findById(id);
    }

    public void deleteHorario(int id) {
        horarioCrud.delete(id);
    }

}
