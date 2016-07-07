package co.touchlab.magicthreadsdemo.test.ordering;

import android.content.Context;
import android.util.Log;

import co.touchlab.android.threading.errorcontrol.SoftException;
import co.touchlab.android.threading.tasks.persisted.PersistedTask;

/**
 * Created by kgalligan on 10/5/14.
 */
public class NumberedCommand extends PersistedTask
{
    int commandCount;

    public NumberedCommand(int commandCount)
    {
        this.commandCount = commandCount;
    }

    public NumberedCommand()
    {
    }

    @Override
    public void run(Context context) throws SoftException, Throwable
    {
        Log.i("Lots", "Numbered (static): "+ commandCount +"/time: "+ getAdded() +"/tie: "+ getOrderTie());
    }

    @Override
    public boolean handleError(Context context, Throwable exception)
    {
        return false;
    }
}
