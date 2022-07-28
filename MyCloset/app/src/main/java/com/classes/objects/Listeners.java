package com.classes.objects;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;

public interface Listeners {
    public void onStart();
    public void onSuccess(ArrayList<Object> list, Context context);
    public void onFailed(DatabaseError databaseError);
}
