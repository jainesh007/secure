package com.secure.secure.installer.securelibary.replaceProgrammer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.database.AppDatabase;
import com.secure.secure.installer.securelibary.database.AppExecutors;
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.interfaces.ItemClickListener;
import com.secure.secure.installer.securelibary.model.ProductListGS;
import com.secure.secure.installer.securelibary.replaceProgrammer.adapter.ReplacementProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReplacementProductList extends AppCompatActivity {

    RecyclerView otherProductRecyclerView, recommendedRecyclerView;
    LinearLayout otherProductLayout, recommendedProductLayout;

    ArrayList<String> recommended_list = new ArrayList<>();
    List<String> other_product_list = new ArrayList<>();

    List<ProductListGS> all_recommended_list = new ArrayList<>();
    List<ProductListGS> all_other_product_list = new ArrayList<>();

    ReplacementProductAdapter recommended_adapter;
    ReplacementProductAdapter other_product_adapter;

    String manufacturer_name;
    String product_name;
    AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replacement_product_list);

        otherProductLayout = findViewById(R.id.otherProductLayout);
        recommendedProductLayout = findViewById(R.id.recommendedProductLayout);
        recommendedRecyclerView = findViewById(R.id.recommendedRecyclerView);
        otherProductRecyclerView = findViewById(R.id.otherProductRecyclerView);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // setup toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        GlobalClass.setToolbar(this, mToolbar, getString(R.string.replace_a_programmer));

        product_name = getIntent().getStringExtra("product_name");
        manufacturer_name = getIntent().getStringExtra("manufacturer_name");

        mDb = AppDatabase.getInstance(ReplacementProductList.this);

        setUpData();

        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(ReplacementProductList.this));
        otherProductRecyclerView.setLayoutManager(new LinearLayoutManager(ReplacementProductList.this));

        recommended_adapter = new ReplacementProductAdapter(ReplacementProductList.this, all_recommended_list, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getApplicationContext(), ReplacementProductDetails.class);
                intent.putExtra("replace_product_name", "" + recommended_list.get(position));
                intent.putExtra("manufacturer_name", manufacturer_name);
                intent.putExtra("product_name", product_name);
                intent.putExtra("product_type", "recommended");
                startActivity(intent);
            }
        });

        recommendedRecyclerView.setAdapter(recommended_adapter);

        other_product_adapter = new ReplacementProductAdapter(ReplacementProductList.this, all_other_product_list, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getApplicationContext(), ReplacementProductDetails.class);
                intent.putExtra("replace_product_name", "" + other_product_list.get(position));
                intent.putExtra("manufacturer_name", manufacturer_name);
                intent.putExtra("product_name", product_name);
                intent.putExtra("product_type", "other");
                startActivity(intent);
            }
        });

        otherProductRecyclerView.setAdapter(other_product_adapter);
    }


    void setUpData() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {

                    List<String> other_array_list = mDb.productDetailsDao().getAllCompatibleProduct(manufacturer_name, product_name);
                    if (other_array_list.size() == 0) {
                        otherProductLayout.setVisibility(View.GONE);
                    } else {

                        otherProductLayout.setVisibility(View.VISIBLE);

                        for (int k = 0; k < other_array_list.size(); k++) {

                            String value = other_array_list.get(k);

                            String data[] = value.split(",");

                            for (int i = 0; i < data.length; i++) {

                                if (data[i].length() != 0) {
                                    other_product_list.add(data[i]);
                                }
                            }
                        }
                    }

                    List<String> recommended_array_list = mDb.productDetailsDao().getAllRecommendedProduct(manufacturer_name, product_name);

                    if (recommended_array_list.size() == 0) {
                        recommendedProductLayout.setVisibility(View.GONE);
                    } else {
                        recommendedProductLayout.setVisibility(View.VISIBLE);

                        for (int k = 0; k < recommended_array_list.size(); k++) {

                            String value = recommended_array_list.get(k);
                            String data[] = value.split(",");

                            for (int i = 0; i < data.length; i++) {
                                if (data[i].length() != 0) {
                                    recommended_list.add(data[i]);
                                }
                            }
                        }
                    }


                    for (int i = 0; i < recommended_list.size(); i++) {
                        String details = mDb.productDetailsDao().getApplicationDetails(recommended_list.get(i).trim().toLowerCase());
                        String image = mDb.productDetailsDao().getProductImage(recommended_list.get(i).trim().toLowerCase());
                        ProductListGS product = new ProductListGS();
                        product.setName(recommended_list.get(i));
                        product.setApplication_details(details);
                        product.setImage(image);
                        all_recommended_list.add(product);
                    }


                    for (int i = 0; i < other_product_list.size(); i++) {
                        String details = mDb.productDetailsDao().getApplicationDetails(other_product_list.get(i).trim().toLowerCase());
                        String image = mDb.productDetailsDao().getProductImage(other_product_list.get(i).trim().toLowerCase());
                        ProductListGS product = new ProductListGS();
                        product.setName(other_product_list.get(i));
                        product.setApplication_details(details);
                        product.setImage(image);
                        all_other_product_list.add(product);
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recommended_adapter.notifyDataSetChanged();
                            other_product_adapter.notifyDataSetChanged();
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            GlobalClass.navigateToHomePage(ReplacementProductList.this);
        }
        return super.onOptionsItemSelected(item);
    }
}
