package com.secure.secure.installer.securelibary.installNewProduct;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.installNewProduct.adapter.ProductInstallRecyclerViewAdapter;
import com.secure.secure.installer.securelibary.interfaces.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewProductInstallListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView header;
    EditText et_search;

    ArrayList<String> product_list = new ArrayList<>();
    ProductInstallRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_product_install_list_activity);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        recyclerView = findViewById(R.id.recyclerView);
        et_search = findViewById(R.id.et_search);
        header = findViewById(R.id.header);


        // setup toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        GlobalClass.setToolbar(this, mToolbar, getString(R.string.new_product_installation));

        header.setText("Select " + getIntent().getStringExtra("name"));

        product_list.add("ChannelPlus XL");
        product_list.add("CentaurPlus Series");
        product_list.add("ChannelPlus XL");
        product_list.add("CentaurPlus Series");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductInstallRecyclerViewAdapter(this, product_list, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                GlobalClass.navigateToWebView(NewProductInstallListActivity.this);

            }
        });
        recyclerView.setAdapter(adapter);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // filter your list from your input
                filter(s.toString().toLowerCase());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            GlobalClass.navigateToHomePage(NewProductInstallListActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }


    void filter(String text) {
        List<String> temp = new ArrayList();
        for (String d : product_list) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.toLowerCase().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateList(temp);
    }
}
