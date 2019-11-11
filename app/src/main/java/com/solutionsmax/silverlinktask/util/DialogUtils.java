package com.solutionsmax.silverlinktask.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.solutionsmax.silverlinktask.R;

public class DialogUtils {
    public static void showDialog(Context context, String sMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.set_dialog);
        builder.setMessage(sMessage);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
