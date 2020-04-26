package com.example.encuestaestudiantil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaEstudiantesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Estudiante> ListItems;

    public ListaEstudiantesAdapter(Context context, ArrayList<Estudiante> listItems) {
        this.context = context;
        ListItems = listItems;
    }


    @Override
    public int getCount() {
        return ListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return ListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Estudiante Item=(Estudiante) getItem(position);

        convertView= LayoutInflater.from(context).inflate(R.layout.item_list,null);
        TextView codigolis=convertView.findViewById(R.id.textCodigo);
        TextView nombrelis=convertView.findViewById(R.id.txtNombre);
        TextView progrmalis=convertView.findViewById(R.id.txtPrograma);
        TextView computalis=convertView.findViewById(R.id.txtComputador);
        TextView smartlis=convertView.findViewById(R.id.txtSmart);
        TextView internetlis=convertView.findViewById(R.id.txtInternet);


        codigolis.setText(Item.getCodigo());
        nombrelis.setText(Item.getNombre());
        progrmalis.setText(Item.getProgama());
        computalis.setText(Item.getComputador());
        smartlis.setText(Item.getSmartphone());
        internetlis.setText(Item.getInternet());
        return convertView;
    }
}
