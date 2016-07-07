package co.touchlab.magicthreadsdemo.test;

import org.junit.runner.RunWith;

import co.touchlab.android.threading.tasks.persisted.PersistedTaskQueue;
import co.touchlab.doppel.testing.DopplSkipJavaJUnit4ClassRunner;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by kgalligan on 10/4/14.
 */

@RunWith(DopplSkipJavaJUnit4ClassRunner.class)
public class SimpleSimpleQueueTest extends BaseSimpleQueueTestNomatch
{
    @Override
    protected void runQueueOps()
    {
        queue.execute(new TestCommand());
        queue.execute(new NetworkExceptionCommand());
        queue.execute(new TestCommand());
        queue.execute(new NeverCommand());
    }

    @Override
    protected void asserQueueState(PersistedTaskQueue.PersistedTaskQueueState endState)
    {
        assertEquals(endState.getPending().size(), 0);
        assertEquals(endState.getQueued().size(), 3);
        assertNull(endState.getCurrentTask());
    }
}
