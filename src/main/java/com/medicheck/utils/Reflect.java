package com.medicheck.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Reflect<T> {
    private Class<T> tClass;

    public Reflect(Class<T> tClass) {
        this.tClass = tClass;
    }

    public T getObjectParam(Map<String, String[]> paramMap){
        try {
            return setMetodo(paramMap);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private T setMetodo(Map<String, String[]> paramMap) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ParseException {
        T obj = tClass.getDeclaredConstructor().newInstance();
        for (String name : paramMap.keySet()) {
            try {
                Field field = tClass.getDeclaredField(name);
                String methodname = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                Method method = tClass.getMethod(methodname, field.getType());
                String[] values = paramMap.get(name);
                Object valor = convertValue(values[0], field.getType());
                method.invoke(obj, valor);
            } catch (NoSuchFieldException e) {

            }

        }
        return obj;
    }

    private Object convertValue(String value, Class<?> targetType) throws ParseException, ParseException {
        if (targetType == Date.class) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(value);
        } else if (targetType == Time.class) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            return new Time(timeFormat.parse(value).getTime());
        } else if (targetType == int.class) {
            return Integer.parseInt(value);
        }
        return value;
    }

}
