package com.example.encuestaestudiantil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ControladorBD controlador;
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

        controlador= new ControladorBD(getApplicationContext());

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
                } else{
                    Estudiante est = new Estudiante(codigo.getText().toString().trim(),nombre.getText().toString(), Sprograma, Scomputador, Ssmartphone, Sinternet);
                    if (!controlador.buscarEstudiante(codigo.getText().toString().trim())){
                        Log.d("Buscar", "No encontrado");
                        long retorno = controlador.agregarRegistro(est);
                        if (retorno != -1) {
                            Toast.makeText(v.getContext(), "Estudiante guardado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "registro fallido", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Log.d("Buscar", "encontrado");
                        Toast.makeText(getApplicationContext(),"Estudiante ya esta registrado",Toast.LENGTH_SHORT).show();
                    }

                    limpiar();
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

    private void limpiar() {
        codigo.setText("");
        nombre.setText("");
    }

}



