package com.secure.secure.installer.securelibary;

import android.content.Context;
import android.content.Intent;

import com.secure.secure.installer.securelibary.baseClass.MainHomePage;

public class OpenSecureSDK {

    public static void goToMainHomePage(Context context) {
        context.startActivity(new Intent(context, MainHomePage.class));
    }


}
