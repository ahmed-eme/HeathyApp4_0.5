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

import com.example.heathyapp4.Favorite.favClass;
import com.example.heathyapp4.Item.ItemClass;
import com.example.heathyapp4.Item.NewItemClass;
import com.example.heathyapp4.Item.itemAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Queue;


public class favFragment extends Fragment {


    ArrayList<String> listOfFavId = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    private GridView getGridid;
    private FirebaseFirestore db;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userFavRef = database.getReference("User").child(user.getUid()).child("Favorite");
    DatabaseReference itemRef = database.getReference("Item");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        getGridid = view.findViewById(R.id.idGVCourses);

        db = FirebaseFirestore.getInstance();

       // getidfromUserFav();

        GetdataonRealTime();

        return view;
    }

    public void getidfromUserFav()
    {
        userFavRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listOfFavId.clear();
                NewItemClass newItemClass = new NewItemClass();
                for(DataSnapshot snapshot :dataSnapshot.getChildren() )
                {

                    String item = snapshot.getKey();
                    listOfFavId.add(item);
                    System.out.println(listOfFavId);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void GetdataonRealTime()
    {


/*        Query userQuery = userFavRef.orderByChild("id");

        ArrayList<NewItemClass> list = new ArrayList();

        itemAdapter adapter = new itemAdapter(getActivity(), list);
        NewItemClass newItemClass = new NewItemClass();



        Query query = itemRef
                .orderByChild("id");
        //  .equalTo(userFavRef.child(""));

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
                    getGridid.setAdapter(adapter);

                }
                else{
                    System.out.println("not found");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        /*****************************************************************/

        ArrayList<ItemClass> list = new ArrayList();

        itemAdapter adapter = new itemAdapter(getActivity(), list);
        NewItemClass newItemClass = new NewItemClass();

            userFavRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot data :snapshot.getChildren() )
                    {
                       long getIdFavLong = data.child("id").getValue(long.class);
                       System.out.println(getIdFavLong);

                       itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               for(DataSnapshot data :snapshot.getChildren() ) {
                                   long getid = data.child("id").getValue(long.class);
                                   if (getid == getIdFavLong)
                                   {
                                       ItemClass item = data.getValue(ItemClass.class);
                                       list.add(item);
                                   }
                                   System.out.println(getid);
                               }
                               getGridid.setAdapter(adapter);
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        /**********************Two loop*************************/


      /*  userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    int id = newItemClass.getId();
                    Object id2 = child.getValue();
                    Query itemQuery = itemRef .orderByChild("id").equalTo(id);
                    System.out.println(child.getKey());
                    System.out.println(child.child("id").getValue());

                    *//*****************************************************//*
                    itemQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getChildrenCount()>0) {
                               // list.clear();
                                NewItemClass newItemClass = new NewItemClass();
                                for(DataSnapshot snapshot :dataSnapshot.getChildren() )
                                {
                                    NewItemClass item = snapshot.getValue(NewItemClass.class);
                                    list.add(item);
                                    System.out.println(list.indexOf(snapshot));
                                }
                                getGridid.setAdapter(adapter);
                            }
                            else{
                                System.out.println("not found");
                            }

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    *//*****************************************************//*
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/



        /*************************************************/



        /*************************************************************/

       /* ArrayList<NewItemClass> list = new ArrayList();

        itemAdapter adapter = new itemAdapter(getActivity(), list);

        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                NewItemClass newItemClass = new NewItemClass();
                favFragment favorite = new favFragment();
                for(DataSnapshot snapshot :dataSnapshot.getChildren() )
                {

                        NewItemClass item = snapshot.getValue(NewItemClass.class);
                        String idStr = String.valueOf(newItemClass.getId());

                        list.add(item);
                        System.out.println(list.indexOf(snapshot));

                }
                getGridid.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/


    }
}