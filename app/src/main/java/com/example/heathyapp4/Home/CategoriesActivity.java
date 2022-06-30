package com.example.heathyapp4.Home;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.heathyapp4.Item.ItemClass;
import com.example.heathyapp4.Item.itemAdapter;
import com.example.heathyapp4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private  DatabaseReference newRef = database.getReference("Item");
    private GridView getGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);



        Bundle bundle=getIntent().getExtras();
        String value =bundle.getString("valueClick");

        GetdataonRealTime(value);
    }

    private void GetdataonRealTime(String value)
    {

      /*  ArrayList<NewItemClass> list = new ArrayList();

        itemAdapter adapter = new itemAdapter(CategoriesActivity.this, list);
        Query query = myRef
                .orderByChild("type")
                .equalTo(value);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) {

                    list.clear();
                    NewItemClass newItemClass = new NewItemClass();
                    for(DataSnapshot snapshot :dataSnapshot.getChildren() )
                    {
                        NewItemClass item = snapshot.getValue(NewItemClass.class);
                        list.add(item);
                        System.out.println(list.indexOf(snapshot));
                    }
                    getGrid.setAdapter(adapter);

                }
                else{
                    System.out.println("not found");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



      ArrayList<ItemClass> list = new ArrayList();

       // itemAdapter adapter = new itemAdapter(CategoriesActivity.this, list);
        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot :dataSnapshot.getChildren() )
                {
                    ItemClass item = snapshot.getValue(ItemClass.class);
                    list.add(item);
                    System.out.println(list.indexOf(snapshot));
                }
               // getGrid.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}