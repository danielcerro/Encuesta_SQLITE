package com.example.encuestaestudiantil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {

    EditText codigo, nombre, programa, computador, smartphone, internet;
    Button actualizar,eliminar;
    BaseDatos conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        codigo = findViewById(R.id.editcod);
        nombre = findViewById(R.id.edtnom);
        programa = findViewById(R.id.edtprogram);
        computador = findViewById(R.id.edtpc);
        smartphone = findViewById(R.id.edtsmart);
        internet = findViewById(R.id.edtinter);
        actualizar=findViewById(R.id.btnActualizar);
        eliminar=findViewById(R.id.btneliminar);

        conn=new BaseDatos(getApplicationContext(),DefDB.nameDb,null,1);

        Bundle objetoEnviado = getIntent().getExtras();
        Estudiante user = null;

        if (objetoEnviado != null) {
            user = (Estudiante) objetoEnviado.getSerializable("Estudiante");
            codigo.setText(user.getCodigo());
            nombre.setText(user.getNombre());
            programa.setText(user.getProgama());
            computador.setText(user.getComputador());
            smartphone.setText(user.getSmartphone());
            internet.setText(user.getInternet());
        }
    }
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnActualizar: actualizarUsuario();
                break;
            case R.id.btneliminar: eliminarUsuario();
                break;
        }

    }

    private void eliminarUsuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={codigo.getText().toString()};

        db.delete(DefDB.tabla_est,DefDB.col_codigo+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se Eliminó el estudiante",Toast.LENGTH_LONG).show();
        codigo.setText("");
        limpiar();
        db.close();
    }


    private void actualizarUsuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={codigo.getText().toString()};
        ContentValues values=new ContentValues();
        values.put(DefDB.tabla_est,nombre.getText().toString());
        values.put(DefDB.tabla_est,programa.getText().toString());
        values.put(DefDB.tabla_est,computador.getText().toString());
        values.put(DefDB.tabla_est,smartphone.getText().toString());
        values.put(DefDB.tabla_est,internet.getText().toString());

        db.update(DefDB.tabla_est,values,DefDB.col_codigo+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se actualizó el estudiante",Toast.LENGTH_LONG).show();
        db.close();

    }

    private void limpiar() {
        nombre.setText("");
        programa.setText("");
        computador.setText("");
        smartphone.setText("");
        internet.setText("");
    }

}
