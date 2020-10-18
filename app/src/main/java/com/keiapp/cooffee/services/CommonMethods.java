package com.keiapp.cooffee.services;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CommonMethods {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Context context;

    public CommonMethods(Context context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();
    }

    public String getUserEmail(){
        user = auth.getCurrentUser();
        if (user == null){
            return null;
        }else{
            return user.getEmail();
        }
    }

    public String getUserUID(){
        user = auth.getCurrentUser();
        if (user == null){
            return null;
        }else{
            return user.getUid();
        }
    }
}
