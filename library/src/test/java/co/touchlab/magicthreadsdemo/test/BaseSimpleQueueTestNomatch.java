package co.touchlab.magicthreadsdemo.test;

import org.junit.Test;

import co.touchlab.android.threading.tasks.persisted.PersistedTaskQueue;
import co.touchlab.android.threading.utils.UiThreadContext;
import co.touchlab.doppl.testing.DopplRuntimeEnvironment;
import co.touchlab.magicthreadsdemo.test.utils.ThreadHelper;

/**
 * Created by kgalligan on 10/4/14.
 */
public abstract class BaseSimpleQueueTestNomatch extends BaseQueueTestNomatch
{
    PersistedTaskQueue queue;
    PersistedTaskQueue.PersistedTaskQueueState queueState;
    int firstPause;
    int secondPause;

    protected BaseSimpleQueueTestNomatch(int firstPause, int secondPause)
    {
        super();
        this.firstPause = firstPause;
        this.secondPause = secondPause;
    }

    protected BaseSimpleQueueTestNomatch()
    {
        this(2000, 4000);
    }

    @Test
    public void testUiThread()
    {
        //Command line
        if(UiThreadContext.isInIosUiThread())
            return;

        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                queue = TestPersistedTaskQueueFactory.getInstance(DopplRuntimeEnvironment.getApplication(), BaseSimpleQueueTestNomatch.this.getClass());
                runQueueOps();
            }
        });

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                checkQueueState();
            }
        }, firstPause);

        ThreadHelper.sleep(secondPause);
        asserQueueState(queueState);
    }

    protected abstract void runQueueOps();

    private void checkQueueState()
    {
        queueState = queue.copyPersistedState();
    }

    protected abstract void asserQueueState(PersistedTaskQueue.PersistedTaskQueueState endState);

    public PersistedTaskQueue getQueue()
    {
        return queue;
    }
}
