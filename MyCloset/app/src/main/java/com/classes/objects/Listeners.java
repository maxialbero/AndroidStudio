package com.classes.objects;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface Listeners {
    public void onStart();
    public void onSuccess(DataSnapshot data);
    public void onFailed(DatabaseError databaseError);
}
