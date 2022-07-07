package com.example.heathyapp4.Item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
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
    private TextView itemNameTitle , itemTypeTitle;
    private View scanContainer;
    private TextView scanBtn;
    private TextView priceText , discountTxt , saveTxt , percentTxt;
    private TextView madinfo , scanLeafelt;
    private TextView daysTxt , hoursTxt , minTxt , secTxt;

    private RecyclerView courseRV;
    private RecyclerView  capacityRV;


    private DatabaseReference db;
    private  ImageView mainImage;
    private Button addToCart , buyNow;
    private Spinner qtySpinner;
// var upload to cart
    private String uploadImageToCart;
    private String type1upload;
    private String nameupload;

    String id;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference itemRef = database.getReference("Item");
    DatabaseReference cartRef = database.getReference("Cart");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        initUI();

        courseRV.setHasFixedSize(true);
        capacityRV.setHasFixedSize(true);

        courseRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        capacityRV.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));

        Bundle bundle=getIntent().getExtras();
         id =bundle.getString("id");

        loadrecyclerImage(id);
        giveFirstValue(id);
        countDownStart();


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

    final CapacityClass[] cap = {new CapacityClass()};
    ArrayList firstListQty = new ArrayList<>();

    private void giveFirstValue(String id) {



        int idInt = Integer.parseInt(id);
        Query query = itemRef
                .orderByChild("id")
                .equalTo(idInt);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap2 : snapshot.child(id).child("Capacity").getChildren()) {

                   cap[0] = snap2.getValue(CapacityClass.class);

                    break;
                }
                finaldis = (cap[0].getPrice() / 100.0f) * cap[0].getDiscount();
                precentDis = cap[0].getPrice() - finaldis;

                priceText.setText(String.valueOf("SAR "+ cap[0].getPrice() ));
                priceText.setPaintFlags(priceText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                discountTxt.setText(String.valueOf("SAR "+String.format("%.2f", precentDis)));
                saveTxt.setText(String.valueOf("SAR "+String.format("%.2f", finaldis)));
                percentTxt.setText(String.valueOf("(" +String.format("%.1f", cap[0].getDiscount()) + "%"+")"));
                endDeal = cap[0].getEndDeal();

                for (int i = 0; i <= cap[0].getQuantity(); i++) {
                    firstListQty.add(i);
                }

                ArrayAdapter adapterQty1 = new ArrayAdapter(ItemDetails.this, android.R.layout.simple_list_item_1, firstListQty);
                qtySpinner.setAdapter(adapterQty1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    private void initUI() {
        mViewGroup = findViewById(R.id.viewsContainer);
        medicalBtn = findViewById(R.id.button);
        scanContainer = findViewById(R.id.scanContainer);
        scanBtn = findViewById(R.id.scanBtn);
        mainImage = findViewById(R.id.mainImage);
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
        daysTxt = findViewById(R.id.daysTxt);
        hoursTxt = findViewById(R.id.hoursTxt);
        minTxt = findViewById(R.id.minsTxt);
        secTxt = findViewById(R.id.secTxt);
        itemNameTitle = findViewById(R.id.itemNameTitle);
        itemTypeTitle = findViewById(R.id.itemTypeTitle);
        db = FirebaseDatabase.getInstance().getReference();
    }

    /************************************Add to cart*************************************/


    private void addToCartFun(String id) {

        if (getmg == 0)
        {
            addToCartValue(cap[0].getMg() , cap[0].getQuantity() , cap[0].getPrice() , cap[0].getDiscount());
        }
        else {
            addToCartValue(getmg , getQty , getprice , getdis);
        }
    }

    private void addToCartValue(int mg , int Qty , double price , double dis)
    {
        String qtySpinnerUp= qtySpinner.getSelectedItem().toString();
        String idStr = String.valueOf(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
        String mgStr = String.valueOf(mg);
        int qtySpinInt  = Integer.valueOf(qtySpinnerUp);
        if(qtySpinInt >= 5)
        {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            int newQty = Qty - qtySpinInt;
            HashMap<String, Object> updateHash = new HashMap<String, Object>();
            updateHash.put("quantity", newQty);

            HashMap<String, Object> hashMap = new HashMap<String, Object>();
           // String imgLink, String type1, String name, double price, double discount, String id, String endDeal, int quantity
            // userid , itemid , name , mg;
            hashMap.put("price" , price);
            hashMap.put("discount" , dis);
            hashMap.put("quantity" , qtySpinInt);
            hashMap.put("mg" , mg);
            hashMap.put("endDeal" , endDeal);
            hashMap.put("userId" , user.getUid());
            hashMap.put("itemId" , id);
            hashMap.put("type1" , type1upload);
            hashMap.put("imgLink" , uploadImageToCart);
            hashMap.put("name" , nameupload);

            cartRef.push().updateChildren(hashMap);


            itemRef.child(idStr).child("Capacity").child(mgStr).updateChildren(updateHash);
            Toast.makeText(ItemDetails.this, "Add to cart Successful", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(ItemDetails.this, "The minimum order is 5", Toast.LENGTH_SHORT).show();
        }
    }

    /************************************end of Add to cart*************************************/




    /*****************************Images horizontal *********************************************************/



    private void loadrecyclerImage(String id) {


        ArrayList<String> imgList = new ArrayList<>();
        ArrayList<CapacityClass> capList = new ArrayList();
        AdapterImages imgAdapter = new AdapterImages(imgList, this);
        CapacityAdapter capAdapter = new CapacityAdapter(capList, this );
        int idInt = Integer.parseInt(id);
        Query query = itemRef
                .orderByChild("id")
                .equalTo(idInt);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

             imgList.clear();
                for (DataSnapshot snap2 : snapshot.child(id).child("ImgLink").getChildren()) {
                    imgList.add(snap2.getValue(String.class));

                    String imageMain = imgList.get(0);
                    uploadImageToCart = imageMain;
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

                        type1upload = snap4.child("type1").getValue(String.class);
                        nameupload = snap4.child("name").getValue(String.class);
                        itemNameTitle.setText(nameupload);
                        itemTypeTitle.setText(type1upload);

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
    private String endDeal;
  //  private String  EVENT_DATE_TIME = "2022-8-2 13:00:00";

    @Override
    public void ItemInfoLisner(Intent intent) {
          getprice = intent.getDoubleExtra("Price" , 0.0);
          getdis = intent.getDoubleExtra("discount" , 0.0);
          getQty = intent.getIntExtra("Qty" , 0);
          getmg = intent.getIntExtra("mg" , 0);
          endDeal = intent.getStringExtra("endDeal");
          System.out.println(getQty);
        ArrayList listQty = new ArrayList<>();

        for (int i = 0; i <= getQty; i++) {
            listQty.add(i);
        }


        ArrayAdapter adapterQty1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listQty);
        qtySpinner.setAdapter(adapterQty1);

         finaldis = (getprice / 100.0f) * getdis;

         precentDis = getprice - finaldis;

         priceText.setText(String.valueOf("SAR "+getprice ));
         priceText.setPaintFlags(priceText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
         discountTxt.setText(String.valueOf("SAR "+precentDis));
         saveTxt.setText(String.valueOf("SAR "+finaldis));
         percentTxt.setText(String.valueOf("(" +getdis + "%"+")"));

       // countDownStart();



    }
    private Handler handler = new Handler();
    private Runnable runnable;
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date event_date = dateFormat.parse(endDeal);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();

                        long Days = diff /( 24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;

                        daysTxt.setText(String.format("%02d", Days) + "d ");
                        hoursTxt.setText(String.format("%02d", Hours) + "h ");
                        minTxt.setText(String.format("%02d", Minutes) + "m ");
                        secTxt.setText(String.format("%02d", Seconds) + "s ");
                    } else {

                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
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