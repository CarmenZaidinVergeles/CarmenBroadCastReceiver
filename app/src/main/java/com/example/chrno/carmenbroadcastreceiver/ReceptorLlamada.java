package com.example.chrno.carmenbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.telecom.Call;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.SQLOutput;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Chrno on 26/01/2016.
 */
public class ReceptorLlamada extends BroadcastReceiver {

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;


    @Override
    public void onReceive(final Context context, Intent intent) {
        final Context c = context;

        //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
        } else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING;
            }
            onCallStateChanged(context, state, number);
        }
    }

    //Derived classes should override these to respond to specific events of interest
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
    }

    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
    }

    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) { //Entrante
        start = new Date();
        int dia = getDayOfTheWeek(start);
        Llamada call = new Llamada(number, dia);
        ctx.getContentResolver().insert(Contrato.TablaEntrantes.CONTENT_URI, call.getContentValuesPerdida());
        System.out.println("llamada entrante " + call);
    }

    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) { //Saliente
        start = new Date();
        int dia = getDayOfTheWeek(start);
        Llamada call = new Llamada(number, dia);
        ctx.getContentResolver().insert(Contrato.TablaSalientes.CONTENT_URI, call.getContentValuesSaliente());
        System.out.println("llamada saliente " + call);
    }


    protected void onMissedCall(Context ctx, String number, Date start) { //Perdida
        start = new Date();
        int dia = getDayOfTheWeek(start);
        Llamada call = new Llamada(number, dia);
        ctx.getContentResolver().insert(Contrato.TablaPerdidas.CONTENT_URI, call.getContentValuesEntrante());
        System.out.println("llamada perdida" + call);
    }

    //Deals with actual events

    //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
    //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
    public void onCallStateChanged(Context context, int state, String number) {
        if (lastState == state) {
            //No change, debounce extras
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                onIncomingCallStarted(context, number, callStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false;
                    callStartTime = new Date();
                    onOutgoingCallStarted(context, savedNumber, callStartTime);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    //Ring but no pickup-  a miss
                    onMissedCall(context, savedNumber, callStartTime);
                } else if (isIncoming) {
                    onIncomingCallEnded(context, savedNumber, callStartTime, new Date());
                } else {
                    onOutgoingCallEnded(context, savedNumber, callStartTime, new Date());
                }
                break;
        }
        lastState = state;
    }

    //CALCULAR DIA DE LA SEMANA
    public int getDayOfTheWeek(Date d) {
        //Los resultados van del 1 = Domingo, 2 = Lunes, 3 = Martes, 4 = Miercoles, 5 = Jueves, 6=Viernes, 7= Sabado
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        return i;
    }
}





