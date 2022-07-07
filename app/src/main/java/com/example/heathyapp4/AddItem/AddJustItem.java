package com.example.heathyapp4.AddItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

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
        builder.setTitle("Enter product details")
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

    Calendar date;
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss" ;
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT , Locale.US);
    Context context = this;



    public void showDateTimePicker(String capacity) {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        String formatted = dateFormat.format(date.getTime());

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("endDeal" , formatted);
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Item");
                        databaseReference.child(idToString).child("Capacity").child(capacity).updateChildren(hashMap);

                        Toast.makeText(AddJustItem.this, "" + formatted, Toast.LENGTH_SHORT).show();

                        Log.v("aa", "The choosen one " + formatted);
                    }
                },
                        currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }


    private void addCard(String capacity , String price , String discount , String quantity) {
        final View view = getLayoutInflater().inflate(R.layout.card_add_more, null);

        TextView nameView = view.findViewById(R.id.name);
        TextView priceView = view.findViewById(R.id.priceMore);
        TextView discountView = view.findViewById(R.id.discountMore);
        TextView quantityView = view.findViewById(R.id.qtyMore);
        Button delete = view.findViewById(R.id.delete);
        Button addTime = view.findViewById(R.id.addTime);

        nameView.setText(capacity);
        priceView.setText(price);
        discountView.setText(discount);
        quantityView.setText(quantity);
        double priceUp = Double.parseDouble(price);
        double discountUp = Double.parseDouble(discount);
        int qtyUp = Integer.parseInt(quantity);
        int mgUp = Integer.parseInt(capacity);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("mg" , mgUp);
        hashMap.put("price" , priceUp);
        hashMap.put("discount" , discountUp);
        hashMap.put("quantity" , qtyUp);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Item");
        databaseReference.child(idToString).child("Capacity").child(capacity).setValue(hashMap);
      //  db.collection("Item").document(idToString).collection("Capacity").document(name).set(hashMap);

        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(capacity);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
                databaseReference.child(idToString).child("Capacity").child(capacity).removeValue();
                db.collection("Item").document(idToString).collection("Capacity").document(capacity).delete();
            }
        });

        layout.addView(view);
    }

    /***********************************end of add more Functions ************************************************/


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