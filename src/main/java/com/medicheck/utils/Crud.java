package com.medicheck.utils;

import com.ctc.wstx.sw.SimpleOutputElement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Crud<T> {

    private Conexion conexion = new Conexion();

    private Class<T> tClass;
    private int columns;

    public Crud(Class<T> tClass) {
        this.tClass = tClass;
        columns = tClass.getDeclaredFields().length;
    }

    public T create(T obja) {
        String CREATE = "insert into {class} ({atributos}) values ({valores})";
       
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (Field field : tClass.getDeclaredFields()) {
            String fieldname = field.getName();
            if (fieldname.equals("id")) {
                continue;
            }
            sb.append(fieldname);
            sb2.append("?");
            if (!field.getName().equals(tClass.getDeclaredFields()[columns - 1].getName())) {
                sb.append(",");
                sb2.append(",");
            }

        }
        String create = CREATE.replace("{class}", tClass.getSimpleName())
                .replace("{atributos}", sb.toString())
                .replace("{valores}", sb2.toString());
        try (PreparedStatement ps = conexion.conectar().prepareCall(create)) {
            int parameterIndex = 1;
            for (Field field : tClass.getDeclaredFields()) {
                String fieldName = field.getName();
                if (fieldName.equals("id")) {
                    continue;
                }
                String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

                Method method = tClass.getMethod(methodName);
                Object valor = method.invoke(obja);
                ps.setObject(parameterIndex, valor);
                parameterIndex++;
            }
            ps.executeUpdate();
            return getLastInsert();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public T update(T obja) {
        String UPDATE = "update {class} set {atributos} where id = ?";
        
        StringBuilder sb = new StringBuilder();
        for (Field field : tClass.getDeclaredFields()) {
            String fieldname = field.getName();
            if (fieldname.equals("id")) {
                continue;
            }
            sb.append(fieldname).append("=?");
            if (!field.getName().equals(tClass.getDeclaredFields()[columns - 1].getName())) {
                sb.append(",");
            }

        }
        String update = UPDATE.replace("{class}", tClass.getSimpleName())
                .replace("{atributos}", sb.toString());
        try (PreparedStatement ps = conexion.conectar().prepareCall(update)) {
            int parameterIndex = 1;
            for (Field field : tClass.getDeclaredFields()) {
                String fieldName = field.getName();
                if (fieldName.equals("id")) {
                    continue;
                }
                String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                Method method = tClass.getMethod(methodName);
                Object valor = method.invoke(obja);
                ps.setObject(parameterIndex, valor);
                parameterIndex++;
            }
            Method method = tClass.getMethod("getId");
            Object valor = method.invoke(obja);
            ps.setObject(parameterIndex, valor);
            ps.executeUpdate();
            return obja;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(int id){
        String DELETE = "delete from {class} where id = ?";

        String delete = DELETE.replace("{class}", tClass.getSimpleName());

        try (PreparedStatement ps = conexion.conectar().prepareCall(delete)) {
            ps.setObject(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public ArrayList<T> readAll() {
        ArrayList<T> list = new ArrayList<>();
        String READ = "select * from {class}";
        String read = READ.replace("{class}", tClass.getSimpleName());
        try (PreparedStatement ps = conexion.conectar().prepareCall(read)) {
            ResultSet res = ps.executeQuery();
            ResultSetMetaData metaData = res.getMetaData();
            while (res.next()) {
                T obj = tClass.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columns; i++) {
                    String fieldname = metaData.getColumnName(i + 1);
                    Field field = tClass.getDeclaredField(fieldname);
                    String methodname = "set" + Character.toUpperCase(fieldname.charAt(0)) + fieldname.substring(1);
                    Method method = tClass.getMethod(methodname, field.getType());
                    Object valor = res.getObject(i + 1);
                    method.invoke(obj, valor);
                }
                list.add(obj);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public List<T> findByAllFields(T obj, String... fields){
        String READ = "select * from {class} where {exist}";
        StringBuilder sb = new StringBuilder();

        for (String param : fields) {
            sb.append(param).append("=?");
            if (!param.equals(fields[fields.length - 1])) {
                sb.append(" and ");
            }
        }
        ArrayList<T> lista = new ArrayList<>();
        String read = READ.replace("{class}", tClass.getSimpleName())
                .replace("{exist}", sb.toString());
        try (PreparedStatement ps = conexion.conectar().prepareCall(read)) {
            int parameterIndex = 1;
            for (String param : fields) {
                String methodname = "get" + Character.toUpperCase(param.charAt(0)) + param.substring(1);
                Method method = tClass.getMethod(methodname);
                Object valor = method.invoke(obj);
                ps.setObject(parameterIndex, valor);
                parameterIndex++;
            }

            ResultSet res = ps.executeQuery();
            ResultSetMetaData metaData = res.getMetaData();
            while (res.next()) {
                T obja = tClass.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columns; i++) {
                    String fieldname = metaData.getColumnName(i + 1);
                    Field field = tClass.getDeclaredField(fieldname);
                    String methodname = "set" + Character.toUpperCase(fieldname.charAt(0)) + fieldname.substring(1);
                    Method method = tClass.getMethod(methodname, field.getType());
                    Object valor = res.getObject(i + 1);
                    method.invoke(obj, valor);
                }
                lista.add(obja);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }


    public T findByFields(T obj, String... fields){
        String READ = "select * from {class} where {exist}";
        StringBuilder sb = new StringBuilder();

        for (String param : fields) {
            sb.append(param).append("=?");
            if (!param.equals(fields[fields.length - 1])) {
                sb.append(" and ");
            }
        }

        String read = READ.replace("{class}", tClass.getSimpleName())
                .replace("{exist}", sb.toString());
        try (PreparedStatement ps = conexion.conectar().prepareCall(read)) {
            int parameterIndex = 1;
            for (String param : fields) {
                String methodname = "get" + Character.toUpperCase(param.charAt(0)) + param.substring(1);
                Method method = tClass.getMethod(methodname);
                Object valor = method.invoke(obj);
                ps.setObject(parameterIndex, valor);
                parameterIndex++;
            }

            ResultSet res = ps.executeQuery();
            ResultSetMetaData metaData = res.getMetaData();
            while (res.next()) {
                T obja = tClass.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columns; i++) {
                    String fieldname = metaData.getColumnName(i + 1);
                    Field field = tClass.getDeclaredField(fieldname);
                    String methodname = "set" + Character.toUpperCase(fieldname.charAt(0)) + fieldname.substring(1);
                    Method method = tClass.getMethod(methodname, field.getType());
                    Object valor = res.getObject(i + 1);
                    method.invoke(obja, valor);
                }
                return obja;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        return null;
    }




    public T findById(int id) {
        String READ = "select * from {class} where id=?";
        String read = READ.replace("{class}", tClass.getSimpleName());
        try (PreparedStatement ps = conexion.conectar().prepareCall(read)) {
            ps.setObject(1, id);
            ResultSet res = ps.executeQuery();
            ResultSetMetaData metaData = res.getMetaData();
            while (res.next()) {
                T obj = tClass.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columns; i++) {
                    String fieldname = metaData.getColumnName(i + 1);
                    Field field = tClass.getDeclaredField(fieldname);
                    String methodname = "set" + Character.toUpperCase(fieldname.charAt(0)) + fieldname.substring(1);
                    Method method = tClass.getMethod(methodname, field.getType());
                    Object valor = res.getObject(i + 1);
                    method.invoke(obj, valor);
                }
                return obj;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private T getLastInsert() {
        String READ = "select * from {class} order by id desc limit 1";
        String read = READ.replace("{class}", tClass.getSimpleName());
        try (PreparedStatement ps = conexion.conectar().prepareCall(read)) {
            ResultSet res = ps.executeQuery();
            ResultSetMetaData metaData = res.getMetaData();
            while (res.next()) {
                T obj = tClass.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columns; i++) {
                    String fieldname = metaData.getColumnName(i + 1);
                    Field field = tClass.getDeclaredField(fieldname);
                    String methodname = "set" + Character.toUpperCase(fieldname.charAt(0)) + fieldname.substring(1);
                    Method method = tClass.getMethod(methodname, field.getType());
                    Object valor = res.getObject(i + 1);
                    method.invoke(obj, valor);
                }
                return obj;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
