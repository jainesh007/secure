package com.secure.secure.installer.securelibary.database;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.baseClass.MainHomePage;
import com.secure.secure.installer.securelibary.baseClass.WebViewActivity;

public class GlobalClass {

    public static final String PUBLIC_IP_ADDRESS = "http://160.202.11.54:8080/installerapp/api/";

    public static final String GENERATE_JWT_TOKEN = PUBLIC_IP_ADDRESS + "managejwttoken";
    public static final String GET_INSTALLER_TABLE_DATA = PUBLIC_IP_ADDRESS + "dbinstaller/getinstallertabledata";
    public static final String GET_DB_VERSION = PUBLIC_IP_ADDRESS + "DBInstaller/GetDBVersion";

    public static final String DATABASE_NAME = "Installer";
    public static final String TABLE_NAME = "AllProductDetails";
    public static final String COMPANY_NAME = "Secure";
    public static final int API_DATA_SUCCESS_CODE = 202;

    public static final String ID = "id";
    public static final String ManufacturerName = "manufacturerName";
    public static final String ProductModelName = "productModelName";
    public static final String Pair1 = "pair1";
    public static final String Pair2 = "pair2";
    public static final String Pair3 = "pair3";
    public static final String Pair4 = "pair4";
    public static final String Pair5 = "pair5";
    public static final String Pair6 = "pair6";
    public static final String Pair7 = "pair7";
    public static final String Pair8 = "pair8";
    public static final String Pair9 = "pair9";
    public static final String Pair10 = "pair10";
    public static final String Pair11 = "pair11";
    public static final String Pair12 = "pair12";
    public static final String PairSpare = "spare";
    public static final String Image = "image";
    public static final String FAQ_URL = "faqurl";
    public static final String VIDEO_URL = "videoURL";
    public static final String OTHER_URL = "otherUrl";
    public static final String IS_Active = "isActive";
    public static final String Instructions = "instructions";
    public static final String ModelImagePath = "modelImagePath";
    public static final String ProductCategory = "productCategory";
    public static final String WiringAndBackPlate = "wiringAndBackPlate";
    public static final String ApplicationDetails = "applicationDetails";
    public static final String ProductSubCategory = "productSubCategory";
    public static final String RecommendProductName = "recommendProductName";
    public static final String IsCommissioningProduct = "isCommissioningProduct";
    public static final String OtherCompatibleProductName = "otherCompatibleProductName";
    public static final String RecommendProductInstructions = "recommendProductInstructions";

    public static final String Version = "version";
    public static final String IS_NEW_PRODUCT = "isNewProduct";
    public static final String SEARCH_COUNT = "search_count";


    public static void setToolbar(final Context mContext, Toolbar mToolbar, String title) {

        mToolbar.setTitle(title);
        ((AppCompatActivity) mContext).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back_arrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) mContext).finish();
            }
        });


    }

    public static void navigateToHomePage(Context mContext) {
        Intent intent = new Intent(mContext, MainHomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    public static void navigateToWebView(Context mContext) {

        if (isNetworkAvailable(mContext)) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra("web_link", "http://horstmann.securemeters.com/index.php/index.php/product/heating-and-hot-water-controls/timeswitches-programmers/channelplus-xl/");
            mContext.startActivity(intent);
            ((AppCompatActivity) mContext).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

        } else {
            showAlertDialogBox(mContext);
        }
    }


    public static SpannableStringBuilder setBulletData(Context context, String arr[]) {

        int bulletGap = (int) dp(context, 10);

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String line = arr[i].trim();
            SpannableString ss = new SpannableString(line);
            ss.setSpan(new BulletSpan(bulletGap), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(ss);

            if (i + 1 < arr.length)
                ssb.append("\n");
        }

        return ssb;

    }

    private static float dp(Context mContext, int dp) {
        return ((AppCompatActivity) mContext).getResources().getDisplayMetrics().density * dp;
    }


    public static void showAlertDialogBox(Context context) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Secure Installer");
        alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
