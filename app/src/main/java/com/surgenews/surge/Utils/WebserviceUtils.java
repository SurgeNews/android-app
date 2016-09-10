package com.surgenews.surge.Utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WebserviceUtils {

    //url
    public static final String BASE_URL = "http://whoismyrepresentative.com/";

    //endpoints
    public static final String ENDPOINT_GET_ALL_REPS_BY_STATE = "getall_reps_bystate.php";

    //query
    public static final String QUERY_STATE = "state";
    public static final String QUERY_OUTPUT = "output";


    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}
