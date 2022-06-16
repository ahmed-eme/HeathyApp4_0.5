package com.example.heathyapp4.Favorite;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class favRef {
    public void getFavoriteRef()
    {
         DatabaseReference databaseReference;
         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("User").child(user.getUid()).child("Favorite");
    }
}
