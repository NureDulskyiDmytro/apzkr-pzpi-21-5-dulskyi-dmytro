package com.example.smartstoragemobile;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapterOrdering extends RecyclerView.Adapter<CartAdapterOrdering.CartViewHolder> {
    private final Context context;
    private int iterator = 0;

    public CartAdapterOrdering(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_ordering_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.textView1.setText(context.getResources().getString(R.string.product_id) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView2.setText(context.getResources().getString(R.string.customer_id) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView3.setText(context.getResources().getString(R.string.humidity_range_now) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView4.setText(context.getResources().getString(R.string.humidity_range_) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView5.setText(context.getResources().getString(R.string.quantity_) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView6.setText(context.getResources().getString(R.string.size_) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView7.setText(context.getResources().getString(R.string.storage_address) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView8.setText(context.getResources().getString(R.string.storage_life_) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView9.setText(context.getResources().getString(R.string.sum) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView10.setText(context.getResources().getString(R.string.temperature_range_now) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView11.setText(context.getResources().getString(R.string.temperature_range_) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView12.setText(context.getResources().getString(R.string.type_of_product_) + " " + Arrays.orderingArray.get(position + iterator));
        iterator++;
        holder.textView13.setText(context.getResources().getString(R.string.weight_) + " " + Arrays.orderingArray.get(position + iterator));
    }

    @Override
    public int getItemCount() {
        return Arrays.orderingArray.size() / 13;
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
                textView9, textView10, textView11, textView12, textView13;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.productIdOrdering);
            textView2 = itemView.findViewById(R.id.customerIdOrdering);
            textView3 = itemView.findViewById(R.id.humidityNowOrdering);
            textView4 = itemView.findViewById(R.id.humidityRangeOrdering);
            textView5 = itemView.findViewById(R.id.quantityOrdering);
            textView6 = itemView.findViewById(R.id.sizeOrdering);
            textView7 = itemView.findViewById(R.id.storageAddressOrdering);
            textView8 = itemView.findViewById(R.id.storageLifeOrdering);
            textView9 = itemView.findViewById(R.id.sumOrdering);
            textView10 = itemView.findViewById(R.id.temperatureNowOrdering);
            textView11 = itemView.findViewById(R.id.temperatureRangeOrdering);
            textView12 = itemView.findViewById(R.id.typeOfProductOrdering);
            textView13 = itemView.findViewById(R.id.weightOrdering);
        }
    }
}
