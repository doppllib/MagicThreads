package co.touchlab.magicthreadsdemo.test.fromdb;

import android.os.Handler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import co.touchlab.android.threading.tasks.persisted.PersistedTaskQueue;
import co.touchlab.android.threading.tasks.persisted.storage.DefaultPersistedTaskQueue;
import co.touchlab.android.threading.utils.UiThreadContext;
import co.touchlab.doppel.testing.DoppelTest;
import co.touchlab.doppel.testing.DopplSkipJavaJUnit4ClassRunner;
import co.touchlab.magicthreadsdemo.test.AndroidTestCase;
import co.touchlab.magicthreadsdemo.test.NetworkExceptionCommand;
import co.touchlab.magicthreadsdemo.test.NeverCommand;
import co.touchlab.magicthreadsdemo.test.TestCommand;
import co.touchlab.magicthreadsdemo.test.TestPersistedTaskQueueFactory;
import co.touchlab.magicthreadsdemo.test.utils.ThreadHelper;

import static org.junit.Assert.*;

/**
 * Created by kgalligan on 10/4/14.
 */
@DoppelTest
@RunWith(DopplSkipJavaJUnit4ClassRunner.class)
public class FirstSaveTest extends AndroidTestCase
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

                queue = TestPersistedTaskQueueFactory.getInstance(getContext(), FirstSaveTest.class);
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
