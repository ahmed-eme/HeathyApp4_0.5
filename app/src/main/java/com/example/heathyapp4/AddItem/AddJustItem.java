package com.example.heathyapp4.AddItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.heathyapp4.R;
import com.example.heathyapp4.User.UserInfoClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class AddJustItem extends AppCompatActivity {
    /************************** Variables for add images ******************/
    private static final int PICK_IMG = 1;
    TextView textView;
    Button selectImages,submit;
    private DatabaseReference databaseReference;

    /******************************Spinner variables *********************************/
    Spinner type1;
    Spinner type2;
    /*******************************EditText variables*********************************/
    private  EditText itemName;
    private EditText medInfo;
    private EditText scanFet;
    /*********************************Variables ready to upload*********************************/
    String itemNameUp;
    String medInfoUp;
    String scanFetUp;
    String type1up;
    String type2up;
    String idToString;
    int id;
    String urlForClass;

    UserInfoClass user = new UserInfoClass();


    /********************************new variables **************************************/
    ArrayList ImageList = new ArrayList();
    private int upload_count = 0;
    ArrayList urlStrings;
    ArrayList<Object> test = new ArrayList();
    Button add;
    AlertDialog dialog;
    LinearLayout layout;

    FirebaseFirestore db = FirebaseFirestore.getInstance();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_just_item);

        /********call function*********/

        Spinner();
        getsize();


        textView = findViewById(R.id.textProgress);
        selectImages = findViewById(R.id.addImages);
        submit = findViewById(R.id.submit);

        /**************************add more***************************/
        add = findViewById(R.id.add);
        layout = findViewById(R.id.container);

        buildDialog();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        /**************************end add more***************************/



        selectImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageList.clear();
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


    /***********************************add more Functions ************************************************/
    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_more, null);

        final EditText name = view.findViewById(R.id.CapacityEdit);
        final EditText price = view.findViewById(R.id.priceEdit);
        final EditText discount = view.findViewById(R.id.discountEdit);
        final EditText quantity = view.findViewById(R.id.qtyEdit);

        builder.setView(view);
        builder.setTitle("Enter name")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCard(name.getText().toString() , price.getText().toString() ,discount.getText().toString() , quantity.getText().toString());


                        // addCard(quantity.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog = builder.create();
    }

    private void addCard(String name , String price , String discount , String quantity) {
        final View view = getLayoutInflater().inflate(R.layout.card_add_more, null);

        TextView nameView = view.findViewById(R.id.name);
        TextView priceView = view.findViewById(R.id.priceMore);
        TextView discountView = view.findViewById(R.id.discountMore);
        TextView quantityView = view.findViewById(R.id.qtyMore);
        Button delete = view.findViewById(R.id.delete);

        nameView.setText(name);
        priceView.setText(price);
        discountView.setText(discount);
        quantityView.setText(quantity);
        double priceUp = Double.parseDouble(price);
        double discountUp = Double.parseDouble(discount);
        int qtyUp = Integer.parseInt(quantity);
        int mgUp = Integer.parseInt(name);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("mg" , mgUp);
        hashMap.put("price" , priceUp);
        hashMap.put("discount" , discountUp);
        hashMap.put("quantity" , qtyUp);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Item");
        databaseReference.child(idToString).child("Capacity").child(name).setValue(hashMap);
      //  db.collection("Item").document(idToString).collection("Capacity").document(name).set(hashMap);




        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
                databaseReference.child(idToString).child("Capacity").child(name).removeValue();
                db.collection("Item").document(idToString).collection("Capacity").document(name).delete();
            }
        });

        layout.addView(view);
    }

    /***********************************end add more Functions ************************************************/


    /*************************give value to variable ***********************************/
    private void giveValueToVariable()
    {
        Log.d("TAG", "giveValueToVariable: ");
        itemName = findViewById(R.id.ItemName);
        medInfo = findViewById(R.id.medicalInfo);
        scanFet = findViewById(R.id.scanned);

        itemNameUp = itemName.getText().toString();
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
        urlStrings = new ArrayList<>();
        StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

        for (upload_count = 0; upload_count < ImageList.size(); upload_count++) {

            Uri IndividualImage = (Uri) ImageList.get(upload_count);
            final StorageReference ImageName = ImageFolder.child("Images" + IndividualImage.getLastPathSegment());

            ImageName.putFile(IndividualImage).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageName.getDownloadUrl().addOnSuccessListener(
                                    new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            urlStrings.add(String.valueOf(uri));

                                            if (urlStrings.size() == ImageList.size()){
                                                storeLink(urlStrings);
                                            }

                                        }
                                    }
                            );
                        }
                    }
            );
        }
    }

    private void storeLink(ArrayList<String> urlStrings) {

        HashMap<String, Object> hashMap = new HashMap<>();

        for (int i = 0; i <urlStrings.size() ; i++) {
            test.add(urlStrings.get(i));

            hashMap.put("ImgLink", test);

        }
        DatabaseReference databaseReference;
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Item").child(idToString);
        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddJustItem.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
      /*  db.collection("Item").document(idToString).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddJustItem.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
            }
        });*/
        ImageList.clear();
        test.clear();
    }

    /********************************************************************/
    private void addClasstoDatabase()
    {

        Log.d("TAG", "addClasstoDatabase: ");

        itemName = findViewById(R.id.ItemName);
        medInfo = findViewById(R.id.medicalInfo);
        scanFet = findViewById(R.id.scanned);

        itemNameUp = itemName.getText().toString();
        medInfoUp = medInfo.getText().toString();
        scanFetUp = scanFet.getText().toString();
        type1up= type1.getSelectedItem().toString();
        type2up= type2.getSelectedItem().toString();
        HashMap<String, Object> itemHash = new HashMap<>();
        itemHash.put("name" , itemNameUp);
        itemHash.put("medInfo" , medInfoUp);
        itemHash.put("scanLeaflet" , scanFetUp);
        itemHash.put("type1" , type1up);
        itemHash.put("type2" , type2up);
        itemHash.put("id" , id);
        System.out.println(itemHash);
        DatabaseReference databaseReference;
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Item").child(idToString);
        databaseReference.updateChildren(itemHash);
       // db.collection("Item").document(idToString).set(itemHash);
    }

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
    }
}