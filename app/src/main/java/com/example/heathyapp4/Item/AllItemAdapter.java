package com.example.heathyapp4.Item;

import static android.content.ContentValues.TAG;

import static com.example.heathyapp4.R.drawable.icon_passwod;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.example.heathyapp4.Favorite.OADfavorite;
import com.example.heathyapp4.Favorite.favClass;
import com.example.heathyapp4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Objects;

public class AllItemAdapter extends ArrayAdapter<ItemOfAllObject> {

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    ImageFilterView addFav;


    // constructor for our list view adapter.
    public AllItemAdapter(@NonNull Context context, ArrayList<ItemOfAllObject> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.image_gv_item, parent, false);
        }

        ItemOfAllObject dataModal = getItem(position);

        TextView nameTV = listitemView.findViewById(R.id.idTVtext);
        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);
        TextView typeTV = listitemView.findViewById(R.id.typeTxt);
        addFav = listitemView.findViewById(R.id.addFav);
        TextView price = listitemView.findViewById(R.id.priceText);

        nameTV.setText(dataModal.getItemName());
        typeTV.setText(dataModal.getType1());
        price.setText(String.valueOf(dataModal.getPrice()));
       // Picasso.get().load(dataModal.getImageUrl()).into(courseIV);

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Item clicked is : " + dataModal.getItemName(), Toast.LENGTH_SHORT).show();
            }
        });

        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = dataModal.getItemId();
                //  getfavkey();
                addORdeleteFavorite(id);
                //   OADfavorite oADfavorite = new OADfavorite();
                // oADfavorite.add(id);


            }
        });
        return listitemView;
    }
    /*****************************************************************************************/


    public void addORdeleteFavorite(int id)
    {
        String idToString = String.valueOf(id);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
      //  OADfavorite oADfavorite = new OADfavorite(idToString);
        DatabaseReference databaseReference;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = db.getReference("User").child(user.getUid()).child("Favorite");
        /********************************Get value from the reference************************************/


        String test = databaseReference.getKey();
        ArrayList<NewItemClass> list = new ArrayList();
        Query query = databaseReference
                .orderByChild("id").equalTo(id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) {
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        System.out.println(child.getKey());
                        System.out.println(child.child("id").getValue());
                     //   oADfavorite.delete(child.getKey());
                    }
                }
                else{
                  //  oADfavorite.add(id);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
