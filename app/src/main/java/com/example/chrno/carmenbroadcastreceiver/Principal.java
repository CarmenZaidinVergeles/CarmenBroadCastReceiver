package com.example.chrno.carmenbroadcastreceiver;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.SQLOutput;

public class Principal extends AppCompatActivity {
    private Cursor c, c2, c3;

    private int entrantesDomingo, entrantesLunes, entrantesMartes, entrantesMiercoles, entrantesJueves, entrantesViernes, entrantesSabado;
    private int salientesDomingo, salientesLunes, salientesMartes, salientesMiercoles, salientesJueves, salientesViernes, salientesSabado;
    private int perdidasDomingo, perdidasLunes, perdidasMartes, perdidasMiercoles, perdidasJueves, perdidasViernes, perdidasSabado;

    private int totalDomingo, totalLunes, totalMartes, totalMiercoles, totalJueves, totalViernes, totalSabado;

    private int[] contadorEntrantes;
    private int[] contadorSalientes;
    private int[] contadorPerdidas;

    private int[] perdidasSemana;
    private int[] entrantesSemana;
    private int[] salientesSemana;

    private WebView webView;
    private int[] a;

    private Button btEntrantes, btSalientes, btPerdidas, btTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btEntrantes = (Button) findViewById(R.id.btEntrantes);
        btSalientes = (Button) findViewById(R.id.btSalientes);
        btPerdidas = (Button) findViewById(R.id.btPerdidas);
        btTotal = (Button) findViewById(R.id.btTotal);
        init();

//
//        System.out.println("Tabla entrantes");
//        if (c != null) {
//            while (c.moveToNext()) {
//                System.out.println("--_: " + (c.getColumnName(0)) + ": " + c.getString(c.getColumnIndex(c.getColumnName(0))) +
//                        " - " + (c.getColumnName(1)) + ": " + c.getString(c.getColumnIndex(c.getColumnName(1))) +
//                        " - " + (c.getColumnName(2)) + ": " + c.getString(c.getColumnIndex(c.getColumnName(2))));
//            }
//        }
//
//        System.out.println("tabla salientes");
//        if (c2 != null) {
//            while (c2.moveToNext()) {
//                System.out.println("--_: " + (c2.getColumnName(0)) + ": " + c2.getString(c.getColumnIndex(c2.getColumnName(0))) +
//                        " - " + (c2.getColumnName(1)) + ": " + c2.getString(c2.getColumnIndex(c2.getColumnName(1))) +
//                        " - " + (c2.getColumnName(2)) + ": " + c2.getString(c2.getColumnIndex(c2.getColumnName(2))));
//            }
//        }
//
//        System.out.println("tabla perdidas");
//        if (c3 != null) {
//            while (c3.moveToNext()) {
//                System.out.println("--_: " + (c3.getColumnName(0)) + ": " + c3.getString(c.getColumnIndex(c3.getColumnName(0))) +
//                        " - " + (c3.getColumnName(1)) + ": " + c3.getString(c3.getColumnIndex(c3.getColumnName(1))) +
//                        " - " + (c3.getColumnName(2)) + ": " + c3.getString(c3.getColumnIndex(c3.getColumnName(2))));
//            }
//        }


    }

    public void init(){
        obtenerCursores();
        contarLlamadasPorTabla();

        llamadasTotalesPorDia();
        llamadasEntrantesPorDia();
        llamadasSalientesPorDia();
        llamadasPerdidasPorDia();

        //Array semanal
        perdidasSemana = new int[]{perdidasDomingo, perdidasLunes, perdidasMartes, perdidasMiercoles, perdidasJueves, perdidasViernes, perdidasSabado};
        entrantesSemana = new int[]{entrantesDomingo, entrantesLunes, entrantesMartes, entrantesMiercoles, entrantesJueves, entrantesViernes, entrantesSabado, entrantesDomingo};
        salientesSemana = new int[]{salientesDomingo, salientesLunes, salientesMartes, salientesMiercoles, salientesJueves, salientesViernes, salientesSabado};

        a=perdidasSemana;//Por defecto sacamos en pantalla en primer lugar las perdidas por semana

        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/canvas/pruebagraficos.html");
        webView.addJavascriptInterface(this, "InterfazAndroid");
    }

    //Metodo para obtener los cursores
    public void obtenerCursores() {
        //Obtener los cursores
        c = getContentResolver().query(Contrato.TablaEntrantes.CONTENT_URI, null, null, null, null);
        c2 = getContentResolver().query(Contrato.TablaSalientes.CONTENT_URI, null, null, null, null);
        c3 = getContentResolver().query(Contrato.TablaPerdidas.CONTENT_URI, null, null, null, null);
    }

    public void contarLlamadasPorTabla() {
        //Contar las llamadas de cada una de las tablas, para ello pasamos al método contadorLlamadas el cursor
        contadorEntrantes = contadorLlamadas(c); //Es un array que contiene el numero de llamadas entrantes realizadas a lo largo de la semana
        contadorSalientes = contadorLlamadas(c2); //Es un array que contiene el numero de llamadas salientes realizadas a lo largo de la semana
        contadorPerdidas = contadorLlamadas(c3); //Es un array que contiene el numero de llamadas Perdidas realizadas a lo largo de la semana

    }

    public void llamadasTotalesPorDia() {
        //LLAMADAS TOTALES POR DIA
        int[] totalPorDia = totalPorDia(contadorEntrantes, contadorSalientes, contadorPerdidas);
        totalDomingo = totalPorDia[0];//Llamadas totales(entrantes, salientes y perdidas) en DOMINGO
        totalLunes = totalPorDia[1];
        totalMartes = totalPorDia[2];
        totalMiercoles = totalPorDia[3];
        totalJueves = totalPorDia[4];
        totalViernes = totalPorDia[5];
        totalSabado = totalPorDia[6];
    }


    public void llamadasEntrantesPorDia() {
        //LLAMADAS ENTRANTES POR DÍA
        entrantesDomingo = contadorEntrantes[0];
        System.out.println("entrantesDomingo " + entrantesDomingo);
        entrantesLunes = contadorEntrantes[1];
        entrantesMartes = contadorEntrantes[2];
        entrantesMiercoles = contadorEntrantes[3];
        entrantesJueves = contadorEntrantes[4];
        entrantesViernes = contadorEntrantes[5];
        entrantesSabado = contadorEntrantes[6];
    }

    public void llamadasSalientesPorDia() {
        //LLAMADAS SALIENTES POR DIA
        salientesDomingo = contadorSalientes[0];
        System.out.println("salientesDomingo: " + salientesDomingo);
        salientesLunes = contadorSalientes[1];
        salientesMartes = contadorSalientes[2];
        salientesMiercoles = contadorSalientes[3];
        salientesJueves = contadorSalientes[4];
        salientesViernes = contadorSalientes[5];
        salientesSabado = contadorSalientes[6];
        System.out.println("salientesSabado " + salientesSabado);
    }

    public void llamadasPerdidasPorDia() {
        //LLAMADAS PERDIDAS POR DIA
        perdidasDomingo = contadorPerdidas[0];
        perdidasLunes = contadorPerdidas[1];
        perdidasMartes = contadorPerdidas[2];
        perdidasMiercoles = contadorPerdidas[3];
        perdidasJueves = contadorPerdidas[4];
        perdidasViernes = contadorPerdidas[5];
        perdidasSabado = contadorPerdidas[6];
    }


    //Metodo para contar las llamadas
    public int[] contadorLlamadas(Cursor c) {
        int contadorD = 0;
        int contadorL = 0;
        int contadorM = 0;
        int contadorMi = 0;
        int contadorJ = 0;
        int contadorV = 0;
        int contadorS = 0;


        while (c.moveToNext()) {
            int caso = c.getInt(c.getColumnIndex("fecha")); //Obtenemos los datos de la columnna fecha

            switch (caso) {
                case 1://Domingo
                    contadorD++;
                    break;

                case 2: //Lunes
                    contadorL++;
                    break;

                case 3: //Martes
                    contadorM++;
                    break;

                case 4: //Miercoles
                    contadorMi++;
                    break;

                case 5: //Jueves
                    contadorJ++;
                    break;

                case 6: //Viernes
                    contadorV++;
                    break;

                case 7: //Sabado
                    contadorS++;
                    break;

                default:
                    break;
            }
        }
        int[] result = {contadorD, contadorL, contadorM, contadorMi, contadorJ, contadorV, contadorS};
        return result;
    }

    //Llamadas totales por dia
    public int[] totalPorDia(int[] entrantes, int[] salientes, int[] perdidas) {
        totalDomingo = entrantes[0] + salientes[0] + perdidas[0];
        totalLunes = entrantes[1] + salientes[1] + perdidas[1];
        totalMartes = entrantes[2] + salientes[2] + perdidas[2];
        totalMiercoles = entrantes[3] + salientes[3] + perdidas[3];
        totalJueves = entrantes[4] + salientes[4] + perdidas[4];
        totalViernes = entrantes[5] + salientes[5] + perdidas[5];
        totalSabado = entrantes[6] + salientes[6] + perdidas[6];
        int[] resultadoTotal = {totalDomingo, totalLunes, totalMartes, totalMiercoles, totalJueves, totalViernes, totalSabado};
        return resultadoTotal;
    }

    //Boton Perdidas
    public void volcarPerdidas(View v) {
        a = perdidasSemana;
        System.out.println(a.toString());
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/canvas/pruebagraficos.html");
        webView.addJavascriptInterface(this, "InterfazAndroid");
    }

    //Boton entrantes
    public void volcarEntrantes(View v) {
        a = entrantesSemana;
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/canvas/pruebagraficos.html");
        webView.addJavascriptInterface(this, "InterfazAndroid");
    }

    //Boton Salientes
    public void volcarSalientes(View v) {
        a = salientesSemana;
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/canvas/pruebagraficos.html");
        webView.addJavascriptInterface(this, "InterfazAndroid");
    }

    //Boton total
    public void volcarTotal(View v){
        int[] total = totalPorDia(entrantesSemana, salientesSemana, perdidasSemana);
        a = total;
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/canvas/pruebagraficos.html");
        webView.addJavascriptInterface(this, "InterfazAndroid");
    }

    @JavascriptInterface
    public int enviarDia(int pos) {
        return a[pos];
    }
}
