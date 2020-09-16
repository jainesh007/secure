package com.secure.secure.installer.securelibary.replaceProgrammer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.database.AppDatabase;
import com.secure.secure.installer.securelibary.database.AppExecutors;
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.model.ProductListGS;
import com.secure.secure.installer.securelibary.replaceProgrammer.adapter.ReplaceProgrammerSelectProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReplaceProgrammerProductList extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView header;
    EditText et_search;
    AppDatabase mDb;
    String manufacturerName;
    List<ProductListGS> product_list = new ArrayList<>();
    ReplaceProgrammerSelectProductAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replace_programmer_select_product_list);

        recyclerView = findViewById(R.id.recyclerView);
        header = findViewById(R.id.header);
        et_search = findViewById(R.id.et_search);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mDb = AppDatabase.getInstance(ReplaceProgrammerProductList.this);

        manufacturerName = getIntent().getStringExtra("product_name");

        recyclerView.setLayoutManager(new LinearLayoutManager(ReplaceProgrammerProductList.this));

        // setup toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        GlobalClass.setToolbar(this, mToolbar, getString(R.string.replace_a_programmer));

        header.setText("Select existing " + manufacturerName + " product");

        setUpData();

        adapter = new ReplaceProgrammerSelectProductAdapter(ReplaceProgrammerProductList.this, product_list, manufacturerName);
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
            GlobalClass.navigateToHomePage(ReplaceProgrammerProductList.this);
        }
        return super.onOptionsItemSelected(item);
    }

    void filter(String text) {
        List<ProductListGS> temp = new ArrayList();
        for (ProductListGS d : product_list) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getName().toLowerCase().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateList(temp);
    }

    void setUpData() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {

                    List<String> list = mDb.productDetailsDao().getAllProductName(manufacturerName);

                    for (int i = 0; i < list.size(); i++) {
                        String image = mDb.productDetailsDao().getProductImage(list.get(i).trim().toLowerCase());
                        ProductListGS product = new ProductListGS();
                        product.setName(list.get(i));
                        product.setImage(image);
                        product_list.add(product);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
