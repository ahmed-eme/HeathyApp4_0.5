package com.example.heathyapp4.Item;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.heathyapp4.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ViewHolder> {
    private ArrayList<String> dataModalArrayList;
    private Context context;
    private ImageLesner imageLesner;

    // constructor class for our Adapter
    public AdapterImages(ArrayList<String> dataModalArrayList, Context context) {
        this.dataModalArrayList = dataModalArrayList;
        this.context = context;
        try {
            this.imageLesner = ((AdapterImages.ImageLesner)context);
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(e.getMessage());
        }
    }

    @NonNull
    @Override
    public AdapterImages.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterImages.ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_images_firebase, parent, false));
    }
 int row_index;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull AdapterImages.ViewHolder holder, int position) {
        final String modal = dataModalArrayList.get(position);


        Glide
                .with(context)
                .load(modal)
                .centerCrop()
                .placeholder(R.drawable.loading_icon)
                .into(holder.courseIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index= position;
                notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("image" , modal);
                imageLesner.ImageLesner(intent);
               // Toast.makeText(context, "Clicked item is " + modal.length(), Toast.LENGTH_SHORT).show();
            }
        });

        if(row_index == position){

            holder.itemView.setForeground(null);
            holder.itemView.setBackgroundColor(Color.parseColor("#00C6FF"));
            // holder.tv1.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            Drawable replace = (Drawable) context.getResources().getDrawable(R.drawable.gray_image);
            holder.itemView.setForeground(replace);
            holder.itemView.setBackgroundColor(Color.parseColor("#BFBFBF"));
            // holder.tv1.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {
        return dataModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView courseNameTV;
        private ImageView courseIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseIV = itemView.findViewById(R.id.idIVimage);
        }
    }

    public interface ImageLesner {
        public void ImageLesner(Intent intent);
    }
}

