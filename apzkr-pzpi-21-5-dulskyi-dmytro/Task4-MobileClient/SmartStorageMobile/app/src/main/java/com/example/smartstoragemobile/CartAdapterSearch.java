package com.example.smartstoragemobile;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapterSearch extends RecyclerView.Adapter<CartAdapterSearch.CartViewHolder> {
    private final Context context;
    private int iterator = 0;

    public CartAdapterSearch(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_search_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.textView1.setText(Arrays.statisticsArray.get(position + iterator));
        iterator++;
        holder.textView2.setText(Arrays.statisticsArray.get(position + iterator));
        iterator++;
        holder.textView3.setText(Arrays.statisticsArray.get(position + iterator));

    }

    @Override
    public int getItemCount() {
        return Arrays.statisticsArray.size() / 3;
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.totalSum);
            textView2 = itemView.findViewById(R.id.textProductSize);
            textView3 = itemView.findViewById(R.id.textProductCount);
        }
    }
}
