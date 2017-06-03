package co.touchlab.magicthreadsdemo.test.fromdb;

import android.os.Handler;

import org.junit.Test;
import org.junit.runner.RunWith;

import co.touchlab.android.threading.tasks.persisted.PersistedTaskQueue;
import co.touchlab.android.threading.utils.UiThreadContext;
import co.touchlab.doppl.testing.DopplContextDelegateTestRunner;
import co.touchlab.doppl.testing.DopplRuntimeEnvironment;
import co.touchlab.magicthreadsdemo.test.NetworkExceptionCommand;
import co.touchlab.magicthreadsdemo.test.NeverCommand;
import co.touchlab.magicthreadsdemo.test.TestCommand;
import co.touchlab.magicthreadsdemo.test.TestPersistedTaskQueueFactory;
import co.touchlab.magicthreadsdemo.test.utils.ThreadHelper;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by kgalligan on 10/4/14.
 */
@RunWith(DopplContextDelegateTestRunner.class)
public class FirstSaveTest
{
//    private Handler handler;
    PersistedTaskQueue queue;
    PersistedTaskQueue.PersistedTaskQueueState queueState;

    @Test
    public void testInsertSavedValues()
    {
        //Command line
        if(UiThreadContext.isInIosUiThread())
            return;

//        PersistedTaskQueue queue;
//        PersistedTaskQueue.PersistedTaskQueueState queueState;
        Handler handler = ThreadHelper.mainHandler();
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                UiThreadContext.assertUiThread();

                queue = TestPersistedTaskQueueFactory.getInstance(DopplRuntimeEnvironment.getApplication(), FirstSaveTest.class);
                queue.execute(new TestCommand());
                queue.execute(new NetworkExceptionCommand());
                queue.execute(new TestCommand());
                queue.execute(new NetworkExceptionCommand());
                queue.execute(new NeverCommand());
            }
        });

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                queueState = queue.copyPersistedState();
            }
        }, 2200);

        ThreadHelper.sleep(4000);
        assertEquals(queueState.getPending().size(), 0);
        assertEquals(queueState.getQueued().size(), 4);
        assertNull(queueState.getCurrentTask());
    }
}
