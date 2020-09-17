package com.secure.secure.installer.securelibary.installNewProduct.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.installNewProduct.ProductDetailsActivity;
import com.secure.secure.installer.securelibary.model.ProductTableData;

import java.util.List;

public class NewProductRecyclerViewAdapter extends RecyclerView.Adapter<NewProductRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<ProductTableData> product_list;

    // data is passed into the constructor
    public NewProductRecyclerViewAdapter(Context context, List<ProductTableData> product_list) {
        this.mContext = context;
        this.product_list = product_list;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.new_search_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        try {

            ProductTableData product = product_list.get(position);
            holder.myTextView.setText(product.getProductModelName());

            if (product.getIs_new_product().toLowerCase().equalsIgnoreCase("yes")) {
                holder.badge_notification.setVisibility(View.VISIBLE);
            } else {
                holder.badge_notification.setVisibility(View.INVISIBLE);
            }

            holder.homeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                    intent.putExtra("product_name", "" + product.getProductModelName());
                    mContext.startActivity(intent);
                }
            });

            byte[] imageAsBytes = Base64.decode(product.getImage(), Base64.DEFAULT);
            holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));


        } catch (Exception e) {
            holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_image_found));
            e.printStackTrace();
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return product_list.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView myTextView, badge_notification;
        RelativeLayout homeLayout;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            myTextView = itemView.findViewById(R.id.product_name);
            homeLayout = itemView.findViewById(R.id.homeLayout);
            badge_notification = itemView.findViewById(R.id.badge_notification);

        }
    }


    public void updateList(List<ProductTableData> list) {
        product_list = list;
        notifyDataSetChanged();
    }
}