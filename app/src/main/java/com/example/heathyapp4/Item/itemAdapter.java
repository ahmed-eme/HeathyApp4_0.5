package com.example.heathyapp4.Item;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
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
import com.example.heathyapp4.Favorite.OADfavorite;
import com.example.heathyapp4.Home.ItemViewClass;
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


public class itemAdapter extends ArrayAdapter<ItemViewClass> {


    ImageFilterView addFav;
    private Context c;
    private Activity mActivity;


    // constructor for our list view adapter.
    public itemAdapter(@NonNull Context context, ArrayList<ItemViewClass> list) {
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

        ItemViewClass dataModal = getItem(position);

        TextView nameTV = listitemView.findViewById(R.id.idTVtext);
        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);
        TextView typeTV = listitemView.findViewById(R.id.typeTxt);
        addFav = listitemView.findViewById(R.id.addFav);
        TextView priceText = listitemView.findViewById(R.id.priceText);

        nameTV.setText(dataModal.getName());
        typeTV.setText(dataModal.getType1());
        priceText.setText(String.valueOf(dataModal.getPrice()));


        Glide
                .with(getContext())
                .load(dataModal.getImgLink())
                .centerCrop()
                .placeholder(R.drawable.loading_icon)
                .into(courseIV);


            Glide.with(getContext()).load(dataModal.isFav()).into(addFav);

           // Picasso.get().load(R.drawable.ic_date).into(addFav);

       /* FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference favRef = database.getReference("Item").child(dataModal.getId()).child("Favorite").child(user.getUid());
        favRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists())
                {
                    System.out.println("no like");
                }
                else{
                    Picasso.get().load(R.drawable.loading_icon).into(addFav);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/



        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getContext(), "Item clicked is : " + dataModal.getId(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(c.getApplicationContext(), ItemDetails.class);
                intent.putExtra("id" , dataModal.getId());
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                c.getApplicationContext().startActivity(intent);
            }
        });

        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String id = dataModal.getId();
               addORdeleteFavorite(id);

            }
        });
        return listitemView;
    }
/*****************************************************************************************/



    public void addORdeleteFavorite(String id)
{
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    OADfavorite oADfavorite = new OADfavorite();
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    databaseReference = db.getReference("Item"); //.child(idToString).child("Favorite");
    /********************************Get value from the reference************************************/
    int idInt = Integer.parseInt(id);
Query query = databaseReference
            .orderByChild("id")
            .equalTo(idInt);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot :dataSnapshot.getChildren() )
                {
                    if(!snapshot.child("Favorite").exists())
                    {
                        oADfavorite.add(id);
                    }
                    else
                    {
                        if(snapshot.child("Favorite").child(user.getUid()).exists())
                        {
                            oADfavorite.delete(id);
                        }
                        else
                            oADfavorite.add(id);
                    }

                }

        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            throw databaseError.toException();
        }
    });
}
}
