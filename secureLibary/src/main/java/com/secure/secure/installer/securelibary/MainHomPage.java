package com.secure.secure.installer.securelibary;

import android.content.Context;
import android.content.Intent;

public class MainHomPage {

    public static int plus(int i, int z) {
        return i + z;
    }


    public static void goToMainHomePage(Context context) {
        context.startActivity(new Intent(context, ReplaceActivity.class));
    }


}
