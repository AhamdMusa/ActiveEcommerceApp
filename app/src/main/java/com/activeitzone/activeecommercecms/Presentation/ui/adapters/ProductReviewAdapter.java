package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.Review;
import com.activeitzone.activeecommercecms.R;

import java.util.List;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.ViewHolder> {
    private List<Review> mReviews;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public ProductReviewAdapter(Context context, List<Review> mReviews) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mReviews = mReviews;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ProductReviewAdapter.ViewHolder holder, int position) {
        holder.bind(mReviews.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    // convenience method for getting data at click position
    public Review getItem(int id) {
        return mReviews.get(id);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView review_user_name, review_time, review_comment;
        RatingBar review_rating;

        ViewHolder(View itemView) {
            super(itemView);
            review_user_name = itemView.findViewById(R.id.review_user_name);
            review_time = itemView.findViewById(R.id.review_time);
            review_comment = itemView.findViewById(R.id.review_comment);
            review_rating = itemView.findViewById(R.id.review_rating);
        }

        public void bind(Review review) {
            review_user_name.setText(review.getUser().getName());
            review_time.setText(review.getTime());
            review_comment.setText(review.getComment());
            review_rating.setRating(review.getRating());
        }
    }
}
