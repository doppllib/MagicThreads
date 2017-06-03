package co.touchlab.magicthreadsdemo.test;

import android.os.Handler;
import android.os.Looper;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import co.touchlab.android.threading.tasks.persisted.PersistedTaskQueue;
import co.touchlab.android.threading.utils.UiThreadContext;
import co.touchlab.doppl.testing.DopplContextDelegateTestRunner;
import co.touchlab.doppl.testing.DopplRuntimeEnvironment;
import co.touchlab.doppl.testing.DopplTest;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(DopplContextDelegateTestRunner.class)
public class ApplicationTest
{

    private PersistedTaskQueue queue;
    private PersistedTaskQueue.PersistedTaskQueueState persistedTaskQueueState;

    @Test
    public void testUiThread() throws InterruptedException
    {
        if(Looper.myLooper() == Looper.getMainLooper())
            throw new RuntimeException("Must be in background thread");

//        //Command line
//        if(UiThreadContext.isInIosUiThread())
//            return;

        Handler handler = new Handler(Looper.getMainLooper());
        final CountDownLatch latch = new CountDownLatch(2);

        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                queue = TestPersistedTaskQueueFactory.getInstance(DopplRuntimeEnvironment.getApplication(), ApplicationTest.class);
                queue.execute(new TestCommand());
                queue.execute(new NetworkExceptionCommand());
                queue.execute(new TestCommand());
                queue.execute(new NeverCommand());
                latch.countDown();
            }
        });

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                checkQueueState();
                latch.countDown();
            }
        }, 1200);

        latch.await(7, TimeUnit.SECONDS);

        assertEquals(persistedTaskQueueState.getPending().size(), 0);
        assertEquals(persistedTaskQueueState.getQueued().size(), 3);
        assertNull(persistedTaskQueueState.getCurrentTask());
    }

    private void checkQueueState()
    {
        persistedTaskQueueState = queue.copyPersistedState();
        System.out.println("checkQueueState yep");
    }



}