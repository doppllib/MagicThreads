package co.touchlab.magicthreadsdemo.test;

import org.junit.Test;
import org.junit.runner.RunWith;

import co.touchlab.android.threading.tasks.persisted.PersistedTaskQueue;
import co.touchlab.android.threading.tasks.persisted.storage.DefaultPersistedTaskQueue;
import co.touchlab.doppel.testing.DoppelTest;
import co.touchlab.doppel.testing.DopplSkipJavaJUnit4ClassRunner;
import co.touchlab.magicthreadsdemo.test.utils.ThreadHelper;

import static org.junit.Assert.*;

/**
 * Created by kgalligan on 10/4/14.
 */
@DoppelTest
@RunWith(DopplSkipJavaJUnit4ClassRunner.class)
public class RestartQueueTest extends BaseQueueTestNomatch
{
    PersistedTaskQueue queue;
    PersistedTaskQueue.PersistedTaskQueueState queueState;

    @Test
    public void testUiThread()
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                queue = TestPersistedTaskQueueFactory.getInstance(getContext(), RestartQueueTest.class);
                queue.execute(new TestCommand());
                queue.execute(new NetworkExceptionCommand());
                queue.execute(new TestCommand());
                queue.execute(new NeverCommand());
            }
        });

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                queue.restartQueue();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        checkQueueState();
                    }
                }, 2000);
            }
        }, 2000);

        ThreadHelper.sleep(6000);
        assertEquals(queueState.getPending().size(), 0);
        assertEquals(queueState.getQueued().size(), 0);
        assertNull(queueState.getCurrentTask());
    }

    private void checkQueueState()
    {
        queueState = queue.copyPersistedState();
    }



}
