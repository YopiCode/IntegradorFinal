package com.medicheck.dao;

import com.medicheck.models.CitaMedica;
import com.medicheck.models.Paciente;
import com.medicheck.utils.Crud;

import java.util.Date;
import java.util.List;

public class CitaMedicaDao {

    Crud<CitaMedica> citaCrud;
    CitaMedica cita;

    public CitaMedicaDao() {
        citaCrud = new Crud<>(CitaMedica.class);
        cita = new CitaMedica();
    }

    public CitaMedica createCitaMedica(CitaMedica cita) {
        return citaCrud.create(cita);
    }

    public CitaMedica updateCitaMedica(CitaMedica cita) {
        return citaCrud.update(cita);
    }

    public List<CitaMedica> getAllCitaMedica() {
        return citaCrud.readAll();
    }

    public List<CitaMedica> getAllCitasById(int idPaciente) {
        cita.setIdPaciente(idPaciente);
        return citaCrud.findByAllFields(cita, "idPaciente");
    }

    public List<CitaMedica> getAllCitasByFecha(Date fecha) {
        cita.setFecha(fecha);
        return citaCrud.findByAllFields(cita, "fecha");
    }

    public CitaMedica getCitaMedicaById(int id){
        return citaCrud.findById(id);
    }
    public void deleteCitaMedica(int id) {
        citaCrud.delete(id);
    }

}
