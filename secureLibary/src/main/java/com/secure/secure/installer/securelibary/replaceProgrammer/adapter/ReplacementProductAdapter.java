package com.secure.secure.installer.securelibary.replaceProgrammer.adapter;

import android.content.Context;
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
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.interfaces.ItemClickListener;
import com.secure.secure.installer.securelibary.model.ProductListGS;

import java.util.List;

public class ReplacementProductAdapter extends RecyclerView.Adapter<ReplacementProductAdapter.ViewHolder> {

    private List<ProductListGS> mData;
    private ItemClickListener mClickListener;
    Context mContext;

    // data is passed into the constructor
    public ReplacementProductAdapter(Context context, List<ProductListGS> data, ItemClickListener itemClickListener) {
        this.mData = data;
        this.mContext = context;
        this.mClickListener = itemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.replacement_product_adapter, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {

            holder.setIsRecyclable(false);

            ProductListGS product = mData.get(position);
            holder.title.setText(product.getName());

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


            holder.description.setText(GlobalClass.setBulletData(mContext, product.getApplication_details().split("\\|")));

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
}