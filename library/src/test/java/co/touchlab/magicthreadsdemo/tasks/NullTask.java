package co.touchlab.magicthreadsdemo.tasks;

import android.content.Context;

import co.touchlab.android.threading.eventbus.EventBusExt;
import co.touchlab.android.threading.tasks.Task;

/**
 * This does nothing, really.
 *
 * Created by kgalligan on 9/13/14.
 */
public class NullTask extends Task
{
    public NullTask()
    {
        super();
    }

    @Override
    protected void run(Context context) throws Exception
    {
//        Thread.sleep(5000);
    }

    @Override
    protected boolean handleError(Context context, Throwable e)
    {
        return false;
    }

    @Override
    protected void onComplete(Context context)
    {
        EventBusExt.getDefault().post(this);
    }
}
