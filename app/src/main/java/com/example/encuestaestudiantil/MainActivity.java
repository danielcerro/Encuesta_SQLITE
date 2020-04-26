package com.example.encuestaestudiantil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Spinner programas;
    EditText codigo,nombre;
    RadioGroup RBcomputador,RBsmartphone,RBinternet;
    Button guardar,listado;
    String Sprograma, Scomputador, Ssmartphone, Sinternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        programas = (Spinner) findViewById(R.id.spinner);
        codigo=findViewById(R.id.edtCodigo);
        nombre= findViewById(R.id.edtNombre);
        RBcomputador=findViewById(R.id.RG1);
        RBsmartphone=findViewById(R.id.RG2);
        RBinternet=findViewById(R.id.RG3);
        guardar=findViewById(R.id.btnGuardar);
        listado=findViewById(R.id.btnLista);


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Progrmas,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programas.setAdapter(adapter);

        //lee el dato seleccionado en el spinner
        programas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sprograma= parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        //lee el dato seleccionado en el primer radio group
        RBcomputador.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rbtSi1){
                    Scomputador ="No";
                }else if (checkedId==R.id.rbtNo1){
                    Scomputador ="Si";
                }
            }
        });
        //lee el dato seleccionado en el segundo radio group
        RBsmartphone.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rbnSi2){
                    Ssmartphone ="No";
                }else if (checkedId==R.id.rbnNo2){
                    Ssmartphone ="Si";
                }
            }
        });

        //lee el dato seleccionado en el ultimo radio group
        RBinternet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rbnSi3){
                    Sinternet ="No";
                }else if (checkedId==R.id.rbnNo3){
                    Sinternet ="Si";
                }
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo.getText().toString().trim().length()==0){
                    Toast.makeText(getApplicationContext(), "Error en el ingreso de datos, verifique la seleccionde datos", Toast.LENGTH_SHORT).show();
                }else{
                    Estudiante est = new Estudiante(codigo.getText().toString().trim(),nombre.getText().toString(), Sprograma, Scomputador, Ssmartphone, Sinternet);
                    RegistrarEstudiante();
                }

                }


        });
        listado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListActivity.class);
                startActivity(i);
            }
        });

    }

    public void RegistrarEstudiante(){
            BaseDatos db=new BaseDatos(this,DefDB.nameDb,null,1);
            SQLiteDatabase escritura = db.getWritableDatabase();

            if(!BuscarEstudiante()){
                ContentValues valores = new ContentValues();
                valores.put(DefDB.col_codigo,codigo.getText().toString().trim());
                valores.put(DefDB.col_nombre,nombre.getText().toString());
                valores.put(DefDB.col_programa,Sprograma);
                valores.put(DefDB.col_computador,Scomputador);
                valores.put(DefDB.col_smartphone,Ssmartphone);
                valores.put(DefDB.col_internet,Sinternet);
                escritura.insert(DefDB.tabla_est,null,valores);
                db.close();
                Toast.makeText(this,"Registro exitoso",Toast.LENGTH_SHORT).show();
                limpiar();
            }


    }

   private boolean BuscarEstudiante() {
        BaseDatos db = new BaseDatos(this, DefDB.nameDb, null, 1);
        SQLiteDatabase lectura = db.getReadableDatabase();

        if (codigo.getText().toString().trim().length()>0) {
            String[] args = new String[]{codigo.getText().toString().trim()};
            Cursor puntero = lectura.query(DefDB.tabla_est,null,"codigo=?",args,null,null,null);

            if (puntero.getCount()>0) {
                limpiar();
                Toast.makeText(this, "Estudiante ya registrado", Toast.LENGTH_SHORT).show();
                lectura.close();
                return true;
            } else {
                lectura.close();
                return false;
            }
        }
        return false;
    }

    private void limpiar() {
        codigo.setText("");
        nombre.setText("");
    }
}



