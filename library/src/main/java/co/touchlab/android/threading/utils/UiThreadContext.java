package co.touchlab.android.threading.utils;

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
            nativeThrow("This call must be in UI thread");
        }
    }

    public static native boolean isInUiThread()/*-[
        return [NSThread isMainThread];
     ]-*/;

    private static native void nativeThrow(String message)/*-[
        NSException* myException = [NSException
                                    exceptionWithName:@"AssertException"
                                    reason:message
                                    userInfo:nil];
        @throw myException;
  ]-*/;

    /**
     * Checks if you're not in the UI thread.
     */
    public static void assertBackgroundThread()
    {
        if(isInUiThread())
        {
            nativeThrow("This call must be in background thread");
        }
    }
}