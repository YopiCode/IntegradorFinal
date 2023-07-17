package com.medicheck.dao;

import com.medicheck.models.ConsultaMedica;
import com.medicheck.utils.Crud;

import java.util.List;

public class ConsutlaMedicaDao {
    Crud<ConsultaMedica> consultaCrud;

    public ConsutlaMedicaDao() {
        consultaCrud = new Crud<>(ConsultaMedica.class);
    }

    public ConsultaMedica createConsultaMedica(ConsultaMedica consulta) {
        return consultaCrud.create(consulta);
    }

    public ConsultaMedica updateConsultaMedica(ConsultaMedica consulta) {
        return consultaCrud.update(consulta);
    }

    public List<ConsultaMedica> getAllConsultaMedica() {
        return consultaCrud.readAll();
    }

    public void deleteConsultaMedica(int id) {
        consultaCrud.delete(id);
    }
}
