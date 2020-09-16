package com.secure.secure.installer.securelibary.productGuide;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.commonAdapter.SingleTextRecyclerViewAdapter;
import com.secure.secure.installer.securelibary.database.AppDatabase;
import com.secure.secure.installer.securelibary.database.AppExecutors;
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.interfaces.ItemClickListener;
import com.secure.secure.installer.securelibary.model.ProductTableData;
import com.secure.secure.installer.securelibary.productGuide.adapter.ProductGuideSearchProductRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductGuide extends AppCompatActivity {

    TextView txt_wireless, txt_heating;
    NestedScrollView nestedScrollView;
    EditText et_search;
    RecyclerView recyclerView, heatingRecyclerView, wirelessRecyclerView;

    boolean heating = false;
    boolean wireless = false;

    SingleTextRecyclerViewAdapter heatingAdapter;
    SingleTextRecyclerViewAdapter wirelessAdapter;

    ProductGuideSearchProductRecyclerViewAdapter recyclerViewAdapter;
    private AppDatabase mDb;

    private List<ProductTableData> product_list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_guide_layout);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        init();

        // setup toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        GlobalClass.setToolbar(this, mToolbar, getString(R.string.product_guide));

        mDb = AppDatabase.getInstance(ProductGuide.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        prepareListData();

        // heating button click
        txt_heating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (heating) {
                    heating = false;
                    heatingRecyclerView.setVisibility(View.GONE);
                    txt_heating.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);
                } else {
                    heating = true;
                    heatingRecyclerView.setVisibility(View.VISIBLE);
                    txt_heating.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0);
                }
            }
        });

        // wireless button click
        txt_wireless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wireless) {
                    wireless = false;
                    wirelessRecyclerView.setVisibility(View.GONE);
                    txt_wireless.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);
                } else {
                    wireless = true;
                    wirelessRecyclerView.setVisibility(View.VISIBLE);
                    txt_wireless.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0);

                    nestedScrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            nestedScrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                }
            }
        });


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                try {

                    product_list = mDb.productDetailsDao().getAllProductOfSecure();
                    recyclerViewAdapter = new ProductGuideSearchProductRecyclerViewAdapter(ProductGuide.this, product_list);
                    recyclerView.setAdapter(recyclerViewAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void prepareListData() {

        // for Heating and Hot water controls
        final List<String> heating_control = new ArrayList<String>();
        heating_control.add("Timeswitches & programmers");
        heating_control.add("Programmable thermostats");
        heating_control.add("Motorised valves & actuators");
        heating_control.add("Electric water heating controls");
        heating_control.add("Boost controls");
        heating_control.add("Thermostats");

        heatingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        heatingAdapter = new SingleTextRecyclerViewAdapter(this, heating_control, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getApplicationContext(), ProductGuideSubProduct.class);
                intent.putExtra("name", "" + heating_control.get(position));
                startActivity(intent);

            }
        });
        heatingRecyclerView.setAdapter(heatingAdapter);

        // for Wireless Controls
        final List<String> wireless_controls = new ArrayList<String>();
        wireless_controls.add("Timeswitches & programmers");
        wireless_controls.add("Programmable thermostats");

        wirelessRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wirelessAdapter = new SingleTextRecyclerViewAdapter(this, wireless_controls, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getApplicationContext(), ProductGuideSubProduct.class);
                intent.putExtra("name", "" + wireless_controls.get(position));
                startActivity(intent);

            }
        });
        wirelessRecyclerView.setAdapter(wirelessAdapter);


    }

    void filter(String text) {
        List<ProductTableData> temp = new ArrayList();
        for (ProductTableData d : product_list) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getProductModelName().toLowerCase().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        recyclerViewAdapter.updateList(temp);
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        wirelessRecyclerView = findViewById(R.id.wirelessRecyclerView);
        heatingRecyclerView = findViewById(R.id.heatingRecyclerView);
        et_search = findViewById(R.id.et_search);
        nestedScrollView = findViewById(R.id.scrollView);
        txt_heating = findViewById(R.id.txt_heating);
        txt_wireless = findViewById(R.id.txt_wireless);
    }
}

