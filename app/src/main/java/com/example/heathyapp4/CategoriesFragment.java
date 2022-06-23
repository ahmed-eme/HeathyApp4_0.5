package com.example.heathyapp4;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.heathyapp4.Item.ItemClass;
import com.example.heathyapp4.Item.NewItemClass;
import com.example.heathyapp4.Item.itemAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class CategoriesFragment extends Fragment {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Item");
    private GridView getGrid;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle= getActivity().getIntent().getExtras();
       //String value =bundle.getString("valueClick");




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        getGrid = view.findViewById(R.id.idGVCourses);

        Bundle bundle = this.getArguments();
        String value =bundle.getString("valueClick");

       TextView favText = view.findViewById(R.id.favText);
       favText.setText(value);
        // getidfromUserFav();

        GetdataonRealTime(value);

        return view;
    }

    private void GetdataonRealTime(String value)
    {

        ArrayList<ItemClass> list = new ArrayList();

        itemAdapter adapter = new itemAdapter(getActivity(), list);
        Query query = myRef
                .orderByChild("type1")
                .equalTo(value);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) {

                    list.clear();

                    for(DataSnapshot snapshot :dataSnapshot.getChildren() )
                    {
                        ItemClass item = snapshot.getValue(ItemClass.class);
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
        });


    }

}