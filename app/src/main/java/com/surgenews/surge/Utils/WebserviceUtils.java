package com.surgenews.surge.Utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WebserviceUtils {

    //url
    public static final String BASE_URL = "http://54.162.192.248:8080/";

    //endpoints
    public static final String ENDPOINT_SIGN_UP = "api/v1/user/signUp/";
    public static final String ENDPOINT_LOGIN = "api/v1/user/signIn/";


    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}
