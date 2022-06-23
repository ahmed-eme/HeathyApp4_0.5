package com.example.heathyapp4.Item;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.heathyapp4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemDetails extends AppCompatActivity implements CapacityAdapter.ItemInfoLisner , AdapterImages.ImageLesner{
    private boolean medIsVisible = true;
    private boolean scanIsVisible = true;

    private View mViewGroup;
    private TextView medicalBtn;
    private View scanContainer;
    private TextView scanBtn;
    private TextView priceText , discountTxt , saveTxt , percentTxt;

    private RecyclerView courseRV;
    private RecyclerView  capacityRV;


    private DatabaseReference db;
    private  ImageView mainImage;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Item");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        mViewGroup = findViewById(R.id.viewsContainer);
        medicalBtn = findViewById(R.id.button);
        scanContainer = findViewById(R.id.scanContainer);
        scanBtn = findViewById(R.id.scanBtn);
        mainImage = findViewById(R.id.mainImage);



/*        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/heathyapp4.appspot.com/o/profileImage%2F1655790023281.jpg?alt=media&token=d4eb3867-e7b6-48d8-bf37-2bee1ada0ab9").
               fitCenter().into(mainImage);*/
       // Glide.with(this).load("http://via.placeholder.com/300.png").placeholder(R.drawable.baby).dontAnimate().into(mainImage);

        mViewGroup.setVisibility(View.GONE);
        scanContainer.setVisibility(View.GONE);

        courseRV = findViewById(R.id.idRVItems);
        capacityRV = findViewById(R.id.capacityRecyclerView);
        priceText = findViewById(R.id.thePriceText);
        discountTxt = findViewById(R.id.discountText);
        saveTxt = findViewById(R.id.saveTxt);
        percentTxt = findViewById(R.id.discountper);


        db = FirebaseDatabase.getInstance().getReference();


        courseRV.setHasFixedSize(true);
        capacityRV.setHasFixedSize(true);

        courseRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        capacityRV.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));



        Bundle bundle=getIntent().getExtras();
        int id =bundle.getInt("id");



        loadrecyclerImage(id);




        medicalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medClick();
            }
        });
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanClick();
            }
        });


    }


    /*****************************Images horizontal *********************************************************/
    private String image;

   /* private void getDetails(int id)
    {
        String idStr = String.valueOf(id);
        Bundle bundle=getIntent().getExtras();
        int mg =bundle.getInt("pos");
        String mgStr = String.valueOf(mg);
        ArrayList<CapacityClass> capList = new ArrayList();




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for(DataSnapshot snapshot :dataSnapshot.child(idStr).child("Capacity").child(mgStr).getChildren())
                {
                    int mg = snapshot.child("mg").getValue(int.class);
                }


               // capacityRV.setAdapter(capAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }*/



    private void loadrecyclerImage(int id) {

        Bundle bundle=getIntent().getExtras();
        int pos =bundle.getInt("pos");


        ArrayList<String> list = new ArrayList();
        ArrayList<CapacityClass> capList = new ArrayList();
        String idStr = String.valueOf(id);
        AdapterImages adapter = new AdapterImages(list, this);
        CapacityAdapter capAdapter = new CapacityAdapter(capList, this );


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot :dataSnapshot.getChildren())
                {
                        if(snapshot.getKey().equals(idStr))
                        {

                            image = snapshot.child("imageUrl").getValue(String.class);
                            Glide
                                    .with(getApplicationContext())
                                    .load(image)
                                    .centerCrop()
                                    .placeholder(R.drawable.loading_icon)
                                    .into(mainImage);

                        }

                }

                for(DataSnapshot snapshot :dataSnapshot.child(idStr).child("images").getChildren())
                {
                    String item = snapshot.getValue(String.class);
                    list.add(item);
                }

                courseRV.setAdapter(adapter);

                for(DataSnapshot snapshot :dataSnapshot.child(idStr).child("Capacity").getChildren())
                {

                    CapacityClass capItem = snapshot.getValue(CapacityClass.class);
                    capList.add(capItem);
                    System.out.println(capList);
                }

                capacityRV.setAdapter(capAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }


    /**********************************************************************************/

    /************************Start Medical info*********************/
    private void medClick(){
        if (medIsVisible) {
            mViewGroup.setVisibility(View.GONE);
            medicalBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_right, 0);
            // medicalInfo.setText("Show");
        } else {
            mViewGroup.setVisibility(View.VISIBLE);
            // medicalInfo.setText("Hide");
            medicalBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);

        }

        medIsVisible = !medIsVisible;
    }
    /************************End Medical info*********************/

    /************************Start Scanned Leaflet*********************/
    private void scanClick()
    {
        if (scanIsVisible) {
            scanContainer.setVisibility(View.GONE);
            scanBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_right, 0);
            // medicalInfo.setText("Show");
        } else {
            scanContainer.setVisibility(View.VISIBLE);
            // medicalInfo.setText("Hide");
            scanBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);

        }

        scanIsVisible = !scanIsVisible;
    }

    @Override
    public void ItemInfoLisner(Intent intent) {
         double getprice = intent.getDoubleExtra("Price" , 0.0);
         double getdis = intent.getDoubleExtra("discount" , 0.0);
        double finaldis = (getprice / 100.0f) * getdis;
         double precentDis = getprice - finaldis;
         priceText.setText(String.valueOf("SAR "+getprice ));
         priceText.setPaintFlags(priceText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
         discountTxt.setText(String.valueOf("SAR "+precentDis));
         saveTxt.setText(String.valueOf("SAR "+finaldis));
         percentTxt.setText(String.valueOf("(" +getdis + "%"+")"));

    }

    @Override
    public void ImageLesner(Intent intent) {
      String imageFromAdapter = intent.getStringExtra("image");
        Glide
                .with(this)
                .load(imageFromAdapter)
                .centerCrop()
                .placeholder(R.drawable.loading_icon)
                .into(mainImage);
    }


    /************************End Scanned Leaflet*********************/

}