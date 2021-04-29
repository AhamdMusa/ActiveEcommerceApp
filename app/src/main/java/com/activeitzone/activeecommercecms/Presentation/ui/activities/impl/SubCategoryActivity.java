package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.activeitzone.activeecommercecms.Models.Category;
import com.activeitzone.activeecommercecms.Models.SubCategory;
import com.activeitzone.activeecommercecms.Models.SubSubCategory;
import com.activeitzone.activeecommercecms.Presentation.presenters.CategoryPresenter;
import com.activeitzone.activeecommercecms.Presentation.presenters.SubSubCategoryPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.SubCategoryView;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.AllCategoryAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.SubCategoryAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.CategoryView;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.CategoriesFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.AllCategoryClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.SubCategoryClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.SubSubCategoryClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryActivity extends BaseActivity implements SubCategoryView, SubCategoryClickListener, CategoryView, AllCategoryClickListener, SwipeRefreshLayout.OnRefreshListener  {

    private CategoryPresenter categoryPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Category> mCategories = new ArrayList<>();
    private RecyclerView categoryrecyclerView, subcategoryrecyclerView;
    private AllCategoryAdapter adapter;
    private List<SubCategory> subCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_sub_category);
        Toast.makeText(SubCategoryActivity.this, "asgasgsagas", Toast.LENGTH_SHORT).show();
    //  Category category = (Category) getIntent().getSerializableExtra("category");
        //--------------------------------------id----------------------------------//
        mSwipeRefreshLayout =findViewById(R.id.swipe_container);
        subcategoryrecyclerView = findViewById(R.id.subcategory_list);
        categoryrecyclerView = findViewById(R.id.category_list);
        //--------------------------------------id----------------------------------//



        //--------------------------------------mSwipeRefreshLayout----------------------------------//
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        //--------------------------------------mSwipeRefreshLayout----------------------------------//


        //--------------------------------------categoryrecyclerView----------------------------------//
        categoryrecyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        categoryrecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AllCategoryAdapter(this, mCategories, this);
        categoryrecyclerView.addItemDecoration(new LayoutMarginDecoration(1, AppConfig.convertDpToPx(this, 10)));
        categoryrecyclerView.setAdapter(adapter);
        categoryPresenter = new CategoryPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        categoryPresenter.getAllCategories();

        //--------------------------------------categoryrecyclerView----------------------------------//

        initializeActionBar();
        setTitle("Category");


    }

    @Override
    public void setSubCategories(List<SubCategory> subCategories) {
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        subcategoryrecyclerView.setLayoutManager(linearLayoutManager);
        subcategoryrecyclerView.addItemDecoration( new LayoutMarginDecoration( 1,  AppConfig.convertDpToPx(getApplicationContext(), 10)) );
        SubCategoryAdapter adapter = new SubCategoryAdapter(this, subCategories, this);
        subcategoryrecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSubCategoryItemClick(SubCategory subCategory) {
        Intent intent = new Intent(this, ProductListingActivity.class);
        intent.putExtra("title", subCategory.getName());
        intent.putExtra("url", subCategory.getLinks().getProducts());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        categoryPresenter.getAllCategories();
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
        SubSubCategoryPresenter subSubCategoryPresenter = new SubSubCategoryPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        subSubCategoryPresenter.getSubSubCategories(category.getLinks().getSubCategories());
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        subcategoryrecyclerView.setLayoutManager(linearLayoutManager);
        subcategoryrecyclerView.addItemDecoration( new LayoutMarginDecoration( 1,  AppConfig.convertDpToPx(getApplicationContext(), 10)) );
        SubCategoryAdapter adapter = new SubCategoryAdapter(this, subCategories, this);
        subcategoryrecyclerView.setAdapter(adapter);
    }
}
