package com.example.encuestaestudiantil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ListView lista;

    ArrayList<Estudiante> listaEstudiante;

    ControladorBD controlador;

    ListaEstudiantesAdapter adapter;

    SearchView buscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        controlador = new ControladorBD(getApplicationContext());
        lista = findViewById(R.id.idListview);
        listaEstudiante = controlador.obtenerRegistros();

        adapter = new ListaEstudiantesAdapter(getApplicationContext(), R.layout.item_list, listaEstudiante);
        lista.setAdapter(adapter);

        registerForContextMenu(lista);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Estudiante> listaEstudents = controlador.obtenerRegistros();
                ListaEstudiantesAdapter adaptador = new ListaEstudiantesAdapter(getApplicationContext(), R.layout.item_list, listaEstudents);
                lista.setAdapter(adaptador);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "modificacion cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_lista, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.IdModificar:
                modificarRegistro(menuInfo.position);
                return true;
            case R.id.IdEliminar:
                borrarRegistro(menuInfo.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void modificarRegistro(int posicion) {
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("indice", posicion);
        startActivityForResult(intent, 2);
    }

    private void borrarRegistro(int posicion) {
        int retorno = controlador.borrarRegistro(listaEstudiante.get(posicion));
        if (retorno == 1) {
            Toast.makeText(getApplicationContext(), "registro eliminado", Toast.LENGTH_SHORT).show();
            listaEstudiante = controlador.obtenerRegistros();
            adapter = new ListaEstudiantesAdapter(getApplicationContext(), R.layout.item_list, listaEstudiante);
            lista.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "error al borrar", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_searchin, menu);
        MenuItem item= menu.findItem(R.id.idbuscador);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter.setFilter(listaEstudiante);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {

            ArrayList<Estudiante>listafiltrada=filter(listaEstudiante,newText);
           adapter.setFilter(listafiltrada);

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private ArrayList<Estudiante> filter(ArrayList<Estudiante> estudiantes,String texto){
        ArrayList<Estudiante>listaFiltrada=new ArrayList<>();
        try{
            texto=texto.toLowerCase();

            for(Estudiante estu : estudiantes){
                String cod=estu.getCodigo().toString();
                String nom=estu.getNombre().toLowerCase();
                String prog=estu.getProgama().toLowerCase();
                if (cod.contains(texto) || nom.contains(texto) || prog.contains(texto)){
                    listaFiltrada.add(estu);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaFiltrada;
    }
}
