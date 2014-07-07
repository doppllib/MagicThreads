package co.touchlab.android.threading.utils;

import android.os.Looper;

/**
 * Checks current thread, and throws a RuntimeException if you're not where you're supposed to
 * be.
 *
 * Created by kgalligan on 7/6/14.
 */
public class UiThreadContext
{
    /**
     * Checks if you're in the UI thread.
     */
    public static void assertUiThread()
    {
        Thread uiThread = Looper.getMainLooper().getThread();
        Thread currentThread = Thread.currentThread();

        if(uiThread != currentThread)
            throw new RuntimeException("This call must be in UI thread");
    }

    /**
     * Checks if you're not in the UI thread.
     */
    public static void assertBackgroundThread()
    {
        Thread uiThread = null;
        Thread currentThread = null;
        try
        {
            uiThread = Looper.getMainLooper().getThread();
            currentThread = Thread.currentThread();
        }
        catch (Exception e)
        {
            //Probably in unit tests
            return;
        }

        if(uiThread == currentThread)
            throw new RuntimeException("This call must be in background thread");
    }
}