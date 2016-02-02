package com.example.chrno.carmenbroadcastreceiver;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Chrno on 26/01/2016.
 */
public class Proveedor extends ContentProvider {

    public static final UriMatcher convierteUri;
    public static final int ENTRANTES = 1;
    public static final int SALIENTES = 2;
    public static final int PERDIDAS = 3;


    private Ayudante abd;
    private static SQLiteDatabase db;

    static {
        convierteUri = new UriMatcher(UriMatcher.NO_MATCH);
        convierteUri.addURI(Contrato.TablaEntrantes.AUTHORITY, Contrato.TablaEntrantes.TABLA, ENTRANTES);
        convierteUri.addURI(Contrato.TablaSalientes.AUTHORITY, Contrato.TablaSalientes.TABLA, SALIENTES);
        convierteUri.addURI(Contrato.TablaPerdidas.AUTHORITY, Contrato.TablaPerdidas.TABLA, PERDIDAS);


    }


    @Override
    public boolean onCreate() {
        abd = new Ayudante((this.getContext()));
        db=abd.getReadableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        int match = convierteUri.match(uri);

        //long idActividad = ContentUris.parseId(uri);

        Cursor c;

        switch (match) {
            case ENTRANTES:
                c = db.query(Contrato.TablaEntrantes.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                break;


            case SALIENTES:
                c = db.query(Contrato.TablaSalientes.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case PERDIDAS:
                c = db.query(Contrato.TablaPerdidas.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = abd.getWritableDatabase();

        ContentValues contentValues;
        if (values == null) {
            throw new IllegalArgumentException("Tabla null ");
        }

        switch (convierteUri.match(uri)) {


            case ENTRANTES:
                long rowIdEntrante = db.insert(Contrato.TablaEntrantes.TABLA, null, values);
                if (rowIdEntrante > 0) {
                    //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                    Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaEntrantes.CONTENT_URI, rowIdEntrante);
                    getContext().getContentResolver().notifyChange(uri_actividad, null);
                    return uri_actividad;
                }
                throw new SQLException("Error al insertar fila en : " + uri);

            case SALIENTES:
                long rowIdSaliente = db.insert(Contrato.TablaSalientes.TABLA, null, values);
                if (rowIdSaliente > 0) {
                    //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                    Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaSalientes.CONTENT_URI, rowIdSaliente);
                    getContext().getContentResolver().notifyChange(uri_actividad, null);
                    return uri_actividad;
                }
                throw new SQLException("Error al insertar fila en : " + uri);

            case PERDIDAS:
                long rowIdPerdida = db.insert(Contrato.TablaPerdidas.TABLA, null, values);
                if (rowIdPerdida > 0) {
                    //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                    Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaPerdidas.CONTENT_URI, rowIdPerdida);
                    getContext().getContentResolver().notifyChange(uri_actividad, null);
                    return uri_actividad;
                }
                throw new SQLException("Error al insertar fila en : " + uri);
            }
        return null;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
