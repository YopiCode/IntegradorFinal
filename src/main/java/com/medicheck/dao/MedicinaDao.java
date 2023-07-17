package com.medicheck.dao;

import com.medicheck.models.Horario;
import com.medicheck.models.Medicina;
import com.medicheck.models.Paciente;
import com.medicheck.utils.Crud;

import java.util.List;

public class MedicinaDao {
    Crud<Medicina> medicinaCrud;

    public MedicinaDao() {
        medicinaCrud = new Crud<>(Medicina.class);
    }

    public Medicina createMedicina(Medicina medicina) {
        return medicinaCrud.create(medicina);
    }

    public Medicina updateMedicina(Medicina medicina) {
        return medicinaCrud.update(medicina);
    }

    public List<Medicina> getAllMedicina() {
        return medicinaCrud.readAll();
    }

    public Medicina getMedicinaById(int id){
        return medicinaCrud.findById(id);
    }

    public void deleteMedicina(int id) {
        medicinaCrud.delete(id);
    }
}
