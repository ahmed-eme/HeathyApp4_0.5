package com.example.heathyapp4.Item;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ItemDAO {
    private DatabaseReference databaseReference;

    public ItemDAO(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Item");
    }

    public Task<Void> add(String id , NewItemClass item )
    {
        return databaseReference.child(id).setValue(item);
    }

    public Task<Void>  update(String id , HashMap<String, Object> hashMap)
    {
        return databaseReference.child(id).updateChildren(hashMap);
    }
}


