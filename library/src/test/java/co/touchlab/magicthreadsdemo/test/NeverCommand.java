package co.touchlab.magicthreadsdemo.test;

import android.content.Context;
import android.util.Log;

import co.touchlab.android.threading.errorcontrol.SoftException;
import co.touchlab.android.threading.tasks.persisted.PersistedTask;

/**
 * Created by kgalligan on 10/4/14.
 */
public class NeverCommand extends PersistedTask
{
    @Override
    public void run(Context context) throws SoftException, Throwable
    {
        Log.w("queuetest", "Never get here");
    }

    @Override
    public boolean handleError(Context context, Throwable exception)
    {
        return false;
    }
}
