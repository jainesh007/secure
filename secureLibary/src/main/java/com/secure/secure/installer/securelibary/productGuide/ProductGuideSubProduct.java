package com.secure.secure.installer.securelibary.productGuide;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.interfaces.ItemClickListener;
import com.secure.secure.installer.securelibary.productGuide.adapter.ProductGuideSubRecyclerViewAdapter;

import java.util.ArrayList;

public class ProductGuideSubProduct extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView header;
    ArrayList<String> product_list = new ArrayList<>();
    ProductGuideSubRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_guide_sub_product_layout);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        header = findViewById(R.id.header);
        recyclerView = findViewById(R.id.recyclerView);

        // setup toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        GlobalClass.setToolbar(this, mToolbar, getString(R.string.product_guide));

        header.setText("Select " + getIntent().getStringExtra("name"));

        product_list.add("ChannelPlus XL");
        product_list.add("CentaurPlus Series");
        product_list.add("ChannelPlus XL");
        product_list.add("CentaurPlus Series");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductGuideSubRecyclerViewAdapter(this, product_list, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                GlobalClass.navigateToWebView(ProductGuideSubProduct.this);

            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            GlobalClass.navigateToHomePage(ProductGuideSubProduct.this);
        }
        return super.onOptionsItemSelected(item);
    }
}
