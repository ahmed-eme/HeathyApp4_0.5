package com.example.heathyapp4.User;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOUser {

    private DatabaseReference databaseReference;
    UserInfoClass user_info = new UserInfoClass();

    public DAOUser(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("User");
    }

    public Task<Void> add(UserInfoClass user )
    {
        return databaseReference.child(user_info.uid).child("UserInfo").setValue(user);
    }

    public Task<Void>  update(HashMap<String, Object> hashMap)
    {
        return databaseReference.child(user_info.uid).child("UserInfo").updateChildren(hashMap);
    }


}


