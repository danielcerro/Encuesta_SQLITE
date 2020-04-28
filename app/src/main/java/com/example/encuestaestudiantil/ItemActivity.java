package com.example.encuestaestudiantil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    EditText  nombre, computador, smartphone, internet;
    Button actualizar,cancelar;
    ControladorBD controlador;
    Spinner programa;
    String Sprograma,Scodigo;
    Estudiante user;
    TextView codigo;
    int indice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        codigo = findViewById(R.id.idTextcodigo);
        nombre = findViewById(R.id.edtnom);
        programa = findViewById(R.id.idSpiner);
        computador = findViewById(R.id.edtpc);
        smartphone = findViewById(R.id.edtsmart);
        internet = findViewById(R.id.edtinter);
        actualizar=findViewById(R.id.btnActualizar);
        cancelar=findViewById(R.id.btncancelar);

        controlador = new ControladorBD(getApplicationContext());


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Progrmas,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programa.setAdapter(adapter);

        //lee el dato seleccionado en el spinner
        programa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sprograma= parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        Intent i = getIntent();
        indice = i.getIntExtra("indice", 0);
        ArrayList<Estudiante> lista = controlador.obtenerRegistros();

        Estudiante estudiante = lista.get(indice);
        Scodigo = estudiante.getCodigo();

        nombre.setText(estudiante.getNombre());
        computador.setText(estudiante.getComputador());
        smartphone.setText(estudiante.getSmartphone());
        internet.setText(estudiante.getInternet());

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Estudiante estudiant = new Estudiante(Scodigo, nombre.getText().toString(),Sprograma, computador.getText().toString(), smartphone.getText().toString(), internet.getText().toString());
                int retorno = controlador.actualizarRegistro(estudiant);
                if (retorno == 1) {
                    Toast.makeText(getApplicationContext(), "actualizacion exitosa", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "fallo en la actualizacion", Toast.LENGTH_SHORT).show();
                }
                limpiar();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

    }
   

    private void limpiar() {
        nombre.setText("");
        computador.setText("");
        smartphone.setText("");
        internet.setText("");
    }

}
