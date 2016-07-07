package co.touchlab.magicthreadsdemo.test;

import android.content.Context;
import android.util.Log;

import co.touchlab.android.threading.errorcontrol.SoftException;
import co.touchlab.android.threading.tasks.persisted.PersistedTask;

/**
 * Created by kgalligan on 10/4/14.
 */
public class TestCommand extends PersistedTask
{
    @Override
    public boolean handleError(Context context, Throwable exception)
    {
        return false;
    }

    @Override
    public void run(Context context) throws SoftException, Throwable
    {
        Log.w("queuetest", "start");
        Thread.sleep(1000);
        Log.w("queuetest", "end");
    }
}
