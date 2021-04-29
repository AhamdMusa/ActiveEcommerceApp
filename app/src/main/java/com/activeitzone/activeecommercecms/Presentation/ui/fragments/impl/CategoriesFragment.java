package com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeitzone.activeecommercecms.Models.Category;
import com.activeitzone.activeecommercecms.Models.SubCategory;
import com.activeitzone.activeecommercecms.Presentation.presenters.CategoryPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.SubCategoryView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.ProductListingActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.SubCategoryActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.AllCategoryAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.SubCategoryAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.CategoryView;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.AllCategoryClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.SubCategoryClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CategoriesFragment extends Fragment implements CategoryView, AllCategoryClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View v;
    private CategoryPresenter categoryPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Category> mCategories = new ArrayList<>();
    private RecyclerView categoryrecyclerView;
    private AllCategoryAdapter adapter;

    private Context context;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_categories, null);

        mSwipeRefreshLayout = v.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        categoryrecyclerView = v.findViewById(R.id.category_list);
        categoryrecyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        categoryrecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AllCategoryAdapter(getActivity(), mCategories, CategoriesFragment.this);
        categoryrecyclerView.addItemDecoration(new LayoutMarginDecoration(1, AppConfig.convertDpToPx(getContext(), 10)));
        categoryrecyclerView.setAdapter(adapter);

        categoryPresenter = new CategoryPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        categoryPresenter.getAllCategories();

        return v;
    }

    @Override
    public void setAllCategories(List<Category> categories) {
        mCategories.clear();
        mCategories.addAll(categories);
        adapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCategoryClick(Category category) {
        // Intent intent = new Intent(getContext(), SubCategoryActivity.class);
        //  intent.putExtra("category", category);
        //  startActivity(intent);


    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        categoryPresenter.getAllCategories();
    }


}
