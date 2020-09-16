package com.secure.secure.installer.securelibary.commonAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.installNewProduct.ProductDetailsActivity;
import com.secure.secure.installer.securelibary.model.ProductTableData;

import java.util.List;

public class SearchProductRecyclerViewAdapter extends RecyclerView.Adapter<SearchProductRecyclerViewAdapter.ViewHolder> {

    private List<ProductTableData> product_list;
    Context mContext;

    // data is passed into the constructor
    public SearchProductRecyclerViewAdapter(Context context, List<ProductTableData> product_list) {
        this.product_list = product_list;
        this.mContext = context;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recent_search_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {

            holder.setIsRecyclable(false);

            ProductTableData product = product_list.get(position);
            holder.myTextView.setText(product.getProductModelName());

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
        TextView myTextView;
        LinearLayout homeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            homeLayout = itemView.findViewById(R.id.homeLayout);
            myTextView = itemView.findViewById(R.id.product_name);
        }
    }

    public void updateList(List<ProductTableData> list) {
        product_list = list;
        notifyDataSetChanged();
    }
}