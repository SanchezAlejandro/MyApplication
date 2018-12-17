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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Serializable, AdapterView.OnItemSelectedListener {

    ArrayList<Tarea> listaDeTareas;
    ListView listviewTareas;
    ImageView botonAgregar;
    AdaptadorTarea adaptador;
    RelativeLayout color, bienvenida, agregar, muestraAgregar, areaDeLista, muestraareaDeLista, tarea, muestraTarea, xRoja, tresPuntitos;
    Button okColor, comenzar, siguienteAgregar, siguienteAreaDeLista, siguienteTarea, siguientexRoja, listo;
    Spinner spin, spinLetra;

    AdminDB baseDatosT = new AdminDB(this);

    int ultimo = 0;
    int m = 1;
    int l = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listviewTareas = findViewById(R.id.listviewTareas);
        botonAgregar = findViewById(R.id.botonAgregar);
        listaDeTareas = new ArrayList<Tarea>();
        color = findViewById(R.id.relativeLayoutColores);
        okColor = findViewById(R.id.buttonOkColor);
        bienvenida = findViewById(R.id.RelativeLayoutHola);
        agregar = findViewById(R.id.RelativeLayoutConsejoAgregar);
        muestraAgregar = findViewById(R.id.RelativeLayoutMuestraAgregar);
        areaDeLista = findViewById(R.id.RelativeLayoutConsejoAreaDeLista);
        muestraareaDeLista = findViewById(R.id.RelativeLayoutMuestraAreaDeLista);
        tarea = findViewById(R.id.RelativeLayoutConsejoTateaDeLista);
        muestraTarea = findViewById(R.id.RelativeLayoutMuestraTareaDeLista);
        xRoja = findViewById(R.id.RelativeLayoutConsejoSalir);
        tresPuntitos = findViewById(R.id.RelativeLayoutConsejoOpciones);
        comenzar = findViewById(R.id.BotonComencemos);
        siguienteAgregar = findViewById(R.id.BotonSiguienteAgregar);
        siguienteAreaDeLista = findViewById(R.id.BotonSiguienteArea);
        siguienteTarea = findViewById(R.id.BotonSiguienteTarea);
        siguientexRoja = findViewById(R.id.BotonSiguienteSalir);
        listo = findViewById(R.id.BotonTerminarConsejos);

        botonAgregar.setOnClickListener(this);
        okColor.setOnClickListener(this);
        comenzar.setOnClickListener(this);
        siguienteAgregar.setOnClickListener(this);
        siguienteAreaDeLista.setOnClickListener(this);
        siguienteTarea.setOnClickListener(this);
        siguientexRoja.setOnClickListener(this);
        listo.setOnClickListener(this);

        listviewTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(MainActivity.this, Consulta.class);
                i.putExtra("objeto", listaDeTareas.get(position));
                i.putExtra("posicion", position);
                i.putExtra("dedondevengo", 0);
                startActivityForResult(i, 3);
            }
        });

        registerForContextMenu(listviewTareas);

        try {
            leerArchivo();
        }catch (Exception e){
            Toast.makeText(this, "Esta es la exception del leer en el onCreate", Toast.LENGTH_SHORT).show();
        }

        spin = findViewById(R.id.spinnerColores);
        spinLetra = findViewById(R.id.spinnerColorDeLetra);

        spin.setOnItemSelectedListener(this);
        spinLetra.setOnItemSelectedListener(this);

        String[] colores = {"Amarillo pastel","Ambar","Azul pastel", "Azul gris","Blanco","Gris","Índigo pastel","Lima pastel","Purpura Pastel","Rosa pastel","Verde azulado","Verde menta","Verde pastel"};
        spin.setAdapter(new ArrayAdapter<String>(this, R.layout.spinneritem, colores));
        String[] colorDeLetra = {"Blanco","Negro"};
        spinLetra.setAdapter(new ArrayAdapter<String>(this, R.layout.spinneritem, colorDeLetra));

        SharedPreferences preferencias = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);
        int indiceSpinner = preferencias.getInt("color", 4);
        spin.setSelection(indiceSpinner);
        int indiceSpinnerLetras = preferencias.getInt("distribucion", 1);
        spinLetra.setSelection(indiceSpinnerLetras);

        int inicio = preferencias.getInt("primera", 0);
        inicio = inicio+1;
        SharedPreferences.Editor editorR = preferencias.edit();
        editorR.putInt("primera", inicio);
        editorR.commit();

        int primeraVez = preferencias.getInt("primera", 1);
        if(primeraVez>1){
            bienvenida.setVisibility(View.INVISIBLE);
            listviewTareas.setVisibility(View.VISIBLE);
            botonAgregar.setVisibility(View.VISIBLE);
        } else {

        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.opciones,menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getId()==R.id.listviewTareas){
            AdapterView.AdapterContextMenuInfo infoTarea = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String[] elementosMenu = getResources().getStringArray(R.array.elementosMenu);
            menu.setHeaderTitle(listaDeTareas.get(infoTarea.position).getDescripcion());
            for (int i=0; i<elementosMenu.length; i++ ){
                menu.add(Menu.NONE, i, i, elementosMenu[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo infoTarea = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String[] elementosMenu = getResources().getStringArray(R.array.elementosMenu);

        switch (item.getItemId()) {
            case 0: //editar
                Intent i = new Intent(MainActivity.this, Consulta.class);
                i.putExtra("objeto", listaDeTareas.get(infoTarea.position));
                i.putExtra("posicion", infoTarea.position);
                i.putExtra("dedondevengo", 1);
                startActivityForResult(i, 3);
                break;
            case 1: //marcar como realizada


                break;
            case 2: //ver detalles
                Intent i2 = new Intent(MainActivity.this, Consulta.class);
                i2.putExtra("objeto", listaDeTareas.get(infoTarea.position));
                i2.putExtra("posicion", infoTarea.position);
                i2.putExtra("dedondevengo", 2);
                startActivity(i2);
                break;
            case 3: //eliminar
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Eliminar");
                dialogo1.setMessage("¿Está seguro eliminar esta tarea?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        String idT = listaDeTareas.get(infoTarea.position).getId();
                        SQLiteDatabase baseT = baseDatosT.getWritableDatabase();

                        baseDatosT.eliminarTareaPendiente(baseT, idT);

                        listaDeTareas.remove(infoTarea.position);
                        adaptador.notifyDataSetChanged();
                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Toast.makeText(MainActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogo1.show();
                break;
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.salir:
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Espera");
                dialogo1.setMessage("¿Está seguro de salir de la aplicación?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        finish();
                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
                break;
            case R.id.ajustes:
                color.setVisibility(View.VISIBLE);
                break;
            case R.id.ayuda:
                Intent intentAyuda = new Intent(this, Ayuda.class);
                startActivity(intentAyuda);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Espera");
        dialogo1.setMessage("¿Está seguro de salir de la aplicación?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                finish();
            }
        });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, AgregarTarea.class);
        switch (view.getId()){
            case R.id.botonAgregar:
                startActivityForResult(i, 1);
                break;
            case R.id.buttonOkColor:
                color.setVisibility(View.INVISIBLE);
                SharedPreferences preferencias = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferencias.edit();
                editor.putInt("color", m);
                editor.putInt("distribucion", l);
                editor.commit();
                //adaptador.notifyDataSetChanged();
                //listviewTareas.setAdapter(adaptador);
                //leerArchivo();
                break;
            case R.id.BotonComencemos:
                bienvenida.setVisibility(View.INVISIBLE);
                agregar.setVisibility(View.VISIBLE);
                muestraAgregar.setVisibility(View.VISIBLE);
                botonAgregar.setVisibility(View.VISIBLE);
                break;
            case R.id.BotonSiguienteAgregar:
                agregar.setVisibility(View.INVISIBLE);
                muestraAgregar.setVisibility(View.INVISIBLE);
                areaDeLista.setVisibility(View.VISIBLE);
                muestraareaDeLista.setVisibility(View.VISIBLE);
                break;
            case R.id.BotonSiguienteArea:
                areaDeLista.setVisibility(View.INVISIBLE);
                tarea.setVisibility(View.VISIBLE);
                muestraTarea.setVisibility(View.VISIBLE);
                break;
            case R.id.BotonSiguienteTarea:
                tarea.setVisibility(View.INVISIBLE);
                muestraareaDeLista.setVisibility(View.INVISIBLE);
                muestraTarea.setVisibility(View.INVISIBLE);
                xRoja.setVisibility(View.VISIBLE);
                break;
            case R.id.BotonSiguienteSalir:
                xRoja.setVisibility(View.INVISIBLE);
                tresPuntitos.setVisibility(View.VISIBLE);
                break;
            case R.id.BotonTerminarConsejos:
                tresPuntitos.setVisibility(View.INVISIBLE);
                listviewTareas.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SQLiteDatabase baseT = baseDatosT.getWritableDatabase();

        switch (resultCode) {
            case RESULT_OK:
                String descripcion = data.getExtras().getString("descripcion");
                String fecha = data.getExtras().getString("fecha");
                String hora = data.getExtras().getString("hora");

                guardar(descripcion, fecha, hora);

                listaDeTareas.add(new Tarea(String.valueOf(ultimo), descripcion, fecha, hora));

                adaptador = new AdaptadorTarea(this, listaDeTareas);
                listviewTareas.setAdapter(adaptador);

                break;
            case RESULT_CANCELED:
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Tarea obj = (Tarea) data.getExtras().getSerializable("objetoModf");
                int position = data.getExtras().getInt("posicion");

                baseDatosT.modificarTareaPendiente(baseT, obj.getDescripcion(), obj.getFecha(), obj.getHora(), obj.getId());

                listaDeTareas.set(position, obj);
                adaptador.notifyDataSetChanged();
                listviewTareas.setAdapter(adaptador);
                break;
        }
    }

    public void leerArchivo() {
        SQLiteDatabase baseT = baseDatosT.getWritableDatabase();
        Cursor tablaT = baseDatosT.consultaTotal(baseT);
        try {
            int numDeTarea = tablaT.getCount();
            tablaT.moveToFirst();
            for (int i = 0; i < numDeTarea; i++){
                listaDeTareas.add(new Tarea(tablaT.getString(0), tablaT.getString(1), tablaT.getString(2), tablaT.getString(3)));
                tablaT.moveToNext();

                adaptador = new AdaptadorTarea(this, listaDeTareas);
                listviewTareas.setAdapter(adaptador);
                ultimo = i;
            }

            adaptador.notifyDataSetChanged();
            //listviewTareas.setAdapter(adaptador);
        }catch (Exception e){
        }
    }

    protected void guardar(String descripcion, String fecha, String hora) {
        SQLiteDatabase baseT = baseDatosT.getWritableDatabase();
        baseDatosT.insertarTareaPendiente(baseT, descripcion, fecha, hora);

        Toast.makeText(this, "Tarea guardada", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerColores:
                String item = parent.getItemAtPosition(position).toString();
                int i = obtenerPosicionItem(spin, item);
                View contenedor = view.getRootView();

                SharedPreferences preferencias = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);

                m = i;

                SharedPreferences.Editor editor = preferencias.edit();
                editor.putInt("color", m);
                editor.commit();

                switch (i) {
                    case 0:
                        i = Color.parseColor(getResources().getString(R.color.amarilloPastel));
                        break;
                    case 1:
                        i = Color.parseColor(getResources().getString(R.color.ambarPastel));
                        break;
                    case 2:
                        i = Color.parseColor(getResources().getString(R.color.azulPastel));
                        break;
                    case 3:
                        i = Color.parseColor(getResources().getString(R.color.azulGris));
                        break;
                    case 4:
                        i = Color.parseColor(getResources().getString(R.color.blanco));
                        break;
                    case 5:
                        i = Color.parseColor(getResources().getString(R.color.gris));
                        break;
                    case 6:
                        i = Color.parseColor(getResources().getString(R.color.indigoPastel));
                        break;
                    case 7:
                        i = Color.parseColor(getResources().getString(R.color.limaPastel));
                        break;
                    case 8:
                        i = Color.parseColor(getResources().getString(R.color.purpuraPastel));
                        break;
                    case 9:
                        i = Color.parseColor(getResources().getString(R.color.rosaPastel));
                        break;
                    case 10:
                        i = Color.parseColor(getResources().getString(R.color.verdeAzuladoPastel));
                        break;
                    case 11:
                        i = Color.parseColor(getResources().getString(R.color.verdePastel));
                        break;
                    case 12:
                        i = Color.parseColor(getResources().getString(R.color.verdePastelDos));
                        break;
                }
                contenedor.setBackgroundColor(i);
                break;

            case R.id.spinnerColorDeLetra:
                String itemLetra = parent.getItemAtPosition(position).toString();
                int iLetra=obtenerPosicionItem(spinLetra, itemLetra);

                SharedPreferences preferenciasL = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);

                l = iLetra;

                SharedPreferences.Editor editorL = preferenciasL.edit();
                editorL.putInt("distribucion", l);
                editorL.commit();

                switch (l) {
                    case 0:
                        editorL.putInt("distribucion", 0);
                        editorL.commit();
                        break;
                    case 1:
                        editorL.putInt("distribucion", 1);
                        editorL.commit();
                        break;
                }
                //adaptador.notifyDataSetChanged();
                listviewTareas.setAdapter(adaptador);
                //leerArchivo();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
        public static int obtenerPosicionItem(Spinner spinner, String color) {

            int posicion = 0;

            for (int i = 0; i < spinner.getCount(); i++) {
                if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(color)) {
                    posicion = i;
                }
            }
            return posicion;
        }
}