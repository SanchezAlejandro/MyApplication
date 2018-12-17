package com.example.cinthyasanchez.myapplication;

import java.io.Serializable;

public class Tarea implements Serializable {

    private String id;
    private String descripcion;
    private String fecha;
    private String hora;

    public Tarea(){
        id = "";
        descripcion = "";
        fecha = "";
        hora = "";
    }

    public Tarea(String i, String d, String f, String h){
        id = i;
        descripcion = d;
        fecha = f;
        hora = h;
    }

    public void setId(String i){
        id = i;
    }

    public void setDescripcion(String d){
        descripcion = d;
    }

    public void setFecha(String f){
        fecha = f;
    }

    public void setHora(String h){
        hora = h;
    }

    public String getId(){
        return id;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getFecha(){
        return fecha;
    }

    public  String getHora(){
        return hora;
    }

}