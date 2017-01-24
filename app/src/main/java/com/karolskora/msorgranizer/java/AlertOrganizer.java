package com.karolskora.msorgranizer.java;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by apple on 04.01.2017.
 */

public class AlertOrganizer {

    public static void showAlert(Context context, String title, String message){
        AlertDialog alertDialog = buildMessageDialog(context,title,message, null);

        alertDialog.show();
    }

    public static void showAlert(Context context, String title, String message, DialogInterface.OnClickListener listener){
        AlertDialog alertDialog = buildMessageDialog(context,title,message, listener);

        alertDialog.show();
    }

    public static void showCancelableAlert(Context context, String title, String message, DialogInterface.OnClickListener listener){
        AlertDialog alertDialog = buildCancelableMessageDialog(context,title,message, listener);

        alertDialog.show();
    }

    private static AlertDialog buildMessageDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        if (listener == null) {
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                }
            };
        }
        builder.setPositiveButton("Ok", listener);

        return builder.create();
    }

    private static AlertDialog buildCancelableMessageDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        if (listener == null) {
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                }
            };
        }
        builder.setPositiveButton("Ok", listener);
        builder.setNegativeButton("powrót", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
