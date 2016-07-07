package co.touchlab.magicthreadsdemo.test;

import android.os.Handler;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.touchlab.android.threading.tasks.persisted.PersistedTaskQueue;
import co.touchlab.android.threading.tasks.persisted.storage.DefaultPersistedTaskQueue;
import co.touchlab.android.threading.utils.UiThreadContext;
import co.touchlab.doppel.testing.DoppelTest;
import co.touchlab.doppel.testing.DopplSkipJavaJUnit4ClassRunner;


import static org.junit.Assert.*;
/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@DoppelTest
@RunWith(DopplSkipJavaJUnit4ClassRunner.class)
public class ApplicationTest extends BaseQueueTestNomatch
{

    private PersistedTaskQueue queue;
    private PersistedTaskQueue.PersistedTaskQueueState persistedTaskQueueState;

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
                queue = TestPersistedTaskQueueFactory.getInstance(getContext(), ApplicationTest.class);
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
                checkQueueState();
            }
        }, 3000);
    }

    private void checkQueueState()
    {
        persistedTaskQueueState = queue.copyPersistedState();

    }


    @After
    public void tearDown() throws Exception
    {
        Thread.sleep(5000);
        assertEquals(persistedTaskQueueState.getPending().size(), 0);
        assertEquals(persistedTaskQueueState.getQueued().size(), 3);
        assertNull(persistedTaskQueueState.getCurrentTask());
    }
}