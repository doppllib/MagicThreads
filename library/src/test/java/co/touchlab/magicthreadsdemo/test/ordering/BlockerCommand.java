package co.touchlab.magicthreadsdemo.test.ordering;

import android.content.Context;

import co.touchlab.android.threading.errorcontrol.SoftException;
import co.touchlab.android.threading.tasks.persisted.PersistedTask;

/**
 * Created by kgalligan on 10/5/14.
 */
public class BlockerCommand extends PersistedTask
{
    @Override
    public void run(Context context) throws SoftException, Throwable
    {
        throw new SoftException();
    }

    @Override
    protected boolean handleError(Context context, Throwable e)
    {
        return false;
    }
}
