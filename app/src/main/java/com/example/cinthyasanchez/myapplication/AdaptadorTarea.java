package com.example.cinthyasanchez.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorTarea extends BaseAdapter {

    Context contexto;
    ArrayList<Tarea> listaTareas;

    public AdaptadorTarea(Context contexto, ArrayList<Tarea> listaTareas) {
        this.contexto = contexto;
        this.listaTareas = listaTareas;
    }

    @Override
    public int getCount() {
        return listaTareas.size();
    }

    @Override
    public Object getItem(int i) {
        return listaTareas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista = view;

        SharedPreferences preferencias = contexto.getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);
        int fondoDistribucion = preferencias.getInt("distribucion", 1);

        if(vista == null){
            if(fondoDistribucion == 1){
            LayoutInflater inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vista = inflador.inflate(R.layout.distribucion_vista, viewGroup, false);
            } else {
                LayoutInflater inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vista = inflador.inflate(R.layout.distribucion_vista_dos, viewGroup, false);
            }
        }

        TextView tvDescripcion, tvFecha, tvHora;

        tvDescripcion = vista.findViewById(R.id.tvDescripcion);
        tvFecha = vista.findViewById(R.id.tvFecha);
        tvHora = vista.findViewById(R.id.tvHora);

        tvDescripcion.setText(listaTareas.get(i).getDescripcion());
        tvFecha.setText(listaTareas.get(i).getFecha());
        tvHora.setText(listaTareas.get(i).getHora());

        return vista;
    }
}