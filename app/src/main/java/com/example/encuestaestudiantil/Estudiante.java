package com.example.encuestaestudiantil;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Estudiante implements Parcelable, Serializable {
        private String codigo;
        private String nombre;
        private String progama;
        private String computador;
        private String smartphone;
        private String internet;

    public Estudiante() {
    }

    public Estudiante(String codigo, String nombre, String progama, String computador, String smartphone, String internet) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.progama = progama;
        this.computador = computador;
        this.smartphone = smartphone;
        this.internet = internet;
    }

    protected Estudiante(Parcel in) {
        codigo = in.readString();
        nombre =  in.readString();
        progama = in.readString();
        computador = in.readString();
        smartphone = in.readString();
        internet = in.readString();
    }

    public static final Creator<Estudiante> CREATOR = new Creator<Estudiante>() {
        @Override
        public Estudiante createFromParcel(Parcel in) {
            return new Estudiante(in);
        }

        @Override
        public Estudiante[] newArray(int size) {
            return new Estudiante[size];
        }
    };

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProgama() {
        return progama;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setProgama(String progama) {
        this.progama = progama;
    }

    public String getComputador() {
        return computador;
    }

    public void setComputador(String computador) {
        this.computador = computador;
    }

    public String getSmartphone() {
        return smartphone;
    }

    public void setSmartphone(String smartphone) {
        this.smartphone = smartphone;
    }

    public String getInternet() {
        return internet;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", progama='" + progama + '\'' +
                ", computador='" + computador + '\'' +
                ", smartphone='" + smartphone + '\'' +
                ", internet='" + internet + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codigo);
        dest.writeString(nombre);
        dest.writeString(progama);
        dest.writeString(computador);
        dest.writeString(smartphone);
        dest.writeString(internet);
    }
}
