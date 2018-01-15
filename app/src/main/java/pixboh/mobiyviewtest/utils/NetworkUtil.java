/*
 * Copyright (c) 2016. Dealers Solution.
 *  All Rights Reserved
 *
 *  This product is protected by copyright and distributed under
 *  licenses restricting copying, distribution and decompilation.
 */

package pixboh.mobiyviewtest.utils;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtil {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private String TAG = this.getClass().getSimpleName();

    public static String getConnectivityStatusString(Context context){
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI){
            status = "Wifi enabled";
        }else if (conn == NetworkUtil.TYPE_MOBILE){
            status = "Mobile data enabled";
        }else if (conn == NetworkUtil.TYPE_NOT_CONNECTED){
            status = "Not connected to Internet";
        }
        return status;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected() && activeNetwork.isAvailable())
        {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static boolean isDeviceOnline(String connectionStatus){
        if (connectionStatus.equals("Not connected to Internet")){
            Log.d("isDeviceOnline ?", "Device is offline");
            return false;
        }else{
            Log.d("isDeviceOnline ?", "Device is OnlineAuctions now");
            return true;
        }
    }

}
