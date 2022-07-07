package com.example.heathyapp4.Cart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.bumptech.glide.Glide;
import com.example.heathyapp4.Home.ItemViewClass;
import com.example.heathyapp4.R;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CartAdapter extends ArrayAdapter<CartClass> {

    private Context c;
    private Activity mActivity;
    private ImageFilterView plus , minus;
    public CartAdapter(@NonNull Context context, ArrayList<CartClass> list) {
        super(context, 0, list);
        this.c = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.cart_gv_item, parent, false);
        }

        CartClass dataModal = getItem(position);

        TextView nameTV = listitemView.findViewById(R.id.nameText);
        ImageView imageUrl = listitemView.findViewById(R.id.imageUrl);
        TextView typeTV = listitemView.findViewById(R.id.typeTxt);
        TextView daysTxt = listitemView.findViewById(R.id.daysTxt);
        TextView hoursTxt = listitemView.findViewById(R.id.hoursTxt);
        TextView minsTxt = listitemView.findViewById(R.id.minsTxt);
        TextView secTxt = listitemView.findViewById(R.id.secTxt);
        TextView priceTv = listitemView.findViewById(R.id.priceTv);
        TextView afterDis = listitemView.findViewById(R.id.afterDis);
        TextView qtyTv = listitemView.findViewById(R.id.qtyTv);

        plus = listitemView.findViewById(R.id.plus);
        minus = listitemView.findViewById(R.id.minus);

        //TODO : GIVE variable .get
        //TODO : find solution for days end

        nameTV.setText(dataModal.getName());
        typeTV.setText(dataModal.getType1());

        priceTv.setText(String.valueOf(dataModal.getPrice()));
        priceTv.setPaintFlags(priceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);



        double finaldis = (dataModal.getPrice() / 100.0f) * dataModal.getDiscount();

        double precentDis = dataModal.getPrice() - finaldis;

        afterDis.setText(String.valueOf(precentDis));

        qtyTv.setText(String.valueOf(dataModal.getQuantity()));

        System.out.println(dataModal.getImgLink());

     //   Picasso.get().load(dataModal.getImgLink()).into(imageUrl);

        countDownStart(dataModal.getEndDeal() ,  daysTxt ,  hoursTxt ,  minsTxt ,  secTxt);

        Glide
                .with(getContext())
                .load(dataModal.getImgLink())
                .centerCrop()
                .placeholder(R.drawable.loading_icon)
                .into(imageUrl);


        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Item clicked is : " + dataModal.getItemId(), Toast.LENGTH_SHORT).show();

            }
        });

        return listitemView;
    }
    /*****************************************************************************************/
    private Handler handler = new Handler();
    private Runnable runnable;
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private void countDownStart(String endDeal , TextView daysTxt , TextView hoursTxt , TextView minTxt , TextView secTxt) {
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

   /* protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }*/



}
