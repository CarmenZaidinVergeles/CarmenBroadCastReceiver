package com.example.chrno.carmenbroadcastreceiver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chrno on 26/01/2016.
 */
public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="llamadas.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql="create table "+Contrato.TablaEntrantes.TABLA+ " ("+
                Contrato.TablaEntrantes._ID+ " integer primary key, "+
                Contrato.TablaEntrantes.NUMERO+" integer, "+
                Contrato.TablaEntrantes.FECHA+" integer)";

        db.execSQL(sql);


        String sql2;
        sql2="create table "+Contrato.TablaSalientes.TABLA+ " ("+
                Contrato.TablaSalientes._ID+ " integer primary key, "+
                Contrato.TablaSalientes.NUMERO+" integer, "+
                Contrato.TablaSalientes.FECHA+" integer)";

        db.execSQL(sql2);

        String sql3;
        sql3="create table "+Contrato.TablaPerdidas.TABLA+ " ("+
                Contrato.TablaPerdidas._ID+ " integer primary key, "+
                Contrato.TablaPerdidas.NUMERO+" integer, "+
                Contrato.TablaPerdidas.FECHA+" integer)";

        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
