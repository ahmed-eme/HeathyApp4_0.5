package com.example.heathyapp4.AddItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heathyapp4.Item.ItemDAO;
import com.example.heathyapp4.R;
import com.example.heathyapp4.User.UserInfoClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class AddNewItem extends AppCompatActivity {
    /************************** Variables for add images ******************/
    private static final int PICK_IMG = 1;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private int uploads = 0;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    TextView textView;
    Button selectImages,submit ,addMainImage;

    /******************************Spinner variables *********************************/
    Spinner type1;
    Spinner type2;
    /*******************************EditText variables*********************************/
    private EditText itemPrice;
    private  EditText itemName;
    private EditText itemDiscount;
    private EditText medInfo;
    private EditText scanFet;
    /*********************************Variables ready to upload*********************************/
    String itemNameUp;
    double itemPriceUp;
    double itemDiscountUp;
    String medInfoUp;
    String scanFetUp;
    String type1up;
    String type2up;
    String idToString;
    int id;
    String urlForClass;
    UserInfoClass user = new UserInfoClass();


    FirebaseDatabase firebaseDatabase;
    private StorageReference reference = FirebaseStorage.getInstance().getReference("profileImage");

    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        Spinner();
        getsize();




        databaseReference = FirebaseDatabase.getInstance().getReference().child("Item");
        textView = findViewById(R.id.extextProgress);
        selectImages = findViewById(R.id.exaddImages);
        submit = findViewById(R.id.exsubmit);

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
               // addClasstoDatabase();
                uploadToFirebase(imageUri , id);
                Log.d("TAG", "onClick: Submit ");
            }
        });


    }

    /*************************************** Add images Functions *************************************************/
    private void goToGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent , 2);
    }


    /**************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
        }
    }

    /****************************************** upload image *************************************/


    private void uploadToFirebase(Uri uri , int id){

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        itemName = findViewById(R.id.exItemName);
                        itemPrice = findViewById(R.id.exItemPrice);
                        itemDiscount = findViewById(R.id.exItemDiscount);
                        medInfo = findViewById(R.id.exmedicalInfo);
                        scanFet = findViewById(R.id.exscanned);

                        itemNameUp = itemName.getText().toString();
                        itemPriceUp = Double.parseDouble(itemPrice.getText().toString());
                        itemDiscountUp = Double.parseDouble(itemDiscount.getText().toString());
                        medInfoUp = medInfo.getText().toString();
                        scanFetUp = scanFet.getText().toString();
                        type1up= type1.getSelectedItem().toString();
                        type2up= type2.getSelectedItem().toString();

                        ItemDAO itemDAO = new ItemDAO();

                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        hashMap.put("itemId" , id);
                        hashMap.put("itemName" , itemNameUp);
                          hashMap.put("imageUrl" , uri.toString());
                        hashMap.put("itemPrice" , itemPriceUp);
                        hashMap.put("itemDiscount" , itemDiscountUp);
                        hashMap.put("madInfo" , medInfoUp);
                        hashMap.put("scanLeaflet" , scanFetUp);
                        hashMap.put("type1" , type1up);
                        hashMap.put("type2" , type2up);
                        itemDAO.update(idToString,hashMap);
                        Toast.makeText(AddNewItem.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //   progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddNewItem.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    /********************************************************************/
    private void addClasstoDatabase()
    {

        itemName = findViewById(R.id.exItemName);
        itemPrice = findViewById(R.id.exItemPrice);
        itemDiscount = findViewById(R.id.exItemDiscount);
        medInfo = findViewById(R.id.exmedicalInfo);
        scanFet = findViewById(R.id.exscanned);

        itemNameUp = itemName.getText().toString();
        itemPriceUp = Double.parseDouble(itemPrice.getText().toString());
        itemDiscountUp = Double.parseDouble(itemDiscount.getText().toString());
        medInfoUp = medInfo.getText().toString();
        scanFetUp = scanFet.getText().toString();
        type1up= type1.getSelectedItem().toString();
        type2up= type2.getSelectedItem().toString();

        ItemDAO itemDAO = new ItemDAO();

      HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("itemName" , itemNameUp);
      //  hashMap.put("imageUrl" , city);
        hashMap.put("itemPrice" , itemPriceUp);
        hashMap.put("itemDiscount" , itemDiscountUp);
        hashMap.put("madInfo" , medInfoUp);
        hashMap.put("scanLeaflet" , scanFetUp);
        hashMap.put("type1" , type1up);
        hashMap.put("type2" , type2up);
        itemDAO.update(idToString,hashMap);


       /* NewItemClass newItemClass = new NewItemClass(id , type1up ,type2up , "imageuri" , itemNameUp , itemPriceUp , itemDiscountUp , medInfoUp , scanFetUp);
        ItemClass itemClass = new ItemClass(id , itemNameUp , urlForClass , type1up , type2up , medInfoUp , scanFetUp , user.getUid());

        itemDAO.add(idToString,newItemClass);*/
    }




    /***************************************End of Add images Functions *************************************************/


    /******************************************Spinner Functions **************************************************/


    private void Spinner()
    {
        Log.d("TAG", "Spinner: ");
        type1 = findViewById(R.id.extype1);
        type2 = findViewById(R.id.extype2);

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
    }
}


