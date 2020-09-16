package com.secure.secure.installer.securelibary.replaceProgrammer;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.database.AppDatabase;
import com.secure.secure.installer.securelibary.database.AppExecutors;
import com.secure.secure.installer.securelibary.database.GlobalClass;
import com.secure.secure.installer.securelibary.model.ProductTableData;

import java.util.ArrayList;
import java.util.List;

public class ReplacementProductDetails extends AppCompatActivity {

    ImageView productImage;
    TextView productName, selectedProductName, diagramProductName, instructions, replaceProgram, wiringBackplate;

    // for upper wiring diagram textview
    TextView txt_four, txt_five, txt_six, txt_seven, txt_eight, txt_nine, txt_ten, txt_eleven, txt_twelve, txt_spare, txt_neutral, txt_earth, txt_live;

    // for showing spare button
    TextView txt_4_spare, txt_5_spare, txt_6_spare, txt_7_spare, txt_8_spare, txt_9_spare, txt_10_spare, txt_11_spare, txt_12_spare, txt_last_spare;

    // for bottom wiring diagram
    TextView txt_pair4, txt_pair5, txt_pair6, txt_pair7, txt_pair8, txt_pair9, txt_pair10, txt_pair11, txt_pair12;

    // for upper wiring diagram circle Layout
    RelativeLayout circleOneLayout, circleTwoLayout, circleThreeLayout, circleFourLayout, circleFiveLayout, circleSixLayout, circleSevenLayout,
            circleEightLayout, circleNineLayout, circleTenLayout, circleElevenLayout, circleTwelveLayout, circleSpareLayout;

    // for draw line between upper and bottom wiring diagram
    RelativeLayout earthLine, neutralLine, liveLine, fourthLine, fifthLine, sixthLine, seventhLine, eightLine, nineLine, tenLine, elevenLine, twelveLine;

    // for bottom wiring diagram layout
    RelativeLayout bottom_pair_four_layout, bottom_pair_five_layout, bottom_pair_six_layout, bottom_pair_seven_layout, bottom_pair_eight_layout,
            bottom_pair_nine_layout, bottom_pair_ten_layout, bottom_Pair_eleven_Layout, bottom_Pair_twalve_Layout;


    String replace_product_name, product_name, manufacturer_name, product_type;
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replacement_product_details_layout);

        init();

        // setup toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        GlobalClass.setToolbar(this, mToolbar, getString(R.string.replace_a_programmer));

        mDb = AppDatabase.getInstance(ReplacementProductDetails.this);

        product_type = getIntent().getStringExtra("product_type");
        product_name = getIntent().getStringExtra("product_name");
        manufacturer_name = getIntent().getStringExtra("manufacturer_name");
        replace_product_name = getIntent().getStringExtra("replace_product_name");

        productName.setText(replace_product_name);
        diagramProductName.setText(replace_product_name);
        selectedProductName.setText(manufacturer_name + " " + product_name);

        replaceProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReplacementStartCommissioning.class);
                startActivity(intent);
            }
        });


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {

                    // get bottom wiring diagram data
                    List<ProductTableData> secure_product = mDb.productDetailsDao().getBottomWiringDiagram(replace_product_name.trim().toLowerCase());

                    if (secure_product.get(0).getPair4().trim().toLowerCase().equalsIgnoreCase("spare")) {
                        bottom_pair_four_layout.setVisibility(View.INVISIBLE);
                        fourthLine.setVisibility(View.INVISIBLE);
                    }

                    if (secure_product.get(0).getPair5().trim().toLowerCase().equalsIgnoreCase("spare")) {
                        bottom_pair_five_layout.setVisibility(View.INVISIBLE);
                        fifthLine.setVisibility(View.INVISIBLE);
                    }

                    if (secure_product.get(0).getPair6().trim().toLowerCase().equalsIgnoreCase("spare")) {
                        bottom_pair_six_layout.setVisibility(View.INVISIBLE);
                        sixthLine.setVisibility(View.INVISIBLE);
                    }

                    if (secure_product.get(0).getPair7().trim().toLowerCase().equalsIgnoreCase("spare")) {
                        bottom_pair_seven_layout.setVisibility(View.INVISIBLE);
                        seventhLine.setVisibility(View.INVISIBLE);
                    }

                    if (secure_product.get(0).getPair8().length() == 0 || secure_product.get(0).getPair8().trim().toLowerCase().equalsIgnoreCase("spare")) {
                        bottom_pair_eight_layout.setVisibility(View.INVISIBLE);
                        eightLine.setVisibility(View.INVISIBLE);
                    }

                    if (secure_product.get(0).getPair9().length() == 0 || secure_product.get(0).getPair9().trim().toLowerCase().equalsIgnoreCase("spare")) {
                        bottom_pair_nine_layout.setVisibility(View.INVISIBLE);
                        nineLine.setVisibility(View.INVISIBLE);
                    }

                   /* if (secure_product.get(0).getPair10().length() == 0 || secure_product.get(0).getPair10().trim().toLowerCase().equalsIgnoreCase("spare")) {
                        bottom_pair_ten_layout.setVisibility(View.INVISIBLE);
                        tenLine.setVisibility(View.INVISIBLE);
                    }

                    if (secure_product.get(0).getPair11().length() == 0 || secure_product.get(0).getPair11().trim().toLowerCase().equalsIgnoreCase("spare")) {
                        bottom_Pair_eleven_Layout.setVisibility(View.INVISIBLE);
                        elevenLine.setVisibility(View.INVISIBLE);
                    }

                    if (secure_product.get(0).getPair12().length() == 0 || secure_product.get(0).getPair12().trim().toLowerCase().equalsIgnoreCase("spare")) {
                        bottom_Pair_twalve_Layout.setVisibility(View.INVISIBLE);
                        twelveLine.setVisibility(View.INVISIBLE);
                    }*/


                    txt_pair4.setText(secure_product.get(0).getPair4());
                    txt_pair5.setText(secure_product.get(0).getPair5());
                    txt_pair6.setText(secure_product.get(0).getPair6());
                    txt_pair7.setText(secure_product.get(0).getPair7());
                    txt_pair8.setText(secure_product.get(0).getPair8());
                    txt_pair9.setText(secure_product.get(0).getPair9());
                    txt_pair10.setText(secure_product.get(0).getPair10());
                    txt_pair11.setText(secure_product.get(0).getPair11());
                    txt_pair12.setText(secure_product.get(0).getPair12());


                    instructions.setText(GlobalClass.setBulletData(ReplacementProductDetails.this, secure_product.get(0).getInstructions().split("\\|")));

                    List<ProductTableData> other_product_list = new ArrayList<>();

                    if (product_type.equalsIgnoreCase("recommended")) {
                        // RecommendProductNameWiringDiagram
                        other_product_list = mDb.productDetailsDao().getUpperRecommendProductNameWiringDiagram(manufacturer_name.toLowerCase(), product_name.trim().toLowerCase(), replace_product_name.trim().toLowerCase());
                    } else {
                        // OtherCompatibleWiringDiagram
                        other_product_list = mDb.productDetailsDao().getUpperOtherCompatibleWiringDiagram(manufacturer_name.toLowerCase(), product_name.trim().toLowerCase(), replace_product_name.trim().toLowerCase());
                    }

                    wiringBackplate.setText(other_product_list.get(0).getWiringAndBackPlate());

                    // for earth layout
                    if (other_product_list.get(0).getPair1().length() != 0) {
                        circleOneLayout.setVisibility(View.VISIBLE);
                        earthLine.setVisibility(View.VISIBLE);
                        txt_earth.setText(other_product_list.get(0).getPair1());
                    } else {
                        circleOneLayout.setVisibility(View.INVISIBLE);
                        earthLine.setVisibility(View.INVISIBLE);
                    }

                    // for Neutral layout
                    if (other_product_list.get(0).getPair2().length() != 0) {
                        circleTwoLayout.setVisibility(View.VISIBLE);
                        neutralLine.setVisibility(View.VISIBLE);
                        txt_neutral.setText(other_product_list.get(0).getPair2());
                    } else {
                        circleTwoLayout.setVisibility(View.INVISIBLE);
                        neutralLine.setVisibility(View.INVISIBLE);

                    }

                    // for Live layout
                    if (other_product_list.get(0).getPair3().length() != 0) {
                        circleThreeLayout.setVisibility(View.VISIBLE);
                        liveLine.setVisibility(View.VISIBLE);
                        txt_live.setText(other_product_list.get(0).getPair3());
                    } else {
                        circleThreeLayout.setVisibility(View.INVISIBLE);
                        liveLine.setVisibility(View.INVISIBLE);
                    }

                    // for fourth layout
                    if (other_product_list.get(0).getPair4().length() != 0) {

                        if (!secure_product.get(0).getPair4().toLowerCase().equalsIgnoreCase("spare")) {
                            fourthLine.setVisibility(View.VISIBLE);
                        } else {
                            txt_4_spare.setVisibility(View.VISIBLE);
                        }

                        circleFourLayout.setVisibility(View.VISIBLE);
                        txt_four.setText(other_product_list.get(0).getPair4());

                    } else {
                        circleFourLayout.setVisibility(View.INVISIBLE);
                        fourthLine.setVisibility(View.INVISIBLE);
                    }

                    // for fifth layout
                    if (other_product_list.get(0).getPair5().length() != 0) {

                        if (!secure_product.get(0).getPair5().toLowerCase().equalsIgnoreCase("spare")) {
                            fifthLine.setVisibility(View.VISIBLE);
                        } else {
                            txt_5_spare.setVisibility(View.VISIBLE);
                        }

                        circleFiveLayout.setVisibility(View.VISIBLE);
                        txt_five.setText(other_product_list.get(0).getPair5());

                    } else {
                        circleFiveLayout.setVisibility(View.INVISIBLE);
                        fifthLine.setVisibility(View.INVISIBLE);
                    }

                    // for sixth layout
                    if (other_product_list.get(0).getPair6().length() != 0) {

                        if (!secure_product.get(0).getPair6().toLowerCase().equalsIgnoreCase("spare")) {
                            sixthLine.setVisibility(View.VISIBLE);
                        } else {
                            txt_6_spare.setVisibility(View.VISIBLE);
                        }

                        circleSixLayout.setVisibility(View.VISIBLE);
                        txt_six.setText(other_product_list.get(0).getPair6());

                    } else {
                        circleSixLayout.setVisibility(View.INVISIBLE);
                        sixthLine.setVisibility(View.INVISIBLE);
                    }

                    // for seven layout
                    if (other_product_list.get(0).getPair7().length() != 0) {

                        if (!secure_product.get(0).getPair7().toLowerCase().equalsIgnoreCase("spare")) {
                            seventhLine.setVisibility(View.VISIBLE);
                        } else {
                            txt_7_spare.setVisibility(View.VISIBLE);
                        }

                        circleSevenLayout.setVisibility(View.VISIBLE);
                        txt_seven.setText(other_product_list.get(0).getPair7());
                    } else {
                        circleSevenLayout.setVisibility(View.INVISIBLE);
                        seventhLine.setVisibility(View.INVISIBLE);
                    }

                    // for eight layout
                    if (other_product_list.get(0).getPair8().length() != 0) {

                        circleEightLayout.setVisibility(View.VISIBLE);
                        txt_eight.setText(other_product_list.get(0).getPair8());

                        if (!secure_product.get(0).getPair8().toLowerCase().equalsIgnoreCase("spare")) {
                            eightLine.setVisibility(View.VISIBLE);
                        } else {
                            txt_8_spare.setVisibility(View.VISIBLE);
                        }

                    } else {
                        circleEightLayout.setVisibility(View.INVISIBLE);
                        eightLine.setVisibility(View.INVISIBLE);
                    }

                    // for nine layout
                    if (other_product_list.get(0).getPair9().length() != 0) {

                        circleNineLayout.setVisibility(View.VISIBLE);
                        txt_nine.setText(other_product_list.get(0).getPair9());

                        if (!secure_product.get(0).getPair9().toLowerCase().equalsIgnoreCase("spare")) {
                            nineLine.setVisibility(View.VISIBLE);
                        } else {
                            txt_9_spare.setVisibility(View.VISIBLE);
                        }

                    } else {
                        circleNineLayout.setVisibility(View.INVISIBLE);
                        nineLine.setVisibility(View.INVISIBLE);
                    }


                  /*  // for ten layout
                    if (other_product_list.get(0).getPair10().length() != 0) {

                        circleTenLayout.setVisibility(View.VISIBLE);
                        txt_ten.setText(other_product_list.get(0).getPair10());

                        if (!secure_product.get(0).getPair10().toLowerCase().equalsIgnoreCase("spare")) {
                            tenLine.setVisibility(View.VISIBLE);
                        } else {
                            txt_10_spare.setVisibility(View.VISIBLE);
                        }

                    } else {
                        circleTenLayout.setVisibility(View.INVISIBLE);
                        tenLine.setVisibility(View.INVISIBLE);
                    }


                    // for Eleven layout
                    if (other_product_list.get(0).getPair11().length() != 0) {

                        circleElevenLayout.setVisibility(View.VISIBLE);
                        txt_eleven.setText(other_product_list.get(0).getPair11());

                        if (!secure_product.get(0).getPair11().toLowerCase().equalsIgnoreCase("spare")) {
                            elevenLine.setVisibility(View.VISIBLE);
                        } else {
                            txt_11_spare.setVisibility(View.VISIBLE);
                        }

                    } else {
                        circleElevenLayout.setVisibility(View.INVISIBLE);
                        elevenLine.setVisibility(View.INVISIBLE);
                    }


                    // for Eleven layout
                    if (other_product_list.get(0).getPair12().length() != 0) {

                        circleTwelveLayout.setVisibility(View.VISIBLE);
                        txt_eleven.setText(other_product_list.get(0).getPair12());

                        if (!secure_product.get(0).getPair12().toLowerCase().equalsIgnoreCase("spare")) {
                            twelveLine.setVisibility(View.VISIBLE);
                        } else {
                            txt_12_spare.setVisibility(View.VISIBLE);
                        }

                    } else {
                        circleTwelveLayout.setVisibility(View.INVISIBLE);
                        twelveLine.setVisibility(View.INVISIBLE);
                    }*/


                    // for spare layout
                    if (other_product_list.get(0).getPairSpare().length() != 0) {
                        circleSpareLayout.setVisibility(View.VISIBLE);
                        txt_spare.setText(other_product_list.get(0).getPairSpare());
                        txt_last_spare.setVisibility(View.VISIBLE);

                    } else {
                        circleSpareLayout.setVisibility(View.INVISIBLE);
                    }

                    String image = mDb.productDetailsDao().getProductImage(replace_product_name);

                    byte[] imageAsBytes = Base64.decode(image, Base64.DEFAULT);
                    productImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

                } catch (Exception e) {
                    e.printStackTrace();
                    // productImage.setImageDrawable(getResources().getDrawable(R.drawable.no_image_found));
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
            GlobalClass.navigateToHomePage(ReplacementProductDetails.this);
        }
        return super.onOptionsItemSelected(item);
    }


    private void init() {
        wiringBackplate = findViewById(R.id.wiringBackplate);
        replaceProgram = findViewById(R.id.replaceProgram);
        instructions = findViewById(R.id.instructions);
        diagramProductName = findViewById(R.id.diagramProductName);
        selectedProductName = findViewById(R.id.selectedProductName);
        productName = findViewById(R.id.productName);
        productImage = findViewById(R.id.productImage);

        // for showing spare button
        txt_4_spare = findViewById(R.id.txt_4_spare);
        txt_5_spare = findViewById(R.id.txt_5_spare);
        txt_6_spare = findViewById(R.id.txt_6_spare);
        txt_7_spare = findViewById(R.id.txt_7_spare);
        txt_8_spare = findViewById(R.id.txt_8_spare);
        txt_9_spare = findViewById(R.id.txt_9_spare);
        txt_10_spare = findViewById(R.id.txt_10_spare);
        txt_11_spare = findViewById(R.id.txt_11_spare);
        txt_12_spare = findViewById(R.id.txt_12_spare);
        txt_last_spare = findViewById(R.id.txt_last_spare);

        // for draw line between upper and bottom wiring diagram
        earthLine = findViewById(R.id.earthLine);
        neutralLine = findViewById(R.id.neutralLine);
        liveLine = findViewById(R.id.liveLine);
        fourthLine = findViewById(R.id.fourthLine);
        fifthLine = findViewById(R.id.fifthLine);
        sixthLine = findViewById(R.id.sixthLine);
        seventhLine = findViewById(R.id.seventhLine);
        eightLine = findViewById(R.id.eightLine);
        nineLine = findViewById(R.id.nineLine);
        tenLine = findViewById(R.id.tenLine);
        elevenLine = findViewById(R.id.elevenLine);
        twelveLine = findViewById(R.id.twelveLine);

        // for upper wiring diagram textview
        txt_earth = findViewById(R.id.txt_earth);
        txt_live = findViewById(R.id.txt_live);
        txt_neutral = findViewById(R.id.txt_neutral);
        txt_four = findViewById(R.id.txt_four);
        txt_five = findViewById(R.id.txt_five);
        txt_six = findViewById(R.id.txt_six);
        txt_seven = findViewById(R.id.txt_seven);
        txt_eight = findViewById(R.id.txt_eight);
        txt_nine = findViewById(R.id.txt_nine);
        txt_ten = findViewById(R.id.txt_ten);
        txt_eleven = findViewById(R.id.txt_eleven);
        txt_twelve = findViewById(R.id.txt_twelve);
        txt_spare = findViewById(R.id.txt_spare);

        // for bottom wiring diagram
        txt_pair4 = findViewById(R.id.txt_pair4);
        txt_pair5 = findViewById(R.id.txt_pair5);
        txt_pair6 = findViewById(R.id.txt_pair6);
        txt_pair7 = findViewById(R.id.txt_pair7);
        txt_pair8 = findViewById(R.id.txt_pair8);
        txt_pair9 = findViewById(R.id.txt_pair9);
        txt_pair10 = findViewById(R.id.txt_pair10);
        txt_pair11 = findViewById(R.id.txt_pair11);
        txt_pair12 = findViewById(R.id.txt_pair12);

        // for upper wiring diagram circle Layout
        circleOneLayout = findViewById(R.id.circleOneLayout);
        circleTwoLayout = findViewById(R.id.circleTwoLayout);
        circleThreeLayout = findViewById(R.id.circleThreeLayout);
        circleFourLayout = findViewById(R.id.circleFourLayout);
        circleFiveLayout = findViewById(R.id.circleFiveLayout);
        circleSixLayout = findViewById(R.id.circleSixLayout);
        circleSevenLayout = findViewById(R.id.circleSevenLayout);
        circleEightLayout = findViewById(R.id.circleEightLayout);
        circleNineLayout = findViewById(R.id.circleNineLayout);
        circleTenLayout = findViewById(R.id.circleTenLayout);
        circleElevenLayout = findViewById(R.id.circleElevenLayout);
        circleTwelveLayout = findViewById(R.id.circleTwelveLayout);
        circleSpareLayout = findViewById(R.id.circleSpareLayout);

        // for bottom wiring diagram layout
        bottom_pair_four_layout = findViewById(R.id.bottom_Pair_four_Layout);
        bottom_pair_five_layout = findViewById(R.id.bottom_Pair_five_Layout);
        bottom_pair_six_layout = findViewById(R.id.bottom_Pair_six_Layout);
        bottom_pair_seven_layout = findViewById(R.id.bottom_Pair_seven_Layout);
        bottom_pair_eight_layout = findViewById(R.id.bottom_Pair_eight_Layout);
        bottom_pair_nine_layout = findViewById(R.id.bottom_Pair_nine_Layout);
        bottom_pair_ten_layout = findViewById(R.id.bottom_Pair_ten_Layout);
        bottom_Pair_eleven_Layout = findViewById(R.id.bottom_Pair_eleven_Layout);
        bottom_Pair_twalve_Layout = findViewById(R.id.bottom_Pair_twalve_Layout);
    }
}
