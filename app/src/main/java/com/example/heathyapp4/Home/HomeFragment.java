package com.example.heathyapp4.Home;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.heathyapp4.Item.itemAdapter;
import com.example.heathyapp4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class HomeFragment extends Fragment {

    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    OffersAdapter offersAdapter;
    TextView [] dots;



    /*******************************************************/




    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private GridView getGrid;



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Item");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);



        mSLideViewPager = (ViewPager) view.findViewById(R.id.slideViewPagerOffers);
        mDotLayout = (LinearLayout) view.findViewById(R.id.indicator_layoutOffers);

        offersAdapter = new OffersAdapter(getActivity());

        mSLideViewPager.setAdapter(offersAdapter);

        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);




        /*************************************************************/


        getGrid = view.findViewById(R.id.idGVCourses);

        ImageView AntiboticView = view.findViewById(R.id.Antibiotic);
        ImageView AnalgesicView = view.findViewById(R.id.analgesic);
        ImageView DrugsView= view.findViewById(R.id.Drugs);
        ImageView EquationsView= view.findViewById(R.id.Equations);
        ImageView VitaminView = view.findViewById(R.id.vitamin);
        EditText searchText = view.findViewById(R.id.searchEt);

        /***********************************************************/

      //  searchText.setGravity(Gravity.CENTER_HORIZONTAL);

        ViewCompat.setNestedScrollingEnabled(getGrid, true);


        CategoriesClick(AntiboticView , AnalgesicView , DrugsView , EquationsView ,VitaminView);


       GetdataonRealTime();

        return view;
    }

    /**************************************Offers home page******************************/

    @SuppressLint("NewApi")
    public void setUpindicator(int position){

        dots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getActivity().getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);

        }

        dots[position].setTextColor(getResources().getColor(R.color.active,getActivity().getApplicationContext().getTheme()));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

            if (position > 0){



            }else {



            }
            if (position > 1)
            {

            }
            else
            {
            }



        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /***********************************************/


    private void CategoriesClick(ImageView AntiboticView ,  ImageView AnalgesicView ,ImageView DrugsView ,ImageView EquationsView , ImageView VitaminView) {

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

    private void GetdataonRealTime() {

        ArrayList<ItemViewClass> list = new ArrayList<>();
        itemAdapter adapter = new itemAdapter(getActivity(), list);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String id = null;
                String type1 = null;
                String name = null;
                double price = 0.0;
                String image = null;

                for (DataSnapshot snap : snapshot.getChildren()) {
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

                    getGrid.setAdapter(adapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

    }

}

