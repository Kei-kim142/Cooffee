package com.keiapp.cooffee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.keiapp.cooffee.R;
import com.keiapp.cooffee.model.OrderModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<OrderModel> orderModels;
    private Context ctx;

    public OrderAdapter(List<OrderModel> orderModels, Context ctx) {
        this.orderModels = orderModels;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_menu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel orderModel = orderModels.get(position);

        holder.name.setText(orderModel.getName());
        holder.price.setText(String.format("%.2f",Float.parseFloat(String.valueOf(orderModel.getPrice()))));
        holder.description.setText(orderModel.getDescription());

        if (orderModel.getDiscount() == 0){
            holder.discount.setVisibility(View.GONE);
        }else{
            holder.discount.setVisibility(View.VISIBLE);
            holder.discount.setText(orderModel.getDiscount() + "% Off");
        }
    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,price,description;
        private Button discount,addToCart;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.orderName);
            price = itemView.findViewById(R.id.orderPrice);
            description = itemView.findViewById(R.id.orderDesc);
            discount = itemView.findViewById(R.id.orderDiscount);
            addToCart = itemView.findViewById(R.id.orderAddToCart);
            imageView = itemView.findViewById(R.id.orderImg);

        }
    }
}
