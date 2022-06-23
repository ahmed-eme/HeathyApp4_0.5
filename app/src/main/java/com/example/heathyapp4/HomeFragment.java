package com.example.heathyapp4;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.heathyapp4.Item.AllItemAdapter;
import com.example.heathyapp4.Item.ItemClass;
import com.example.heathyapp4.Item.ItemOfAllObject;
import com.example.heathyapp4.Item.itemAdapter;
import com.example.heathyapp4.Item.NewItemClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private FirebaseFirestore db;




    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Item");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        db = FirebaseFirestore.getInstance();
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

    private int getitem(int i){

        return mSLideViewPager.getCurrentItem() + i;
    }

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


    private void GetdataonRealTime()
    {
        ArrayList<ItemClass> list = new ArrayList();
       // ArrayList<NewItemClass> AllItem = new ArrayList<>();

        itemAdapter adapter = new itemAdapter(getActivity(), list);
       // AllItemAdapter adapter2 = new AllItemAdapter(getActivity() ,AllItem );
       // ItemOfAllObject itemOfAllObject = new ItemOfAllObject();





        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
              for(DataSnapshot snapshot :dataSnapshot.getChildren())
              {

                 ItemClass item = snapshot.getValue(ItemClass.class);

                 list.add(item);
                System.out.println(list.indexOf(snapshot));
              }

                getGrid.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }





}

