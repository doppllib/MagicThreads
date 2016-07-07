package co.touchlab.magicthreadsdemo.tasks;
import android.content.Context;

import co.touchlab.android.threading.eventbus.EventBusExt;
import co.touchlab.android.threading.tasks.Task;

/**
 * Created by kgalligan on 9/24/15.
 */
public class MyTask extends Task
{
    @Override
    protected void run(Context context) throws Throwable
    {

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
