package com.example.heathyapp4.Item;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.bumptech.glide.Glide;
import com.example.heathyapp4.AddItem.AddNewItem;
import com.example.heathyapp4.Favorite.OADfavorite;
import com.example.heathyapp4.HomeActivity;
import com.example.heathyapp4.MainActivity;
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


public class itemAdapter extends ArrayAdapter<ItemClass> {


    ImageFilterView addFav;
    private Context c;
    private Activity mActivity;


    // constructor for our list view adapter.
    public itemAdapter(@NonNull Context context, ArrayList<ItemClass> list) {
        super(context, 0, list);
        this.c = context;
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

        ItemClass dataModal = getItem(position);

        TextView nameTV = listitemView.findViewById(R.id.idTVtext);
        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);
        TextView typeTV = listitemView.findViewById(R.id.typeTxt);
        addFav = listitemView.findViewById(R.id.addFav);

        nameTV.setText(dataModal.getItemName());
        typeTV.setText(dataModal.getType1());
     //   Picasso.get().load(dataModal.getImageUrl()).into(courseIV);
        Glide
                .with(getContext())
                .load(dataModal.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.loading_icon)
                .into(courseIV);

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Item clicked is : " + dataModal.getItemId(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(c.getApplicationContext(), ItemDetails.class);
                intent.putExtra("id" , dataModal.getItemId());
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                c.getApplicationContext().startActivity(intent);
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
    OADfavorite oADfavorite = new OADfavorite();
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    databaseReference = db.getReference("Item"); //.child(idToString).child("Favorite");
    /********************************Get value from the reference************************************/
Query query = databaseReference;
    String idStr = String.valueOf(id);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot :dataSnapshot.getChildren() )
                {
                    if(snapshot.child("Favorite").exists())
                    {
                        if(snapshot.child("Favorite").child(user.getUid()).exists())
                        {
                            oADfavorite.delete(idStr);
                        }
                        else
                            oADfavorite.add(idStr);
                    }
                    else
                        oADfavorite.add(idStr);
                }

        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

    /**********************************************************************/
/*
    String test = databaseReference.getKey();
    ArrayList<NewItemClass> list = new ArrayList();
    Query query = databaseReference
            .orderByChild("id").equalTo(id);
    String idStr = String.valueOf(id);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (!dataSnapshot.exists())
            {
                oADfavorite.add(idStr);
            }
            else
            {
                oADfavorite.delete(idStr);
            }

            */
/*for (DataSnapshot child: dataSnapshot.getChildren()) {

                 if(dataSnapshot.getChildrenCount()<0) {
                    System.out.println(child.getKey());
                    System.out.println(child.child("id").getValue());

                    oADfavorite.add(child.getKey());
                }
                else{
                    oADfavorite.delete();
                }

            }*//*


        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
*/

}

}
