package com.example.heathyapp4.Item;

import android.content.Context;
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

import com.example.heathyapp4.Favorite.OADfavorite;
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

public class CapacityAdapter extends ArrayAdapter<CapacityClass> {

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    ImageFilterView addFav;


    // constructor for our list view adapter.
    public CapacityAdapter(@NonNull Context context, ArrayList<CapacityClass> list) {
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

        CapacityClass dataModal = getItem(position);
        CapacityClass dataModelcap = getItem(position);


        TextView priceTV = listitemView.findViewById(R.id.priceText);

        priceTV.setText("test");


        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Item clicked is : " + dataModal.getPrice(), Toast.LENGTH_SHORT).show();
            }
        });
        return listitemView;
    }
    /*****************************************************************************************/

    }

