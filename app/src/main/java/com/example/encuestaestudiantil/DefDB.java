package com.example.encuestaestudiantil;

public class DefDB {
    public static final String nameDb = "Universidad";
    public static final String tabla_est = "estudiante";
    public static final String col_codigo = "codigo";
    public static final String col_nombre = "nombre";
    public static final String col_programa = "programa";
    public static final String col_computador = "computador";
    public static final String col_smartphone = "smartphone";
    public static final String col_internet = "internet";

    public static final String CREATE_TABLA_ESTUDIANTE="CREATE TABLE estudiante(" +
            "codigo TEXT primary key," +
            "nombre TEXT not null,"+
            "programa TEXT not null," +
            "computador TEXT not null," +
            "smartphone TEXT not null," +
            "internet TEXT not null);";
}
