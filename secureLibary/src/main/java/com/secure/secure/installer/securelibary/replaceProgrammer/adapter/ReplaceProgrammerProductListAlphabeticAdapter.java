package com.secure.secure.installer.securelibary.replaceProgrammer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.model.Data;
import com.secure.secure.installer.securelibary.replaceProgrammer.ReplaceProgrammerProductList;

import java.util.ArrayList;
import java.util.List;

public class ReplaceProgrammerProductListAlphabeticAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SectionIndexer, Comparable {

    List<Data> dataList;
    private ArrayList<Integer> mSectionPositions;

    public static final int SECTION_VIEW = 0;
    public static final int CONTENT_VIEW = 1;
    Context mContext;

    public ReplaceProgrammerProductListAlphabeticAdapter(Context mContext, List<Data> dataList) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == SECTION_VIEW) {
            return new SectionHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_title, parent, false));
        }
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (mContext == null) {
            return;
        }
        if (SECTION_VIEW == getItemViewType(position)) {

            SectionHeaderViewHolder sectionHeaderViewHolder = (SectionHeaderViewHolder) holder;
            Data sectionItem = dataList.get(position);
            sectionHeaderViewHolder.headerTitleTextview.setText(sectionItem.getTitle());
            return;
        }

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Data currentItem = ((Data) dataList.get(position));

        itemViewHolder.nameTextview.setText(currentItem.getTitle());

        itemViewHolder.nameTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ReplaceProgrammerProductList.class);
                intent.putExtra("product_name", currentItem.getTitle());
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>();
        mSectionPositions = new ArrayList<>();
        for (int i = 0, size = dataList.size(); i < size; i++) {
            String section = String.valueOf(dataList.get(i).getTitle().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int i) {
        return mSectionPositions.get(i);
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextview;

        public ItemViewHolder(View itemView, final Context context) {
            super(itemView);
            nameTextview = (TextView) itemView.findViewById(R.id.nameTextview);
        }
    }

    public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitleTextview;

        public SectionHeaderViewHolder(View itemView) {
            super(itemView);
            headerTitleTextview = (TextView) itemView.findViewById(R.id.headerTitleTextview);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).isSection) {
            return SECTION_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }

    public void updateList(List<Data> list) {
        dataList = list;
        notifyDataSetChanged();
    }
}