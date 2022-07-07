package com.example.heathyapp4.DrawerPages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heathyapp4.Home.CategoriesFragment;
import com.example.heathyapp4.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class CategoriesDrawer extends Fragment {



    public CategoriesDrawer() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_drawer, container, false);

        /****************************for click Functions *****************/

        MaterialCardView AntiboticView = view.findViewById(R.id.Antibiotic);
        MaterialCardView AnalgesicView = view.findViewById(R.id.analgesic);
        MaterialCardView DrugsView= view.findViewById(R.id.Drugs);
        MaterialCardView EquationsView= view.findViewById(R.id.Equations);
        MaterialCardView VitaminView = view.findViewById(R.id.vitamin);

        CategoriesClick(AntiboticView , AnalgesicView , DrugsView , EquationsView ,VitaminView);

        /*****************************for size categories functions******************************/
        TextView antibioticTxtSize = view.findViewById(R.id.antibioticSize);
        TextView analgesicTxtSize = view.findViewById(R.id.analgesicTxtSize);
        TextView drugsTxtSize = view.findViewById(R.id.drugsTxtSize);
        TextView equationTxtSize = view.findViewById(R.id.equationTxtSize);
        TextView vitaminTxtSize = view.findViewById(R.id.vitaminTxtSize);
        CategoriesSize(antibioticTxtSize , "Antibiotic");
        CategoriesSize(analgesicTxtSize , "Antipyretic and Analgesic");
        CategoriesSize(drugsTxtSize , "GIT Drugs");
        CategoriesSize(equationTxtSize , "Equations and Resources");
        CategoriesSize(vitaminTxtSize , "Vitamin");


        return view;
    }

    private void CategoriesSize(TextView textView,String type1)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Item");

        Query antibioticRef = myRef.orderByChild("type1").equalTo(type1);
        antibioticRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long size= snapshot.getChildrenCount();
                textView.setText(String.valueOf(size) +" Items");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("error");
            }
        });

    }

    private void CategoriesClick(MaterialCardView AntiboticView , MaterialCardView AnalgesicView , MaterialCardView DrugsView , MaterialCardView EquationsView , MaterialCardView VitaminView) {

        AntiboticView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("valueClick","Antibiotic");
                Fragment newFragment = new CategoriesFragment();
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.commit();
            }
        });

        AnalgesicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("valueClick","Antipyretic and Analgesic");
                Fragment newFragment = new CategoriesFragment();
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.commit();

            }
        });

        DrugsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("valueClick","GIT Drugs");
                Fragment newFragment = new CategoriesFragment();
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.commit();
            }
        });

        EquationsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("valueClick","Equations and Resources");
                Fragment newFragment = new CategoriesFragment();
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.commit();
            }
        });

        VitaminView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("valueClick","Vitamin");
                Fragment newFragment = new CategoriesFragment();
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.commit();
            }
        });

    }
}