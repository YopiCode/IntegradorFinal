package com.medicheck.dao;

import com.medicheck.models.Consultorio;
import com.medicheck.utils.Crud;

import java.util.List;


public class ConsultorioDao {
    Crud<Consultorio> consultorioCrud;

    
    public ConsultorioDao() {
        consultorioCrud = new Crud<>(Consultorio.class);
        
    }

    public Consultorio createConsultorio(Consultorio consultorio) {
        return consultorioCrud.create(consultorio);
    }
    

    public Consultorio updateConsultorio(Consultorio consultorio) {
        return consultorioCrud.update(consultorio);
    }

    public List<Consultorio> getAllConsultorio() {
        return consultorioCrud.readAll();
    }
    public Consultorio getConsultorioById(int id){
        return consultorioCrud.findById(id);
    }

    public void deleteConsultorio(int id) {
        consultorioCrud.delete(id);
    }
}
