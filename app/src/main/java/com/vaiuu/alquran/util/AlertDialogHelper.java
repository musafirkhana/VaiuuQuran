package com.vaiuu.alquran.util;

import android.content.Context;
import android.view.View;

/**
 * Created by Ali PC on 5/24/2015.
 */
public class AlertDialogHelper {
    public static void showAlert(final Context context, final String message) {
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
        mMaterialDialog.setTitle("MaterialDialog");
        mMaterialDialog.setMessage("Hello world!");
        mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();

            }
        });
        mMaterialDialog.setNegativeButton("CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.show();
        mMaterialDialog.setTitle("Alert !");
        mMaterialDialog.show();
        mMaterialDialog.setMessage(message);
    }

}
