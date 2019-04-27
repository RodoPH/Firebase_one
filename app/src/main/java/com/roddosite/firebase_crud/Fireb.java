package com.roddosite.firebase_crud;

import com.google.firebase.database.FirebaseDatabase;

public class Fireb extends android.app.Application {
    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
