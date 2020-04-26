package com.example.encuestaestudiantil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Estudiante> listaEstudiantes;
    ArrayList<String> listainfo;

    ListView litsItems;
    BaseDatos conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        conn=new BaseDatos(getApplicationContext(),DefDB.nameDb,null,1);
        litsItems=findViewById(R.id.idListview);

        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listainfo);
        litsItems.setAdapter(adaptador);

        consultarListaEstudiantes();

        litsItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long i) {
                String informacion="Codigo: "+ listaEstudiantes.get(pos).getCodigo()+"\n";
                informacion+="Nombre: "+ listaEstudiantes.get(pos).getNombre()+"\n";
                informacion+="Programa: "+ listaEstudiantes.get(pos).getProgama()+"\n";
                informacion+="Computador: "+ listaEstudiantes.get(pos).getComputador()+"\n";
                informacion+="Smartphone: "+ listaEstudiantes.get(pos).getSmartphone()+"\n";
                informacion+="Internet: "+ listaEstudiantes.get(pos).getInternet()+"\n";

                Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();

                Estudiante user= listaEstudiantes.get(pos);

                Intent intent=new Intent(ListActivity.this,ItemActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("Estudiante",user);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        
        
    }

    private void consultarListaEstudiantes() {
        SQLiteDatabase data=conn.getReadableDatabase();

        Estudiante est;
        listaEstudiantes =new ArrayList<Estudiante>();

        Cursor cursor=data.rawQuery("SELECT * FROM "+DefDB.tabla_est,null);

        while (cursor.moveToNext()){
            est=new Estudiante();
            est.setCodigo(cursor.getString(0));
            est.setNombre(cursor.getString(1));
            est.setProgama(cursor.getString(2));
            est.setComputador(cursor.getString(3));
            est.setSmartphone(cursor.getString(4));
            est.setInternet(cursor.getString(5));

            listaEstudiantes.add(est);
        }
        obtenerLista();
    }
    private void obtenerLista() {
        listainfo=new ArrayList<String>();

        for (int i = 0; i< listaEstudiantes.size(); i++){
            listainfo.add(listaEstudiantes.get(i).getCodigo()+" - "
                    + listaEstudiantes.get(i).getNombre());
        }

    }

}

