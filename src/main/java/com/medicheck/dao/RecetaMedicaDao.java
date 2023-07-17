package com.medicheck.dao;

import com.medicheck.models.Medico;
import com.medicheck.models.Paciente;
import com.medicheck.models.RecetaMedica;
import com.medicheck.utils.Crud;

import java.util.List;

public class RecetaMedicaDao {
    Crud<RecetaMedica> recetaCrud;

    public RecetaMedicaDao() {
        recetaCrud = new Crud<>(RecetaMedica.class);
    }

    public RecetaMedica createRecetaMedica(RecetaMedica receta) {
        return recetaCrud.create(receta);
    }

    public RecetaMedica updateRecetaMedica(RecetaMedica receta) {
        return recetaCrud.update(receta);
    }

    public List<RecetaMedica> getAllRecetaMedica() {
        return recetaCrud.readAll();
    }
    public RecetaMedica getRecetaMedicaById(int id){
        return recetaCrud.findById(id);
    }

    public void deleteRecetaMedica(int id) {
        recetaCrud.delete(id);
    }
}
