package com.example.cinthyasanchez.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class TareasPendientes extends AppCompatActivity {

    ArrayList<Tarea> listaDeTareasRealizadas;
    ListView listviewTareasRealizadas;
    AdaptadorTarea adaptadorR;
    RelativeLayout todoRealizadas;

    AdminDB baseDatosR = new AdminDB(this);

    int ultimoR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_pendientes);

        listviewTareasRealizadas = findViewById(R.id.listviewTareasRealizadas);
        listaDeTareasRealizadas = new ArrayList<Tarea>();
        todoRealizadas = findViewById(R.id.RelativeLayoutTodoRealizadas);

        listviewTareasRealizadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(TareasPendientes.this, ConsultaRealizada.class);
                i.putExtra("objeto", listaDeTareasRealizadas.get(position));
                i.putExtra("posicion", position);
                i.putExtra("dedondevengo", 0);
                startActivityForResult(i, 3);
            }
        });

        registerForContextMenu(listviewTareasRealizadas);

        try {
            leerArchivo();
        }catch (Exception e){
            Toast.makeText(this, "Esta es la exception del leer en el onCreate", Toast.LENGTH_SHORT).show();
        }

        fondoColor();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getId()==R.id.listviewTareasRealizadas){
            AdapterView.AdapterContextMenuInfo infoTarea = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String[] elementosMenuR = getResources().getStringArray(R.array.elementosMenuRealizadas);
            menu.setHeaderTitle(listaDeTareasRealizadas.get(infoTarea.position).getDescripcion());
            for (int i=0; i<elementosMenuR.length; i++ ){
                menu.add(Menu.NONE, i, i, elementosMenuR[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo infoTarea = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String[] elementosMenu = getResources().getStringArray(R.array.elementosMenu);

        switch (item.getItemId()) {
            case 0: //ver detalles
                Intent i2 = new Intent(TareasPendientes.this, ConsultaRealizada.class);
                i2.putExtra("objeto", listaDeTareasRealizadas.get(infoTarea.position));
                i2.putExtra("posicion", infoTarea.position);
                //i2.putExtra("dedondevengo", 2);
                startActivity(i2);
                break;
            case 1: //eleiminar
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Eliminar");
                dialogo1.setMessage("¿Está seguro eliminar esta tarea?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        String idR = listaDeTareasRealizadas.get(infoTarea.position).getId();
                        SQLiteDatabase baseR = baseDatosR.getWritableDatabase();

                        baseDatosR.eliminarTareaRealizada(baseR, idR);

                        listaDeTareasRealizadas.remove(infoTarea.position);
                        adaptadorR.notifyDataSetChanged();
                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        //Toast.makeText(TareasPendientes.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogo1.show();
                break;
        }
        return true;
    }

    public void leerArchivo() {
        SQLiteDatabase baseR = baseDatosR.getWritableDatabase();
        Cursor tablaR = baseDatosR.consultaTotalR(baseR);
        try {
            int numDeTareaR = tablaR.getCount();
            tablaR.moveToFirst();
            for (int i = 0; i < numDeTareaR; i++){
                listaDeTareasRealizadas.add(new Tarea(tablaR.getString(0), tablaR.getString(1), tablaR.getString(2), tablaR.getString(3)));
                tablaR.moveToNext();

                adaptadorR = new AdaptadorTarea(this, listaDeTareasRealizadas);
                listviewTareasRealizadas.setAdapter(adaptadorR);
                ultimoR = i;
            }

            adaptadorR.notifyDataSetChanged();
            //listviewTareasRealizadas.setAdapter(adaptadorR);
        }catch (Exception e){
        }
    }

    protected void guardar(String descripcion, String fecha, String hora) {
        SQLiteDatabase baseR = baseDatosR.getWritableDatabase();
        baseDatosR.insertarTareaPendiente(baseR, descripcion, fecha, hora);

        Toast.makeText(this, "Tarea guardada", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SQLiteDatabase baseT = baseDatosR.getWritableDatabase();

        switch (resultCode) {
            case 3:
                Tarea obj = (Tarea) data.getExtras().getSerializable("objetoModf");
                int position = data.getExtras().getInt("posicion");

                baseDatosR.modificarTareaRealizada(baseT, obj.getDescripcion(), obj.getFecha(), obj.getHora(), obj.getId());

                listaDeTareasRealizadas.set(position, obj);
                adaptadorR.notifyDataSetChanged();
                listviewTareasRealizadas.setAdapter(adaptadorR);
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
        todoRealizadas.setBackgroundColor(cb);
    }
}