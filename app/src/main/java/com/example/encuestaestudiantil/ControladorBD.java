package com.example.encuestaestudiantil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ControladorBD {
    private BaseDatos bd;
    public ControladorBD(Context context) {
        this.bd = new BaseDatos(context, DefDB.tabla_est, null, 1);
    }
    public long agregarRegistro(Estudiante estudiante) {
        try {
            SQLiteDatabase database = bd.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DefDB.col_codigo, estudiante.getCodigo());
            values.put(DefDB.col_nombre, estudiante.getNombre());
            values.put(DefDB.col_programa, estudiante.getProgama());
            values.put(DefDB.col_computador, estudiante.getComputador());
            values.put(DefDB.col_smartphone, estudiante.getSmartphone());
            values.put(DefDB.col_internet, estudiante.getInternet());
            return database.insert(DefDB.tabla_est, null, values);
        } catch (Exception e) {
            return -1L;
        }
    }

    public int actualizarRegistro(Estudiante estudiante) {
        try {
            SQLiteDatabase database = bd.getWritableDatabase();
            ContentValues valoresActualizados = new ContentValues();
            valoresActualizados.put(DefDB.col_nombre, estudiante.getNombre());
            valoresActualizados.put(DefDB.col_programa, estudiante.getProgama());
            valoresActualizados.put(DefDB.col_computador, estudiante.getComputador());
            valoresActualizados.put(DefDB.col_smartphone, estudiante.getSmartphone());
            valoresActualizados.put(DefDB.col_internet, estudiante.getInternet());

            String campoParaActualizar = DefDB.col_codigo + " = ?";
            String[] argumentosParaActualizar = {String.valueOf(estudiante.getCodigo())};

            return database.update(DefDB.tabla_est, valoresActualizados, campoParaActualizar, argumentosParaActualizar);
        } catch (Exception e) {
            return 0;
        }
    }

    public int borrarRegistro(Estudiante estudiante) {
        try {
            SQLiteDatabase database = bd.getWritableDatabase();
            String[] argumentos = {String.valueOf(estudiante.getCodigo())};
            return database.delete(DefDB.tabla_est, DefDB.col_codigo + " = ?", argumentos);
        } catch (Exception e) {
            return 0;
        }
    }

    public ArrayList<Estudiante> obtenerRegistros() {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        SQLiteDatabase database = bd.getReadableDatabase();

        String[] columnasConsultar = {DefDB.col_codigo, DefDB.col_nombre, DefDB.col_programa
                , DefDB.col_computador, DefDB.col_smartphone,DefDB.col_internet};

        Cursor cursor = database.query(DefDB.tabla_est, columnasConsultar
                , null, null, null, null, null);

        if (cursor == null) {
            return estudiantes;
        }

        if (!cursor.moveToFirst()) {
            return estudiantes;
        }

        do {

            Estudiante estudiante = new Estudiante(cursor.getString(0), cursor.getString(1)
                    , cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5));
            estudiantes.add(estudiante);
        } while (cursor.moveToNext());

        cursor.close();
        return estudiantes;
    }
    public boolean buscarEstudiante(String cod){
        String[] args = new String[] {cod};
        SQLiteDatabase database = bd.getReadableDatabase();
        Cursor c = database.query(DefDB.tabla_est, null, "codigo=?", args, null, null, null);
        if (c.getCount()>0) {
            database.close();
            return true;
        }
        else{
            database.close();
            return false;}

    }


}
