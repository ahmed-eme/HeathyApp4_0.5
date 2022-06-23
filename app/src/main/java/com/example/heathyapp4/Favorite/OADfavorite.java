/*package com.example.heathyapp4.Favorite;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class OADfavorite {
    favClass favClass = new favClass();
    private DatabaseReference databaseReference;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public  OADfavorite(String id)
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Item")*//*.child(id).child("Favorite")*//*;
    }
    public Task<Void> add(int i , String key)
    {
        return databaseReference.child(key).push().child("id").setValue(i);
    }

    public Task<Void> update(String key , HashMap<String, Object> hashMap)
    {
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> delete(String key)
    {
        return databaseReference.child(key).removeValue();
    }

}*/

package com.example.heathyapp4.Favorite;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class OADfavorite {
    favClass favClass = new favClass();
    private DatabaseReference databaseReference;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public  OADfavorite()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Item");
    }
    public Task<Void> add(String key)
    {
        return databaseReference.child(key).child("Favorite").child(user.getUid()).setValue(user.getUid());
    }

    public Task<Void> update(String key , HashMap<String, Object> hashMap)
    {
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> delete(String key)
    {
        return databaseReference.child(key).child("Favorite").child(user.getUid()).removeValue();
    }

}




