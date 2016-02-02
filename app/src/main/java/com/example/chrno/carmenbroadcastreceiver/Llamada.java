package com.example.chrno.carmenbroadcastreceiver;

import android.content.ContentValues;

import java.util.Date;

/**
 * Created by Chrno on 27/01/2016.
 */
public class Llamada {
    private String numero;
    private int fecha;


    public Llamada(){
    }

    public Llamada(String numero, int fecha) {
        this.numero = numero;
        this.fecha = fecha;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Llamada{" +
                "fecha=" + fecha +
                ", numero='" + numero + '\'' +
                '}';
    }

    //Entrante
    public ContentValues getContentValuesEntrante(){
        ContentValues cv = new ContentValues();
        cv.put(Contrato.TablaEntrantes.NUMERO, this.numero);
        cv.put(Contrato.TablaEntrantes.FECHA, fecha);
        return cv;
    }
    //Saliente
    public ContentValues getContentValuesSaliente(){
        ContentValues cv = new ContentValues();
        cv.put(Contrato.TablaSalientes.NUMERO, this.numero);
        cv.put(Contrato.TablaSalientes.FECHA, fecha);
        return cv;
    }
    //Perdida
    public ContentValues getContentValuesPerdida(){
        ContentValues cv = new ContentValues();
        cv.put(Contrato.TablaSalientes.NUMERO, this.numero);
        cv.put(Contrato.TablaSalientes.FECHA, fecha);
        return cv;
    }

}
