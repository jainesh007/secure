package com.secure.secure.installer.securelibary.commonAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.interfaces.ItemClickListener;

import java.util.List;

public class SingleTextRecyclerViewAdapter extends RecyclerView.Adapter<SingleTextRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private ItemClickListener mClickListener;
    Context mContext;

    // data is passed into the constructor
    public SingleTextRecyclerViewAdapter(Context context, List<String> data, ItemClickListener mClickListener) {
        this.mData = data;
        this.mContext = context;
        this.mClickListener = mClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_text_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.title.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
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