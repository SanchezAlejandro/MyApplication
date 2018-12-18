package com.example.cinthyasanchez.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import java.util.Calendar;

public class AgregarTarea extends AppCompatActivity implements View.OnClickListener{

    EditText editFecha, editHora, editDescripcion;
    ImageView aceptar, cancelar;
    RelativeLayout todoAgregarTarea;
    LinearLayout llFecha, llHora;

    int dia, mes, anio, hora, minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarea);

        editDescripcion = findViewById(R.id.EditTextDesacripcion);
        editFecha = findViewById(R.id.EditTextFecha);
        editHora = findViewById(R.id.EditTextHora);
        aceptar = findViewById(R.id.botonAceptar);
        cancelar = findViewById(R.id.botonCancelar);
        todoAgregarTarea = findViewById(R.id.RelativeLayoutTodoAgregarTarea);
        llFecha = findViewById(R.id.LineaarLayoutSeleccionarFecha);
        llHora = findViewById(R.id.LineaarLayoutSeleccionarHora);

        aceptar.setOnClickListener(this);
        cancelar.setOnClickListener(this);
        llFecha.setOnClickListener(this);
        llHora.setOnClickListener(this);

        editFecha.setInputType(InputType.TYPE_NULL);

        fondoColor();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.botonAceptar:
                Intent i = getIntent();
                i.putExtra("descripcion", editDescripcion.getText().toString());
                i.putExtra("fecha", editFecha.getText().toString());
                i.putExtra("hora", editHora.getText().toString());
                setResult(RESULT_OK, i);
                finish();
                break;
            case R.id.botonCancelar:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.LineaarLayoutSeleccionarFecha:
                final Calendar calendario = Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio = calendario.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                }, anio, mes, dia);
                dpd.show();
                break;
            case R.id.LineaarLayoutSeleccionarHora:
                final Calendar calendarioHora = Calendar.getInstance();
                hora = calendarioHora.get(Calendar.HOUR_OF_DAY);
                minuto = calendarioHora.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editHora.setText(hourOfDay+":"+minute);
                    }
                }, hora, minuto, false);
                tpd.show();
                break;
        }
    }

    @SuppressLint("ResourceType")
    public void fondoColor() {
        SharedPreferences preferencias = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);
        int cb = preferencias.getInt("color", 1);

        switch (cb) {
            case 0:
                cb = Color.parseColor(getResources().getString(R.color.azulClaro));
                break;
            case 1:
                cb = Color.parseColor(getResources().getString(R.color.blanco));
                break;
            case 2:
                cb = Color.parseColor(getResources().getString(R.color.gris));
                break;
            case 3:
                cb = Color.parseColor(getResources().getString(R.color.marron));
                break;
            case 4:
                cb = Color.parseColor(getResources().getString(R.color.morado));
                break;
            case 5:
                cb = Color.parseColor(getResources().getString(R.color.naranja));
                break;
            case 6:
                cb = Color.parseColor(getResources().getString(R.color.rojo));
                break;
            case 7:
                cb = Color.parseColor(getResources().getString(R.color.rosa));
                break;
            case 8:
                cb = Color.parseColor(getResources().getString(R.color.verde));
                break;
        }
        todoAgregarTarea.setBackgroundColor(cb);
    }
}