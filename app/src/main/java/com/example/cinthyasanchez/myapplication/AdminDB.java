package com.example.cinthyasanchez.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminDB extends SQLiteOpenHelper {

    final static String NOMBREBD = "bdTareas.db";
    final static Integer VERSIONBD = 1;
    final static String NOMBRETABLATAREASPENDIENTES = "tablaTareasPendientes";
    final static String NOMBRECAMPOIDTAREAPENDIENTE = "idTareaPendiente";
    final static String NOMBRECAMPODESCRIPCION = "descripcion";
    final static String NOMBRECAMPOFECHA = "fecha";
    final static String NOMBRECAMPOHORA = "hora";
    final static String CREARTABLATAREASPENDIENTES = "CREATE TABLE "+NOMBRETABLATAREASPENDIENTES+" ("+NOMBRECAMPOIDTAREAPENDIENTE+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +NOMBRECAMPODESCRIPCION+" TEXT,"+NOMBRECAMPOFECHA+" INTEGER,"+NOMBRECAMPOHORA+" INTEGER);";
    final static String CONSULTASQLP = "SELECT * FROM "+NOMBRETABLATAREASPENDIENTES+";";

    final static String NOMBRETABLATAREASREALIZADAS = "tablaTareasRealizadas";
    final static String NOMBRECAMPOIDTAREAREALIZADAS = "idTareaRealizadas";
    final static String NOMBRECAMPODESCRIPCIONREALIZADAS = "descripcion";
    final static String NOMBRECAMPOFECHAREALIZADAS = "fecha";
    final static String NOMBRECAMPOHORAREALIZADAS = "hora";
    final static String CREARTABLATAREASREALIZADAS = "CREATE TABLE "+NOMBRETABLATAREASREALIZADAS+" ("+NOMBRECAMPOIDTAREAREALIZADAS+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +NOMBRECAMPODESCRIPCIONREALIZADAS+" TEXT,"+NOMBRECAMPOFECHAREALIZADAS+" INTEGER,"+NOMBRECAMPOHORAREALIZADAS+" INTEGER);";
    final static String CONSULTASQLR = "SELECT * FROM "+NOMBRETABLATAREASREALIZADAS+";";

    public AdminDB(Context context) {
        super(context, NOMBREBD, null, VERSIONBD);

    }

    @Override
    public void onCreate(SQLiteDatabase bd) {

        bd.execSQL(CREARTABLATAREASPENDIENTES);
        bd.execSQL(CREARTABLATAREASREALIZADAS);
    }

    // Métodos para tareas pendientes
    public void insertarTareaPendiente(SQLiteDatabase bd, String descripcion, String fecha, String hora){
        final String insertar = "INSERT INTO "+NOMBRETABLATAREASPENDIENTES+"("+NOMBRECAMPODESCRIPCION+","+NOMBRECAMPOFECHA+","+NOMBRECAMPOHORA+
                ") VALUES('"+descripcion+"','"+fecha+"','"+hora+"');";
        bd.execSQL(insertar);
    }

    public void eliminarTareaPendiente(SQLiteDatabase bd, String id){
        final String eliminar = "DELETE FROM "+NOMBRETABLATAREASPENDIENTES+" WHERE "+NOMBRECAMPOIDTAREAPENDIENTE+"='"+id+"';";
        bd.execSQL(eliminar);
    }

    public void modificarTareaPendiente(SQLiteDatabase bd, String descripcion, String fecha, String hora, String id){
        final String modificar = "UPDATE "+NOMBRETABLATAREASPENDIENTES+" SET "+NOMBRECAMPODESCRIPCION+"='"+descripcion+"', "+NOMBRECAMPOFECHA+"='"+fecha+"', "+
                NOMBRECAMPOHORA+"='"+hora+"' WHERE "+NOMBRECAMPOIDTAREAPENDIENTE+"='"+id+"';";
        bd.execSQL(modificar);
    }

    //Métodos ara tareas realizadas
    public void insertarTareaRealizada(SQLiteDatabase bd, String descripcion, String fecha, String hora){
        final String insertar = "INSERT INTO "+NOMBRETABLATAREASREALIZADAS+"("+NOMBRECAMPODESCRIPCIONREALIZADAS+","+NOMBRECAMPOFECHAREALIZADAS+","+NOMBRECAMPOHORAREALIZADAS+
                ") VALUES('"+descripcion+"','"+fecha+"','"+hora+"');";
        bd.execSQL(insertar);
    }

    public void eliminarTareaRealizada(SQLiteDatabase bd, String id){
        final String eliminar = "DELETE FROM "+NOMBRETABLATAREASREALIZADAS+" WHERE "+NOMBRECAMPOIDTAREAREALIZADAS+"='"+id+"';";
        bd.execSQL(eliminar);
    }

    public Cursor consultaTotal(SQLiteDatabase bd){

        Cursor t = bd.rawQuery(CONSULTASQLP, null);
        return t;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}