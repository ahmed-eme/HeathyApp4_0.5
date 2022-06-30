package com.example.heathyapp4.Item;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.heathyapp4.R;
import java.util.ArrayList;

public class CapacityAdapter extends RecyclerView.Adapter<CapacityAdapter.ViewHolder> {
    private ArrayList<CapacityClass> dataModalArrayList;
    private Context context;
    private ItemInfoLisner itemInfoLisner;

    public CapacityAdapter(ArrayList<CapacityClass> dataModalArrayList, Context context) {
        this.dataModalArrayList = dataModalArrayList;
        this.context = context;
        try {
            this.itemInfoLisner = ((ItemInfoLisner)context);
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(e.getMessage());
        }
    }

    @NonNull
    @Override
    public CapacityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CapacityAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.capacity_adapter, parent, false));
    }
    int row_index;
    @Override
    public void onBindViewHolder(@NonNull CapacityAdapter.ViewHolder holder, int position) {

        final CapacityClass modal = dataModalArrayList.get(position);



            String mg = String.valueOf(modal.getMg());
            holder.capBtn.setText(mg);
            holder.capBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // holder.capBtn.setBackgroundColor(Color.BLUE);
                    row_index= position;
                    notifyDataSetChanged();
                    Intent intent = new Intent();
                    intent.putExtra("Price" , modal.getPrice());
                    intent.putExtra("discount" , modal.getDiscount());
                    intent.putExtra("Qty" , modal.getQuantity());
                    intent.putExtra("mg" , modal.getMg());


                    itemInfoLisner.ItemInfoLisner(intent);
                /* int pos = modal.getMg();
                Intent intent = new Intent(v.getContext(), ItemDetails.class);
                intent.putExtra("pos",pos);*/
                    //Toast.makeText(context, "Clicked item is " + modal.getQty(), Toast.LENGTH_SHORT).show();
                }
            });

        if(row_index == position){
            holder.capBtn.setBackgroundColor(Color.parseColor("#00C6FF"));
           // holder.tv1.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.capBtn.setBackgroundColor(Color.parseColor("#BFBFBF"));
           // holder.tv1.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {

        return dataModalArrayList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        private Button capBtn;
        private Button sdfs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            capBtn = itemView.findViewById(R.id.capacityBtn);


        }
    }

    public interface ItemInfoLisner {
        public void ItemInfoLisner(Intent intent);
    }
}
