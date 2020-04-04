//Referance : https://github.com/flutter/plugins/pull/1822/files/3f68aa79d1f92d6a8e095a3fe6d03c70f301f95a
//The method available in the android developers is deprecated
package github.jeethjj.translatro;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

public class ConnectivityCheck {

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean con = false;
        try {
            Network network = cm.getActiveNetwork();
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
            if (capabilities == null) {
                con = false;
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                con = true;
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                con = true;
            }
        }catch (Exception e){
            con = false;
        }
        return con;
    }
}
