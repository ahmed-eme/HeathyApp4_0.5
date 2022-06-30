package com.example.heathyapp4.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.heathyapp4.Item.NewItemClass;
import com.example.heathyapp4.Item.itemAdapter;
import com.example.heathyapp4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;



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
                throw error.toException();
            }
        });
    }

    private void GetdataonRealTime()
    {

        ArrayList<ItemViewClass> list = new ArrayList();
        itemAdapter adapter = new itemAdapter(getActivity(), list);

        /*************************************************/
        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String id = null;
                String type1 = null;
                String name = null;
                double price = 0.0;
                String image = null;

                for(DataSnapshot snap :snapshot.getChildren() ) {

                    if (snap.child("Favorite").child(user.getUid()).exists()) {
                        id = snap.getKey();
                        type1 = snap.child("type1").getValue(String.class);
                        name = snap.child("name").getValue(String.class);
                        for (DataSnapshot snap2 : snapshot.child(id).child("ImgLink").getChildren()) {
                            ArrayList<String> imagelist = new ArrayList<>();
                            imagelist.add(snap2.getValue(String.class));
                            image = imagelist.get(0);
                            break;
                        }
                        for (DataSnapshot snap3 : snapshot.child(id).child("Capacity").getChildren()) {
                            price = snap3.child("price").getValue(double.class);
                            break;
                        }
                        ItemViewClass item = new ItemViewClass(image, type1, name, price, id);
                        list.add(item);
                    } else
                    {
                        System.out.println("error");
                    }
                    getGridid.setAdapter(adapter);
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
        /*************************************************/

    }
}