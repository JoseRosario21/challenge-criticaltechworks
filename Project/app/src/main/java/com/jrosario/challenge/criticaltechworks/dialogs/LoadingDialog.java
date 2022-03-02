package com.jrosario.challenge.criticaltechworks.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import com.jrosario.challenge.criticaltechworks.R;

public class LoadingDialog {
    private final Activity activity;
    private Dialog progressDialog;

    public LoadingDialog(Activity activity){
        this.activity = activity;
    }

    public void showDialog(){
        progressDialog = new Dialog(activity);
        View view = View.inflate(activity.getApplicationContext(), R.layout.progress_dialog, null);

        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(view);

        progressDialog.show();
    }

    public void hideDialog(){
        if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
    }
}
