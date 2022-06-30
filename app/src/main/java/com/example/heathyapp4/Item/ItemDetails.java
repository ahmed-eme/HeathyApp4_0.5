package com.example.heathyapp4.Item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.heathyapp4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ItemDetails extends AppCompatActivity implements CapacityAdapter.ItemInfoLisner , AdapterImages.ImageLesner{
    private boolean medIsVisible = true;
    private boolean scanIsVisible = true;

    private View mViewGroup;
    private TextView medicalBtn;
    private View scanContainer;
    private TextView scanBtn;
    private TextView priceText , discountTxt , saveTxt , percentTxt;
    private TextView madinfo , scanLeafelt;

    private RecyclerView courseRV;
    private RecyclerView  capacityRV;


    private DatabaseReference db;
    private  ImageView mainImage;
    private Button addToCart , buyNow;
    private Spinner qtySpinner;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Item");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

        madinfo = findViewById(R.id.madInfoText);
        scanLeafelt = findViewById(R.id.scanLeafletText);

        addToCart = findViewById(R.id.addToCart);
        buyNow = findViewById(R.id.buyNow);

        qtySpinner = findViewById(R.id.qtySpinner);


        db = FirebaseDatabase.getInstance().getReference();


        courseRV.setHasFixedSize(true);
        capacityRV.setHasFixedSize(true);

        courseRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        capacityRV.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));



        Bundle bundle=getIntent().getExtras();
        String id =bundle.getString("id");



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

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addToCartFun(id);
            }
        });


    }

    private void addToCartFun(String id) {

        String qtySpinnerUp= qtySpinner.getSelectedItem().toString();
        String idStr = String.valueOf(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String mgStr = String.valueOf(getmg);
        int qtySpinInt  = Integer.valueOf(qtySpinnerUp);
        if(qtySpinInt >= 5)
        {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            int newQty = getQty - qtySpinInt;
            HashMap<String, Object> updateHash = new HashMap<String, Object>();
            updateHash.put("quantity", newQty);

            HashMap<String, Object> hashMap = new HashMap<String, Object>();

            hashMap.put("price" , getprice);
            hashMap.put("discount" , getdis);
            hashMap.put("quantity" , qtySpinnerUp);
            hashMap.put("mg" , getmg);
            hashMap.put("date" , currentDateandTime);
            hashMap.put("userId" , user.getUid());

            myRef.child(idStr).child("Cart").push().updateChildren(hashMap);


            myRef.child(idStr).child("Capacity").child(mgStr).updateChildren(updateHash);

           /* myRef.child(idStr).child("Cart").child(user.getUid()).child("Price").setValue(getprice);
            myRef.child(idStr).child("Cart").child(user.getUid()).child("discount").setValue(getdis);
            myRef.child(idStr).child("Cart").child(user.getUid()).child("quantity").setValue(qtySpinnerUp);
            myRef.child(idStr).child("Cart").child(user.getUid()).child("mg").setValue(getmg);
            myRef.child(idStr).child("Cart").child(user.getUid()).child("Date").setValue(currentDateandTime);*/
            Toast.makeText(ItemDetails.this, "Add to cart Successful", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(ItemDetails.this, "The minimum order is 5", Toast.LENGTH_SHORT).show();
        }

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



    private void loadrecyclerImage(String id) {

        Bundle bundle=getIntent().getExtras();
        int pos =bundle.getInt("pos");
        CapacityClass itemClass = new CapacityClass();
        ArrayList<String> imgList = new ArrayList<>();
      // ArrayList<String> list = new ArrayList();
        ArrayList<CapacityClass> capList = new ArrayList();
        AdapterImages imgAdapter = new AdapterImages(imgList, this);
        CapacityAdapter capAdapter = new CapacityAdapter(capList, this );
        int idInt = Integer.parseInt(id);
        Query query = myRef
                .orderByChild("id")
                .equalTo(idInt);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

             imgList.clear();
                for (DataSnapshot snap2 : snapshot.child(id).child("ImgLink").getChildren()) {
                    imgList.add(snap2.getValue(String.class));

                    String imageMain = imgList.get(0);
                    Glide
                            .with(getApplicationContext())
                            .load(imageMain)
                            .centerCrop()
                            .placeholder(R.drawable.loading_icon)
                            .into(mainImage);
                }

                courseRV.setAdapter(imgAdapter);

                capList.clear();
                for (DataSnapshot snap3 : snapshot.child(id).child("Capacity").getChildren()) {

                    CapacityClass capItem = snap3.getValue(CapacityClass.class);
                    capList.add(capItem);

                }

                capacityRV.setAdapter(capAdapter);

                    for (DataSnapshot snap4 : snapshot.getChildren()) {

                        System.out.println();
                        String medinfoData = snap4.child("medInfo").getValue(String.class);
                        String scanLeafeltData = snap4.child("scanLeaflet").getValue(String.class);

                        madinfo.setText(medinfoData);
                        scanLeafelt.setText(scanLeafeltData);

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
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
   private double getprice;
    private double getdis;
    private   double finaldis;
    private  double precentDis;
    private int getQty;
    private int getmg;
    @Override
    public void ItemInfoLisner(Intent intent) {
          getprice = intent.getDoubleExtra("Price" , 0.0);
          getdis = intent.getDoubleExtra("discount" , 0.0);
          getQty = intent.getIntExtra("Qty" , 0);
          getmg = intent.getIntExtra("mg" , 0);
          System.out.println(getQty);
        ArrayList listQty = new ArrayList<>();

        for (int i = 0; i <= getQty; i++) {
            listQty.add(i);
        }

        ArrayAdapter adapterType1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listQty);
        qtySpinner.setAdapter(adapterType1);

         finaldis = (getprice / 100.0f) * getdis;
         precentDis = getprice - finaldis;
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