package com.uth.uthapp.config;

public class Transacciones {
    // Nombre de la base de datos
    public static final String NameDatabase = "UTHAppDB";

    // Tablas de la base de datos
    public static final String TablaClientes = "clientes";

    // Campos de la tabla clientes
    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = "edad";
    public static final String correo = "correo";

    // Consultas DDL (Data Definition Language) para crear la tabla
    public static final String CreateTableClientes = "CREATE TABLE " + TablaClientes + " (" +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            nombres + " TEXT, " +
            apellidos + " TEXT, " +
            edad + " INTEGER, " +
            correo + " TEXT)";

    // Consultas DDL para eliminar la tabla
    public static final String DropTableClientes = "DROP TABLE IF EXISTS " + TablaClientes;
}
