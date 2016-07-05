package co.touchlab.android.threading.tasks.utils;
import android.content.Context;

/**
 * Created by kgalligan on 4/4/15.
 */
public class NetworkUtils
{
    //TODO: Actual reachability: https://realm.io/news/tmi-active-internet-connection-ios/
    public static native boolean isOnline(Context context)/*-[
         return true;
    ]-*/;
    /*{
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }*/
}
