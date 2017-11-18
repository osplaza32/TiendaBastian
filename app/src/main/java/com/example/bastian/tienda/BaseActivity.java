package com.example.bastian.tienda;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.example.bastian.tienda.http.HttpResponse;
import com.example.bastian.tienda.http.OnHttpResponse;

/**
 * Created by Bastian on 08-10-2017.
 */

public class BaseActivity extends FragmentActivity implements View.OnClickListener, OnHttpResponse {

    /**
     * Will be used to display a progress dialog
     */
    private Dialog progressDialog;

    /**
     * Method to show a progress dialog without message
     */
    public void showProgressDialog() {


        try {
            if (progressDialog == null || !progressDialog.isShowing()) {
                progressDialog = new Dialog(this);
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                //progressDialog.setContentView(R.layout.custom_progress_dialog);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        }catch (Exception e){
            if (progressDialog == null || !progressDialog.isShowing()) {
            }
        }

    }

    /**
     * Hide progress indicator
     */
    public void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onHttpResponse(HttpResponse response) {

    }

    public void showErrorMessage(String message)
    {
        //showInfoMessage(getResources().getString(R.string.error), message);
    }

    public void showInfoMessage(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Informacion")
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.accept),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });


        builder.create().show();
    }

    /**
     * Check if response has error.
     * @param response Response from server
     * @return true if has error, false otherwise.
     */
    public boolean responseHasError(String response){
        boolean result = false;

        return result;
    }
}
