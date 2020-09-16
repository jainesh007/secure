package com.secure.secure.installer.securelibary.installNewProduct;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.commonAdapter.SearchProductRecyclerViewAdapter;
import com.secure.secure.installer.securelibary.commonAdapter.SingleTextRecyclerViewAdapter;
import com.secure.secure.installer.securelibary.database.AppDatabase;
import com.secure.secure.installer.securelibary.database.AppExecutors;
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.installNewProduct.adapter.NewProductRecyclerViewAdapter;
import com.secure.secure.installer.securelibary.interfaces.ItemClickListener;
import com.secure.secure.installer.securelibary.model.ProductTableData;

import java.util.ArrayList;
import java.util.List;

public class InstallNewProduct extends AppCompatActivity {

    RecyclerView recyclerView, newProductRecyclerView, heatingRecyclerView, wirelessRecyclerView;
    TextView txt_wireless, txt_heating;
    NestedScrollView nestedScrollView;
    LinearLayout searchedLayout;
    EditText et_search;

    boolean heating = false;
    boolean wireless = false;

    SingleTextRecyclerViewAdapter heatingAdapter;
    SingleTextRecyclerViewAdapter wirelessAdapter;
    SearchProductRecyclerViewAdapter recyclerViewAdapter;

    private AppDatabase mDb;
    private NewProductRecyclerViewAdapter newProductRecyclerViewAdapter;

    private List<ProductTableData> product_list = new ArrayList<>();
    private List<ProductTableData> search_list = new ArrayList<>();

    private void init() {

        txt_heating = findViewById(R.id.txt_heating);
        txt_wireless = findViewById(R.id.txt_wireless);
        et_search = findViewById(R.id.et_search);
        nestedScrollView = findViewById(R.id.scrollView);
        searchedLayout = findViewById(R.id.searchedLayout);
        recyclerView = findViewById(R.id.recyclerView);
        newProductRecyclerView = findViewById(R.id.newProductRecyclerView);
        heatingRecyclerView = findViewById(R.id.heatingRecyclerView);
        wirelessRecyclerView = findViewById(R.id.wirelessRecyclerView);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intall_a_new_product);

        init();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mDb = AppDatabase.getInstance(InstallNewProduct.this);

        // setup toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        GlobalClass.setToolbar(this, mToolbar, getString(R.string.new_product_installation));
        prepareListData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newProductRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager2);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                try {

                    List<ProductTableData> search_list = mDb.productDetailsDao().getSearchCountProduct();
                    List<ProductTableData> list = mDb.productDetailsDao().getAllProductOfSecure();

                    Log.d("++>>", "run: " + search_list.size());

                    setRecyclerView(list, search_list);

                    if (search_list.size() != 0) {
                        searchedLayout.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

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
        List<String> heating_control = new ArrayList<String>();
        heating_control.add("TimeSwitches & programmers");
        heating_control.add("Programmable thermostats");
        heating_control.add("Motorised valves & actuators");

        heatingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        heatingAdapter = new SingleTextRecyclerViewAdapter(this, heating_control, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), NewProductInstallListActivity.class);
                intent.putExtra("name", "" + heating_control.get(position));
                startActivity(intent);
            }
        });
        heatingRecyclerView.setAdapter(heatingAdapter);

        // for Wireless Controls
        List<String> wireless_controls = new ArrayList<String>();
        wireless_controls.add("Thermostats");

        wirelessRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wirelessAdapter = new SingleTextRecyclerViewAdapter(this, wireless_controls, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), NewProductInstallListActivity.class);
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
        newProductRecyclerViewAdapter.updateList(temp);
    }

    private void setRecyclerView(List<ProductTableData> list, List<ProductTableData> second_list) {

        product_list.addAll(list);
        search_list.addAll(second_list);

        newProductRecyclerViewAdapter = new NewProductRecyclerViewAdapter(InstallNewProduct.this, product_list);
        newProductRecyclerView.setAdapter(newProductRecyclerViewAdapter);

        recyclerViewAdapter = new SearchProductRecyclerViewAdapter(InstallNewProduct.this, search_list);
        recyclerView.setAdapter(recyclerViewAdapter);

    }


}