package co.touchlab.magicthreadsdemo.test.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by kgalligan on 10/5/14.
 */
public class ThreadHelper
{
    public static void sleep(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
            //
        }
    }

    public synchronized static Handler mainHandler()
    {
        if(Looper.getMainLooper() == null)
            Looper.prepareMainLooper();

        return new Handler(Looper.getMainLooper());
    }
}
