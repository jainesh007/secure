package com.secure.secure.installer.securelibary.productGuide.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.interfaces.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductGuideSubRecyclerViewAdapter extends RecyclerView.Adapter<ProductGuideSubRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private ItemClickListener mClickListener;
    Context mContext;

    // data is passed into the constructor
    public ProductGuideSubRecyclerViewAdapter(Context context, List<String> data, ItemClickListener itemClickListener) {
        this.mData = data;
        this.mContext = context;
        this.mClickListener = itemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_guide_sub_recycler_view_adapter, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.title.setText(animal);

        Picasso.with(mContext).load("https://www.heatingcontrolsonline.co.uk/image/cache/data/Horstmann/H27-500x500.png").noFade().into(holder.imageView);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }
}