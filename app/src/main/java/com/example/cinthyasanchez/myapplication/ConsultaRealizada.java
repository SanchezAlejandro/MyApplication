package com.example.cinthyasanchez.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class ConsultaRealizada extends AppCompatActivity {

    EditText fechaConsultaR, horaConsultaR, descripcionConsultaR;
    RelativeLayout todoConsultaR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_realizada);

        fechaConsultaR = findViewById(R.id.EditTextFechaConsultaR);
        horaConsultaR = findViewById(R.id.EditTextHoraConsultaR);
        descripcionConsultaR = findViewById(R.id.EditTextDescripcionConsultaR);
        todoConsultaR = findViewById(R.id.RelativeLayoutTodoConsultaRealizada);

        Tarea objeto = (Tarea) getIntent().getExtras().getSerializable("objeto");

        descripcionConsultaR.setText(objeto.getDescripcion());
        fechaConsultaR.setText(objeto.getFecha());
        horaConsultaR.setText(objeto.getHora());

        fechaConsultaR.setInputType(InputType.TYPE_NULL);

        fondoColor();
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
        todoConsultaR.setBackgroundColor(cb);
    }
}