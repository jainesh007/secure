package com.secure.secure.installer.securelibary.baseClass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.SecureInstallerApplication;
import com.secure.secure.installer.securelibary.database.AppDatabase;
import com.secure.secure.installer.securelibary.database.AppExecutors;
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.installNewProduct.InstallNewProduct;
import com.secure.secure.installer.securelibary.model.DataBaseModelResponse;
import com.secure.secure.installer.securelibary.model.DataBaseResponse;
import com.secure.secure.installer.securelibary.model.ProductTableData;
import com.secure.secure.installer.securelibary.productGuide.ProductGuide;
import com.secure.secure.installer.securelibary.replaceProgrammer.ReplaceProgrammerManufacturerNameList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainHomePage extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    TextView product_guide, installProduct, replaceProgram, drawerIconClose, quick_pair, add_remove_device;

    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog2;

    private AppDatabase mDb;
    private DataBaseResponse responseObject;
    private List<DataBaseModelResponse> product_list = new ArrayList<>();
    private int db_version = 0;


    private void init() {

        product_guide = findViewById(R.id.product_guide);
        installProduct = findViewById(R.id.installProduct);
        replaceProgram = findViewById(R.id.replaceProgram);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        drawerIconClose = findViewById(R.id.drawerIconClose);
        quick_pair = findViewById(R.id.quick_pair);
        add_remove_device = findViewById(R.id.add_remove_device);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home_page_layout);

        // set up the toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.secure_controls);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.baseline_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        mDb = AppDatabase.getInstance(MainHomePage.this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);

        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
        progressDialog2.setCancelable(false);
        progressDialog2.setIndeterminate(false);

        init();

        // click listener
        product_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProductGuide.class));
            }
        });


        installProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InstallNewProduct.class));
            }
        });


        replaceProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReplaceProgrammerManufacturerNameList.class));
            }
        });


        drawerIconClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        insertDataToDatabase();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("databasefile.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void insertDataToDatabase() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                try {

                    int size = mDb.productDetailsDao().getCount();

                    if (size == 0) {

                        showDialog("Please wait...");

                        JSONObject obj = new JSONObject(loadJSONFromAsset());
                        JSONArray jsonArray = obj.getJSONArray("responseObj");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject json_inside = jsonArray.getJSONObject(i);

                            ProductTableData product = new ProductTableData();

                            product.setId(json_inside.getInt("id"));
                            product.setManufacturerName(json_inside.getString("manufacturerName"));
                            product.setProductModelName(json_inside.getString("productModelName"));
                            product.setOtherCompatibleProductName(json_inside.getString("otherCompatibleProductName"));
                            product.setRecommendProductName(json_inside.getString("recommendProductName"));
                            product.setPair1(json_inside.getString("pair1"));
                            product.setPair2(json_inside.getString("pair2"));
                            product.setPair3(json_inside.getString("pair3"));
                            product.setPair4(json_inside.getString("pair4"));
                            product.setPair5(json_inside.getString("pair5"));
                            product.setPair6(json_inside.getString("pair6"));
                            product.setPair7(json_inside.getString("pair7"));
                            product.setPair8(json_inside.getString("pair8"));
                            product.setPair9(json_inside.getString("pair9"));
                            product.setPair10(json_inside.getString("pair10"));
                            product.setPair11(json_inside.getString("pair11"));
                            product.setPair12(json_inside.getString("pair12"));
                            product.setPairSpare(json_inside.getString("spare_Pin"));
                            product.setWiringAndBackPlate(json_inside.getString("wiringAndBackPlate"));
                            product.setInstructions(json_inside.getString("instructions"));
                            product.setApplicationDetails(json_inside.getString("applicationDetails"));
                            product.setIsCommissioningProduct(json_inside.getString("isCommissioningProduct"));
                            product.setVersion(json_inside.getInt("version"));
                            product.setIs_new_product(json_inside.getString("isNewInstall"));
                            product.setImage(json_inside.getString("image"));
                            product.setSearch_count(0);

                            mDb.productDetailsDao().InsertProduct(product);

                            Log.d("+++>>>", "onClick: data inserted");
                        }

                        dismissDialog();
                    }

                    db_version = mDb.productDetailsDao().getVersion();
                    if (GlobalClass.isNetworkAvailable(MainHomePage.this)) {
                        generateJwtToken(db_version);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void generateJwtToken(int version) {

        showDialog("Checking version...");

        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST, GlobalClass.GENERATE_JWT_TOKEN, response -> {

            try {

                JSONObject jObj = new JSONObject(response);
                String token = jObj.getString("token");
                getDataBaseVersion(token, version);

            } catch (Exception e) {
                e.printStackTrace();
                dismissDialog();
            }

        }, error -> dismissDialog()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }

        };

        SecureInstallerApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void getDataBaseVersion(final String token, int version) {

        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.GET, GlobalClass.GET_DB_VERSION, response -> {

            try {

                JSONObject jObj = new JSONObject(response);
                int db_version = jObj.getInt("dbTableVersion");

                Log.d("===>>>>", "run: 1 " + version);
                Log.d("===>>>>", "run: 2 " + db_version);


                if (version != db_version) {
                    dismissDialog();
                    getDataFormDataBase(token);
                } else {
                    dismissDialog();
                }


            } catch (Exception e) {
                e.printStackTrace();
                dismissDialog();
            }

        }, error -> dismissDialog()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        SecureInstallerApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void getDataFormDataBase(String token) {

        showDialog2("Updating database...");
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.GET, GlobalClass.GET_INSTALLER_TABLE_DATA, response -> {

            try {

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            JSONObject jObj = new JSONObject(response);
                            int responseCode = jObj.getInt("responseCode");

                            if (responseCode == GlobalClass.API_DATA_SUCCESS_CODE) {

                                progressDialog.dismiss();
                                Gson gson = new Gson();
                                responseObject = gson.fromJson(response, DataBaseResponse.class);
                                product_list = responseObject.getResponse();
                            }


                            mDb.productDetailsDao().deleteAllData();

                            for (int i = 0; i < product_list.size(); i++) {

                                ProductTableData product = new ProductTableData();

                                product.setId(product_list.get(i).getId());
                                product.setManufacturerName(product_list.get(i).getManufacturerName());
                                product.setProductModelName(product_list.get(i).getProductModelName());
                                product.setOtherCompatibleProductName(product_list.get(i).getOtherCompatibleProductName());
                                product.setRecommendProductName(product_list.get(i).getRecommendProductName());
                                product.setImage(product_list.get(i).getImage());

                                product.setPair1(product_list.get(i).getPair1());
                                product.setPair2(product_list.get(i).getPair2());
                                product.setPair3(product_list.get(i).getPair3());
                                product.setPair4(product_list.get(i).getPair4());
                                product.setPair5(product_list.get(i).getPair5());
                                product.setPair6(product_list.get(i).getPair6());
                                product.setPair7(product_list.get(i).getPair7());
                                product.setPair8(product_list.get(i).getPair8());
                                product.setPair9(product_list.get(i).getPair9());
                                product.setPair10(product_list.get(i).getPair10());
                                product.setPair11(product_list.get(i).getPair11());
                                product.setPair12(product_list.get(i).getPair12());
                                product.setPairSpare(product_list.get(i).getPairSpare());

                                product.setWiringAndBackPlate(product_list.get(i).getWiringAndBackPlate());
                                product.setInstructions(product_list.get(i).getInstructions());
                                product.setApplicationDetails(product_list.get(i).getApplicationDetails());
                                product.setIsCommissioningProduct(product_list.get(i).getIsCommissioningProduct());
                                product.setVersion(product_list.get(i).getVersion());
                                product.setIs_new_product(product_list.get(i).getIsNewProduct());

                                mDb.productDetailsDao().InsertProduct(product);

                                Log.d("+++>>>", "onClick: data inserted");
                            }

                            dismissDialog2();

                            Log.d("+++>>>", "" + product_list.size());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
                dismissDialog();
            }

        }, error -> dismissDialog()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        SecureInstallerApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    void showDialog(String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                progressDialog.show();
                progressDialog.setMessage(message);
            }
        });
    }

    void showDialog2(String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                progressDialog2.show();
                progressDialog2.setMessage(message);
            }
        });
    }

    void dismissDialog2() {
        runOnUiThread(new Runnable() {
            public void run() {
                progressDialog2.dismiss();
            }
        });
    }

    void dismissDialog() {
        runOnUiThread(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        });
    }
}
