package com.example.heathyapp4.Item;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemDAO {
    private DatabaseReference databaseReference;

    public ItemDAO(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Item");
    }

    public Task<Void> add(String id , ItemClass item )
    {
        return databaseReference.child(id).setValue(item);
    }
}


