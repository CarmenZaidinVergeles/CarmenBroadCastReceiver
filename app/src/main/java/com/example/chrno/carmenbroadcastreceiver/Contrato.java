package com.example.chrno.carmenbroadcastreceiver;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Chrno on 26/01/2016.
 */
public class Contrato {

    private Contrato(){

    }

    public static abstract class TablaEntrantes implements BaseColumns {
        public static final String TABLA = "entrantes";
        public static final String FECHA = "fecha";
        public static final String NUMERO= "numero";

        //La autoridad es la cadena q identifica a qué contentprovider se llama
        public final static String AUTHORITY = "com.example.chrno.carmenbroadcastreceiver";

        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }

    public static abstract class TablaSalientes implements BaseColumns {
        public static final String TABLA = "salientes";
        public static final String FECHA = "fecha";
        public static final String NUMERO= "numero";

        //La autoridad es la cadena q identifica a qué contentprovider se llama
        public final static String AUTHORITY = "com.example.chrno.carmenbroadcastreceiver";

        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }

    public static abstract class TablaPerdidas implements BaseColumns {
        public static final String TABLA = "perdidas";
        public static final String FECHA = "fecha";
        public static final String NUMERO= "numero";

        //La autoridad es la cadena q identifica a qué contentprovider se llama
        public final static String AUTHORITY = "com.example.chrno.carmenbroadcastreceiver";

        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }





}
