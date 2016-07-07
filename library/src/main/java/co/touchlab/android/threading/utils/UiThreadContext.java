package co.touchlab.android.threading.utils;

import android.os.Looper;

/**
 * Checks current thread, and throws a RuntimeException if you're not where you're supposed to
 * be.
 * <p/>
 * Created by kgalligan on 7/6/14.
 */
public class UiThreadContext
{
    /**
     * Checks if you're in the UI thread.
     */
    public static void assertUiThread()
    {
        if(! isInUiThread())
        {
            throw new RuntimeException("This call must be in UI thread");
        }
    }

    public static boolean isInUiThread()
    {
        Looper mainLooper = Looper.getMainLooper();
        if(mainLooper == null)
            throw new RuntimeException("Main looper not initialized");

        Thread uiThread = mainLooper.getThread();
        Thread currentThread = Thread.currentThread();

        return uiThread == currentThread;
    }

    /**
     * Checks if you're not in the UI thread.
     */
    public static void assertBackgroundThread()
    {
        if(isInUiThread())
        {
            throw new RuntimeException("This call must be in background thread");
        }
    }

    /**
     * This is obscure. Really just for testing to counterintuitively figure out if we're in UI test
     * or command line. Probably a better way to do this.
     * @return
     */
    public static native boolean isInIosUiThread()/*-[
        return [NSThread isMainThread];
     ]-*/;
}