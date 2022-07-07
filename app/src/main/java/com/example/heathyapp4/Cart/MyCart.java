package com.example.heathyapp4.Cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.heathyapp4.Home.ItemViewClass;
import com.example.heathyapp4.Item.itemAdapter;
import com.example.heathyapp4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyCart extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference cartRef = database.getReference("Cart");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private GridView getGrid;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        getGrid = view.findViewById(R.id.gridViewCart);
        ViewCompat.setNestedScrollingEnabled(getGrid, true);

        GetdataonRealTime();

        return view;
    }

    private void GetdataonRealTime() {

        ArrayList<CartClass> list = new ArrayList<>();
        CartAdapter adapter = new CartAdapter(getActivity(), list);
        Query query = cartRef.orderByChild("userId").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                String userId = null;
                String type1 = null;
                String name = null;
                double price = 0.0;
                double discount = 0.0;
                String imgLink = null;
                String endDeal = null;
                int quantity = 0;
                String itemId = null;
                int mg = 0;


                for (DataSnapshot snap : snapshot.getChildren()) {
                    userId = snap.getKey();

                    type1 = snap.child("type1").getValue(String.class);
                    name = snap.child("name").getValue(String.class);
                    price = snap.child("price").getValue(double.class);
                    discount = snap.child("discount").getValue(double.class);
                    quantity = snap.child("quantity").getValue(int.class);
                    imgLink = snap.child("imgLink").getValue(String.class);
                    endDeal = snap.child("endDeal").getValue(String.class);
                    itemId = snap.child("itemId").getValue(String.class);
                    mg = snap.child("mg").getValue(int.class);

                    CartClass item = new CartClass(imgLink, type1, name, price, discount , user.getUid() , itemId , mg , endDeal , quantity);
                    list.add(item);

                }
                getGrid.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
}