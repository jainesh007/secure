package com.secure.secure.installer.securelibary.installNewProduct.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.interfaces.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductInstallRecyclerViewAdapter extends RecyclerView.Adapter<ProductInstallRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private ItemClickListener mClickListener;
    Context mContext;

    // data is passed into the constructor
    public ProductInstallRecyclerViewAdapter(Context context, List<String> data, ItemClickListener clickListener) {
        this.mData = data;
        this.mContext = context;
        this.mClickListener = clickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_install_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = mData.get(position);
        holder.title.setText(item);

        Picasso.with(mContext).load("https://www.heatingcontrolsonline.co.uk/image/cache/data/Horstmann/H27-500x500.png").noFade().into(holder.imageView);

        String details = " Time control and domestic gas \n Combi boilers \n Stored HW system \n Underfloor heating system \n Time control of multiple heating zones ";

        holder.description.setText(GlobalClass.setBulletData(mContext, details.split("\n")));

        holder.showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.textLayout.getVisibility() == View.GONE) {
                    holder.textLayout.setVisibility(View.VISIBLE);
                    holder.showMore.setText("Show Less");
                } else {
                    holder.textLayout.setVisibility(View.GONE);
                    holder.showMore.setText("Show More");
                }
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView description, showMore;
        ImageView imageView;
        LinearLayout textLayout;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            showMore = itemView.findViewById(R.id.showMore);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.imageView);
            textLayout = itemView.findViewById(R.id.textLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void updateList(List<String> list) {
        mData = list;
        notifyDataSetChanged();
    }
}