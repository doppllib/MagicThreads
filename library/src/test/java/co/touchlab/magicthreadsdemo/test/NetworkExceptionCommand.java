package co.touchlab.magicthreadsdemo.test;

import android.content.Context;
import android.util.Log;

import co.touchlab.android.threading.errorcontrol.SoftException;
import co.touchlab.android.threading.tasks.persisted.PersistedTask;
import co.touchlab.android.threading.utils.UiThreadContext;

/**
 * Created by kgalligan on 10/4/14.
 */
public class NetworkExceptionCommand extends PersistedTask
{
    public String message;
    private boolean firstRun = true;

    @Override
    public void run(Context context) throws SoftException, Throwable
    {
        Log.w("queuetest", "Try and soft fail");
        if(firstRun)
        {
            firstRun = false;
            throw new SoftException("Network probs");
        }
    }

    @Override
    public void onTransientError(Context context, SoftException exception)
    {
        UiThreadContext.assertUiThread();
        Log.w("queuetest", "Transient error");
        message = exception.getMessage();
    }

    @Override
    public boolean handleError(Context context, Throwable exception)
    {
        return false;
    }
}
