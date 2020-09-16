package com.secure.secure.installer.securelibary.replaceProgrammer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.database.AppDatabase;
import com.secure.secure.installer.securelibary.database.AppExecutors;
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.libary.AlphabetIndexFastScrollRecyclerView;
import com.secure.secure.installer.securelibary.model.Data;
import com.secure.secure.installer.securelibary.replaceProgrammer.adapter.ReplaceProgrammerProductListAlphabeticAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReplaceProgrammerManufacturerNameList extends AppCompatActivity {

    AlphabetIndexFastScrollRecyclerView recyclerView;
    EditText et_search;

    private ArrayList<Data> mProductList = new ArrayList<>();
    ArrayList<Data> productModels = new ArrayList<>();
    ReplaceProgrammerProductListAlphabeticAdapter adapter;
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_with_alphabatic_layout);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        recyclerView = findViewById(R.id.recyclerView);
        et_search = findViewById(R.id.et_search);

        // setup toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        GlobalClass.setToolbar(this, mToolbar, getString(R.string.replace_a_programmer));

        mDb = AppDatabase.getInstance(ReplaceProgrammerManufacturerNameList.this);

        setUpData();

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


    void filter(String text) {
        List<Data> temp = new ArrayList();
        for (Data d : mProductList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getTitle().toLowerCase().contains(text)) {
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

                    List<String> array_list = new ArrayList<String>();
                    array_list = mDb.productDetailsDao().getAllManufacturerName();

                    for (int i = 0; i < array_list.size(); i++) {
                        productModels.add(new Data(array_list.get(i), false));
                    }

                    getHeaderListLatter(productModels);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void setUpRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReplaceProgrammerProductListAlphabeticAdapter(this, mProductList);
        recyclerView.setAdapter(adapter);
        recyclerView.setIndexTextSize(12);
        recyclerView.setIndexBarTextColor("#000000");
        recyclerView.setIndexBarColor("#cdced2");
        recyclerView.setIndexbarHighLateTextColor("#FF4081");
        recyclerView.setIndexBarHighLateTextVisibility(true);
        recyclerView.setIndexBarTransparentValue((float) 1.0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            GlobalClass.navigateToHomePage(ReplaceProgrammerManufacturerNameList.this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getHeaderListLatter(ArrayList<Data> usersList) {

        Collections.sort(usersList, new Comparator<Data>() {
            @Override
            public int compare(Data user1, Data user2) {
                return String.valueOf(user1.getTitle().charAt(0)).toUpperCase().compareTo(String.valueOf(user2.getTitle().charAt(0)).toUpperCase());
            }
        });

        String lastHeader = "";

        int size = usersList.size();

        for (int i = 0; i < size; i++) {

            Data user = usersList.get(i);
            String header = String.valueOf(user.getTitle().charAt(0)).toUpperCase();

            if (!TextUtils.equals(lastHeader, header)) {
                lastHeader = header;
                mProductList.add(new Data(header, true));
            }

            mProductList.add(user);
        }

        setUpRecyclerView();
    }


}


