package com.medicheck;

import com.medicheck.models.Paciente;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class App {

    public static void main(String[] args) {
        Paciente paciente = new Paciente();

        Field[] campos = paciente.getClass().getDeclaredFields();

        Method[] metodos = paciente.getClass().getDeclaredMethods();

        for (Method metodo : metodos) {
            System.out.println(metodo.getName());
        }

        /*for (Field campo : campos) {
            System.out.println(campo.getName());
        }*/


    }

}
