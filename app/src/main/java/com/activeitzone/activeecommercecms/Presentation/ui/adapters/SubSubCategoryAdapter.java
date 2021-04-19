package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.SubSubCategory;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.SubSubCategoryClickListener;
import com.activeitzone.activeecommercecms.R;

import java.util.List;

public class SubSubCategoryAdapter extends RecyclerView.Adapter<SubSubCategoryAdapter.ViewHolder> {

    private List<SubSubCategory> subSubCategories;
    private LayoutInflater mInflater;
    private SubSubCategoryClickListener subSubCategoryClickListener;

    // data is passed into the constructor
    public SubSubCategoryAdapter(Context context, List<SubSubCategory> subSubCategories) {
        this.mInflater = LayoutInflater.from(context);
        this.subSubCategories = subSubCategories;
        this.subSubCategoryClickListener = (SubSubCategoryClickListener) context;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.sub_sub_category_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(subSubCategories.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return subSubCategories.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.subsubcategory_name);
        }

        public void bind(final SubSubCategory subSubCategory) {
            textView.setText(subSubCategory.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subSubCategoryClickListener.onSubSubCategoryClick(subSubCategory);
                }
            });
        }
    }
}
