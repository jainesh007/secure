package com.secure.secure.installer.securelibary.replaceProgrammer.adapter;

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

import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.model.ProductListGS;
import com.secure.secure.installer.securelibary.replaceProgrammer.ReplacementProductList;

import java.util.List;

public class ReplaceProgrammerSelectProductAdapter extends RecyclerView.Adapter<ReplaceProgrammerSelectProductAdapter.ViewHolder> {

    private List<ProductListGS> mData;
    Context mContext;
    String manufacturerName;

    // data is passed into the constructor
    public ReplaceProgrammerSelectProductAdapter(Context context, List<ProductListGS> data, String manufacturerName) {
        this.mData = data;
        this.mContext = context;
        this.manufacturerName = manufacturerName;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.replace_programmer_select_product_list_adapter, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {

            holder.setIsRecyclable(false);

            ProductListGS product = mData.get(position);
            holder.title.setText(product.getName());

            holder.homeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, ReplacementProductList.class);
                    intent.putExtra("manufacturer_name", manufacturerName);
                    intent.putExtra("product_name", product.getName());
                    mContext.startActivity(intent);
                }
            });

            byte[] imageAsBytes = Base64.decode(product.getImage(), Base64.DEFAULT);
            holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));


        } catch (Exception e) {
            holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_image_found));
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        LinearLayout homeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            homeLayout = itemView.findViewById(R.id.homeLayout);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public void updateList(List<ProductListGS> list) {
        mData = list;
        notifyDataSetChanged();
    }
}