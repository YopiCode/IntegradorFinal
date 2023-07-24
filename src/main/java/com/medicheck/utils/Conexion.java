package com.medicheck.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public Connection conectar(){
        String databaseName = "medicheck";
        String databaseUser = "root";
        String databasePassword = "yopi123";
        String url = "jdbc:mysql://localhost/" + databaseName;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, databaseUser, databasePassword);
        }catch (Exception e){
            System.out.println("No se conectó a la base " + databaseName);
        }
        return null;
    }

}
