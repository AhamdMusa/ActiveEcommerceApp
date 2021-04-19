package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.activeitzone.activeecommercecms.R;

public class BaseActivity extends AppCompatActivity {
    protected ImageButton cart, search,scan;

    public void initializeActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);

        View view =getSupportActionBar().getCustomView();

        cart = view.findViewById(R.id.action_bar_cart);
        search = view.findViewById(R.id.action_bar_search);
        scan = view.findViewById(R.id.scanImage);

        cart.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
    }


}
