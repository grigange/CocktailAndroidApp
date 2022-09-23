package com.example.cocktailandroidapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.example.cocktailandroidapp.R;

import java.util.zip.Inflater;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog dialog;

    LoadingDialog(Activity myActivity){
        activity= myActivity;
    }

    void StartLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(true);
        builder.setInverseBackgroundForced(false);
        dialog = builder.create();
        dialog.show();
    }

    void DismissDialog(){
        dialog.dismiss();
    }
}
