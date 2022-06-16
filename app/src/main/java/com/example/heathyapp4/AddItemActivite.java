package com.example.heathyapp4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heathyapp4.Item.CapacityClass;
import com.example.heathyapp4.Item.CapacityDAO;
import com.example.heathyapp4.Item.ItemClass;
import com.example.heathyapp4.Item.ItemDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class AddItemActivite extends AppCompatActivity {
    /************************** Variables for add images ******************/
    private static final int PICK_IMG = 1;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private int uploads = 0;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    TextView textView;
    Button selectImages,submit;

    /******************************Spinner variables *********************************/
    Spinner type1;
    Spinner type2;
    /*******************************EditText variables*********************************/
    private EditText itemPrice;
    private  EditText itemName;
    private EditText itemDiscount;
    private EditText quantity;
    private EditText capacity;
    private EditText medInfo;
    private EditText scanFet;
    /*********************************Variables ready to upload*********************************/
    String itemNameUp;
    double itemPriceUp;
    double itemDiscountUp;
    int quantityUp;
    int capacityUp;
    String medInfoUp;
    String scanFetUp;
    String type1up;
    String type2up;
    String idToString;
    int id;
    String urlForClass;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_activite);

        /********call function*********/

        Spinner();
        getsize();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Item");
        textView = findViewById(R.id.textProgress);
        selectImages = findViewById(R.id.addImages);
        submit = findViewById(R.id.submit);

        selectImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getsize();
                goToGallery();
                Log.d("TAG", "selectImages.onclick" );
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFun();
                addClasstoDatabase();
                Log.d("TAG", "onClick: Submit ");
            }
        });
    }

    /*************************give value to variable ***********************************/
    private void giveValueToVariable()
    {
        Log.d("TAG", "giveValueToVariable: ");
        itemName = findViewById(R.id.ItemName);
        itemPrice = findViewById(R.id.ItemPrice);
        itemDiscount = findViewById(R.id.ItemDiscount);
        quantity = findViewById(R.id.Quantity);
        capacity = findViewById(R.id.CapacityMG);
        medInfo = findViewById(R.id.medicalInfo);
        scanFet = findViewById(R.id.scanned);

        itemNameUp = itemName.getText().toString();
        itemPriceUp = Double.parseDouble(itemPrice.getText().toString());
        itemDiscountUp = Double.parseDouble(itemDiscount.getText().toString());
        quantityUp = Integer.parseInt(quantity.getText().toString());
        capacityUp = Integer.parseInt(capacity.getText().toString());
        medInfoUp = medInfo.getText().toString();
        scanFetUp = scanFet.getText().toString();
        type1up= type1.getSelectedItem().toString();
        type2up= type2.getSelectedItem().toString();
    }
    /**********************************end of give value to variable*****************************************/

    /*************************************** Add images Functions *************************************************/
    private void goToGallery()
    {
        Log.e("TAG", "goToGallery: " );
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMG);
    }

    private void submitFun()
    {
        Log.d("TAG", "submitFun: start ");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading ..........");
        textView.setText("Please Wait ... If Uploading takes Too much time please the button again ");
        progressDialog.show();

        StorageReference ImageFolder =  FirebaseStorage.getInstance().getReference().child("ItemImage");
        for (uploads=0; uploads < ImageList.size(); uploads++) {
            Uri Image  = ImageList.get(uploads);
            final StorageReference imagename = ImageFolder.child(Image.getLastPathSegment());
            Log.d("TAG", "submitFun:  inside for loop");

            imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d("TAG", "onSuccess: inside on success");
                            String url = String.valueOf(uri);
                            // ItemClass item = new ItemClass();
                            //  item.setImageUrl(url);
                            SendLink(url);

                        }
                    });

                }
            });
        }
    }

    /********************************************************************/
    private void addClasstoDatabase()
    {
        Log.d("TAG", "addClasstoDatabase: ");

        itemName = findViewById(R.id.ItemName);
        itemPrice = findViewById(R.id.ItemPrice);
        itemDiscount = findViewById(R.id.ItemDiscount);
        quantity = findViewById(R.id.Quantity);
        capacity = findViewById(R.id.CapacityMG);
        medInfo = findViewById(R.id.medicalInfo);
        scanFet = findViewById(R.id.scanned);

        itemNameUp = itemName.getText().toString();
        itemPriceUp = Double.parseDouble(itemPrice.getText().toString());
        itemDiscountUp = Double.parseDouble(itemDiscount.getText().toString());
        quantityUp = Integer.parseInt(quantity.getText().toString());
        capacityUp = Integer.parseInt(capacity.getText().toString());
        medInfoUp = medInfo.getText().toString();
        scanFetUp = scanFet.getText().toString();
        type1up= type1.getSelectedItem().toString();
        type2up= type2.getSelectedItem().toString();

        //  String urlClass = String.valueOf(ImageList.get(1));
        ItemClass itemClass = new ItemClass(id , itemNameUp , urlForClass , type1up , type2up , medInfoUp , scanFetUp , "uidforUser");
        ItemDAO itemDAO = new ItemDAO();
        itemDAO.add(idToString,itemClass);
        CapacityClass capacity = new CapacityClass(capacityUp , itemPriceUp , itemDiscountUp , quantityUp);
        CapacityDAO capacityAUD = new CapacityDAO();
        capacityAUD.add( idToString ,capacity);
    }


    // @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("TAG", "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    if(count < 7)
                    {
                        int CurrentImageSelect = 0;

                        while (CurrentImageSelect < count) {
                            Uri imageuri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                            ImageList.add(imageuri);
                            CurrentImageSelect = CurrentImageSelect + 1;
                        }
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("You Have Selected "+ ImageList.size() +" Pictures" );
                        //  selectImages.setVisibility(View.GONE);
                    }
                    else
                    {
                        Toast.makeText(this, "you cant upload more than 6 images", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        }

    }

    private void SendLink(String url) {
        Log.d("TAG", "SendLink: ");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("link", url);
        databaseReference.child(idToString).child("Images").push().setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                textView.setText("Image Uploaded Successfully");
                // submit.setVisibility(View.GONE);
                ImageList.clear();
            }
        });
    }

    /***************************************End of Add images Functions *************************************************/


    /******************************************Spinner Functions **************************************************/


    private void Spinner()
    {
        Log.d("TAG", "Spinner: ");
        type1 = findViewById(R.id.type1);
        type2 = findViewById(R.id.type2);

        String[] itemsType1 = new String[]{"Antibiotic", "Antipyretic and Analgesic", "GIT Drugs" , "Equations and Resources" , "Vitamin"};
        String[] itemsType2 = new String[]{"Skin Care", "Loosing Weight", "Covid-19 Safety"};

        ArrayAdapter<String> adapterType1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemsType1);
        type1.setAdapter(adapterType1);

        ArrayAdapter<String> adapterType2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemsType2);
        type2.setAdapter(adapterType2);

    }

    /****************************Get length from database*******************************/

    public void getsize()
    {
        Log.d("TAG", "get size from database");
        DatabaseReference databaseReference;
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Item");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long size= snapshot.getChildrenCount();
                id = (int) size + 1;
                idToString = String.valueOf(id);
                System.out.println(size + " size +++++++++++++++++++++++++++++++++");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         /*   databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long size= dataSnapshot.getChildrenCount();
                    id = (int) size + 1;
                    idToString = String.valueOf(id);
                    System.out.println(size + " size +++++++++++++++++++++++++++++++++");
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });*/

    }
}
