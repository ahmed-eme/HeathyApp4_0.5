package com.example.heathyapp4.Item;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CapacityDAO {
    private DatabaseReference databaseReference;

    public CapacityDAO(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Item");
    }
    public Task<Void> add(String id ,String mg, CapacityClass item)
    {
        return databaseReference.child(id).child("Capacity").child(mg).setValue(item);
    }
}
