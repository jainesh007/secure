package com.secure.secure.installer.securelibary;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

public class SecureInstallerApplication extends Application {

    private static SecureInstallerApplication mInstance;
    public static final String TAG = SecureInstallerApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized SecureInstallerApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {

            DefaultHttpClient httpclient = new DefaultHttpClient();

            CookieStore cookieStore = new BasicCookieStore();
            httpclient.setCookieStore(cookieStore);

            HttpStack httpStack = new HttpClientStack(httpclient);
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), httpStack);
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default_ring tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
}
