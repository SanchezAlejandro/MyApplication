package com.example.cinthyasanchez.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

public class Consulta extends AppCompatActivity implements View.OnClickListener{

    EditText fechaConsulta, horaConsulta, descripcionConsulta;
    ImageView aceptarConsulta, cancelarConsulta, editar;
    RelativeLayout rlBotones, rlEditar, todoConsulta;
    LinearLayout llFechaM, llHoraM, llMod;

    int dia, mes, anio, hora, minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        fechaConsulta = findViewById(R.id.EditTextFechaConsulta);
        horaConsulta = findViewById(R.id.EditTextHoraConsulta);
        descripcionConsulta = findViewById(R.id.EditTextDescripcionConsulta);
        aceptarConsulta = findViewById(R.id.botonAceptarConsulta);
        cancelarConsulta = findViewById(R.id.botonCancelarConsulta);
        editar = findViewById(R.id.botonEditar);
        rlBotones = findViewById(R.id.RelativeLayoutBotones);
        rlEditar = findViewById(R.id.RelativeLayoutEditar);
        todoConsulta = findViewById(R.id.RelativeLayoutTodoConsulta);
        llFechaM = findViewById(R.id.LinearLayoutFechaMod);
        llHoraM = findViewById(R.id.LineaarLayoutHoraConsultaMod);
        llMod = findViewById(R.id.LinearLayoutFechaMod);

        fechaConsulta.setInputType(InputType.TYPE_NULL);

        aceptarConsulta.setOnClickListener(this);
        cancelarConsulta.setOnClickListener(this);
        editar.setOnClickListener(this);
        llFechaM.setOnClickListener(this);
        llHoraM.setOnClickListener(this);

        Tarea objeto = (Tarea) getIntent().getExtras().getSerializable("objeto");

        descripcionConsulta.setText(objeto.getDescripcion());
        fechaConsulta.setText(objeto.getFecha());
        horaConsulta.setText(objeto.getHora());
        int dedondevengo = getIntent().getExtras().getInt("dedondevengo");

        if(dedondevengo == 1){
            descripcionConsulta.setEnabled(true);
            llMod.setVisibility(View.VISIBLE);
            rlBotones.setVisibility(View.VISIBLE);
            rlEditar.setVisibility(View.INVISIBLE);
        }

        fondoColor();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonAceptarConsulta:
                Intent i = getIntent();
                Tarea objeto = (Tarea) getIntent().getExtras().getSerializable("objeto");
                int posicion = getIntent().getExtras().getInt("posicion");
                objeto.setDescripcion(descripcionConsulta.getText().toString());
                objeto.setFecha(fechaConsulta.getText().toString());
                objeto.setHora(horaConsulta.getText().toString());
                i.putExtra("objetoModf", objeto);
                i.putExtra("posicion", posicion);
                setResult(3, i);
                finish();
                break;
            case R.id.botonCancelarConsulta:
                setResult(RESULT_CANCELED);
                descripcionConsulta.setEnabled(false);
                llMod.setVisibility(View.INVISIBLE);
                rlBotones.setVisibility(View.INVISIBLE);
                rlEditar.setVisibility(View.VISIBLE);
                //finish();
                break;
            case R.id.botonEditar:
                descripcionConsulta.setEnabled(true);
                llMod.setVisibility(View.VISIBLE);
                rlBotones.setVisibility(View.VISIBLE);
                rlEditar.setVisibility(View.INVISIBLE);
                break;
            case R.id.LinearLayoutFechaMod:
                final Calendar calendario = Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio = calendario.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fechaConsulta.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                }, anio, mes, dia);
                dpd.show();
                break;
            case R.id.LineaarLayoutHoraConsultaMod:
                final Calendar calendarioHora = Calendar.getInstance();
                hora = calendarioHora.get(Calendar.HOUR_OF_DAY);
                minuto = calendarioHora.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        horaConsulta.setText(hourOfDay+":"+minute);
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
        todoConsulta.setBackgroundColor(cb);
    }
}