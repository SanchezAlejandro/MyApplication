package com.example.cinthyasanchez.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AgregarTarea extends AppCompatActivity implements View.OnClickListener{

    EditText editFecha, editHora, editDescripcion;
    ImageView aceptar, cancelar;
    RelativeLayout todoAgregarTarea;

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

        aceptar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        fondoColor();
    }

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
        }
    }

    @SuppressLint("ResourceType")
    public void fondoColor() {
        SharedPreferences preferencias = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);
        int cb = preferencias.getInt("color", 4);

        switch (cb) {
            case 0:
                cb = Color.parseColor(getResources().getString(R.color.amarilloPastel));
                break;
            case 1:
                cb = Color.parseColor(getResources().getString(R.color.ambarPastel));
                break;
            case 2:
                cb = Color.parseColor(getResources().getString(R.color.azulPastel));
                break;
            case 3:
                cb = Color.parseColor(getResources().getString(R.color.azulGris));
                break;
            case 4:
                cb = Color.parseColor(getResources().getString(R.color.blanco));
                break;
            case 5:
                cb = Color.parseColor(getResources().getString(R.color.gris));
                break;
            case 6:
                cb = Color.parseColor(getResources().getString(R.color.indigoPastel));
                break;
            case 7:
                cb = Color.parseColor(getResources().getString(R.color.limaPastel));
                break;
            case 8:
                cb = Color.parseColor(getResources().getString(R.color.purpuraPastel));
                break;
            case 9:
                cb = Color.parseColor(getResources().getString(R.color.rosaPastel));
                break;
            case 10:
                cb = Color.parseColor(getResources().getString(R.color.verdeAzuladoPastel));
                break;
            case 11:
                cb = Color.parseColor(getResources().getString(R.color.verdePastelDos));
                break;
            case 12:
                cb = Color.parseColor(getResources().getString(R.color.verdePastel));
                break;
        }
        todoAgregarTarea.setBackgroundColor(cb);
    }
}