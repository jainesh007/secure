package com.secure.secure.installer.securelibary.installNewProduct;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.database.AppDatabase;
import com.secure.secure.installer.securelibary.database.AppExecutors;
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.model.ProductTableData;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView productImage;
    TextView productName, redirectLink, instructions, wiringBackplate, product_name;

    AppDatabase mDb;
    private List<ProductTableData> product_list = new ArrayList<>();

    RelativeLayout bottom_Pair_ten_Layout, bottom_pair_nine_layout, bottom_pair_eight_layout, bottom_pair_seven_layout, bottom_pair_six_layout, bottom_pair_five_layout, bottom_pair_four_layout;
    TextView txt_pair4, txt_pair5, txt_pair6, txt_pair7, txt_pair8, txt_pair9, txt_pair10;

    String select_product_name;

    private void init() {

        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        product_name = findViewById(R.id.product_name);
        redirectLink = findViewById(R.id.redirectLink);
        instructions = findViewById(R.id.instructions);
        wiringBackplate = findViewById(R.id.wiringBackplate);

        bottom_Pair_ten_Layout = findViewById(R.id.bottom_Pair_ten_Layout);
        bottom_pair_nine_layout = findViewById(R.id.bottom_Pair_nine_Layout);
        bottom_pair_eight_layout = findViewById(R.id.bottom_Pair_eight_Layout);
        bottom_pair_seven_layout = findViewById(R.id.bottom_Pair_seven_Layout);
        bottom_pair_six_layout = findViewById(R.id.bottom_Pair_six_Layout);
        bottom_pair_five_layout = findViewById(R.id.bottom_Pair_five_Layout);
        bottom_pair_four_layout = findViewById(R.id.bottom_Pair_four_Layout);

        txt_pair4 = findViewById(R.id.txt_pair4);
        txt_pair5 = findViewById(R.id.txt_pair5);
        txt_pair6 = findViewById(R.id.txt_pair6);
        txt_pair7 = findViewById(R.id.txt_pair7);
        txt_pair8 = findViewById(R.id.txt_pair8);
        txt_pair9 = findViewById(R.id.txt_pair9);
        txt_pair10 = findViewById(R.id.txt_pair10);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_layout);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        init();

        // setup toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        GlobalClass.setToolbar(this, mToolbar, getString(R.string.new_product_installation));

        mDb = AppDatabase.getInstance(ProductDetailsActivity.this);

        select_product_name = getIntent().getStringExtra("product_name").trim().toLowerCase();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                try {

                    product_list = mDb.productDetailsDao().getSecureProduct(select_product_name);

                    instructions.setText(GlobalClass.setBulletData(ProductDetailsActivity.this, product_list.get(0).getInstructions().split("\\|")));

                    SpannableString content = new SpannableString("Install " + product_list.get(0).getProductModelName());
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    redirectLink.setText(content);

                    productName.setText(product_list.get(0).getProductModelName());
                    product_name.setText("Secure " + product_list.get(0).getProductModelName());
                    wiringBackplate.setText(product_list.get(0).getWiringAndBackPlate());

                    byte[] imageAsBytes = Base64.decode(product_list.get(0).getImage(), Base64.DEFAULT);
                    productImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

                    txt_pair4.setText(product_list.get(0).getPair4());
                    txt_pair5.setText(product_list.get(0).getPair5());
                    txt_pair6.setText(product_list.get(0).getPair6());
                    txt_pair7.setText(product_list.get(0).getPair7());
                    txt_pair8.setText(product_list.get(0).getPair8());
                    txt_pair9.setText(product_list.get(0).getPair9());
                    txt_pair10.setText(product_list.get(0).getPair10());

                    if (product_list.get(0).getPair4().length() == 0) {
                        bottom_pair_four_layout.setVisibility(View.INVISIBLE);
                    }

                    if (product_list.get(0).getPair5().length() == 0) {
                        bottom_pair_five_layout.setVisibility(View.INVISIBLE);
                    }

                    if (product_list.get(0).getPair6().length() == 0) {
                        bottom_pair_six_layout.setVisibility(View.INVISIBLE);
                    }

                    if (product_list.get(0).getPair7().length() == 0) {
                        bottom_pair_seven_layout.setVisibility(View.INVISIBLE);
                    }

                    if (product_list.get(0).getPair8().length() == 0) {
                        bottom_pair_eight_layout.setVisibility(View.INVISIBLE);
                    }

                    if (product_list.get(0).getPair9().length() == 0) {
                        bottom_pair_nine_layout.setVisibility(View.INVISIBLE);
                    }

                    if (product_list.get(0).getPair10().length() == 0) {
                        bottom_Pair_ten_Layout.setVisibility(View.INVISIBLE);
                    }


                    int count = mDb.productDetailsDao().getSearchCount(select_product_name);
                    count++;
                    Log.d("+++>>>", "run: " + count);
                    mDb.productDetailsDao().updateSearchCount(count, select_product_name);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        redirectLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalClass.navigateToWebView(ProductDetailsActivity.this);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            GlobalClass.navigateToHomePage(ProductDetailsActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }
}
