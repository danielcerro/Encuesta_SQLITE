package com.example.encuestaestudiantil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaEstudiantesAdapter extends BaseAdapter {
    private ArrayList<Estudiante> lista;
    private Context contexto;
    private int layoutRecurso;

    public ListaEstudiantesAdapter( Context contexto, int layoutRecurso, ArrayList<Estudiante> lista) {
        this.lista = lista;
        this.contexto = contexto;
        this.layoutRecurso = layoutRecurso;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(contexto).inflate(layoutRecurso, null);
        }

        Estudiante estudiante=lista.get(position);


        TextView codigolis=view.findViewById(R.id.textCodigo);
        TextView nombrelis=view.findViewById(R.id.txtNombre);
        TextView progrmalis=view.findViewById(R.id.txtPrograma);
        TextView computalis=view.findViewById(R.id.txtComputador);
        TextView smartlis=view.findViewById(R.id.txtSmart);
        TextView internetlis=view.findViewById(R.id.txtInternet);


        codigolis.setText(estudiante.getCodigo());
        nombrelis.setText(estudiante.getNombre());
        progrmalis.setText(estudiante.getProgama());
        computalis.setText(estudiante.getComputador());
        smartlis.setText(estudiante.getSmartphone());
        internetlis.setText(estudiante.getInternet());
        return view;
    }

    public void setFilter(ArrayList<Estudiante> listaestudiante){
        this.lista=new ArrayList<>();
        this.lista.addAll(listaestudiante);
        notifyDataSetChanged();
    }
}
