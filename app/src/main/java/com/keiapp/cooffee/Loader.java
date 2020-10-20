package com.keiapp.cooffee;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class Loader {

        private AlertDialog alertDialog;
        private TextView titleTxt;
        private Activity activity;

        public Loader(Activity activity) {
            this.activity =activity;
        }

        public void StartDialog(String title) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            LayoutInflater inflater = activity.getLayoutInflater();
            View view = inflater.inflate(R.layout.loader, null);
            titleTxt = view.findViewById(R.id.title);
            titleTxt.setText(title);

            builder.setView(view);
            builder.setCancelable(false);
            alertDialog = builder.create();
            alertDialog.show();
        }

        public void changeTitle(String title){
            titleTxt.setText(title);
        }
        public void DismissDialog() {
            alertDialog.dismiss();
        }
}
