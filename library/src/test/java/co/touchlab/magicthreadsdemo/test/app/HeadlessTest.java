package co.touchlab.magicthreadsdemo.test.app;

import android.os.Handler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import co.touchlab.android.threading.tasks.BaseTaskQueue;
import co.touchlab.android.threading.tasks.Task;
import co.touchlab.android.threading.tasks.TaskQueue;
import co.touchlab.android.threading.utils.UiThreadContext;
import co.touchlab.doppl.testing.DopplContextDelegateTestRunner;
import co.touchlab.doppl.testing.DopplRuntimeEnvironment;
import co.touchlab.doppl.testing.DopplTest;

import co.touchlab.magicthreadsdemo.tasks.NullTask;
import co.touchlab.magicthreadsdemo.test.utils.ThreadHelper;


import static org.junit.Assert.assertEquals;

/**
 * Created by kgalligan on 10/13/14.
 */
@RunWith(DopplContextDelegateTestRunner.class)
public class HeadlessTest
{
    @Test
    public void testQueue() throws InterruptedException
    {
        //Command line
        if(UiThreadContext.isInIosUiThread())
            return;

        Assert.assertFalse(UiThreadContext.isInUiThread());
        final AtomicInteger innerCount = new AtomicInteger(0);
        Handler handler = ThreadHelper.mainHandler();
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                UiThreadContext.assertUiThread();
                int count = 0;
                TaskQueue.loadQueueDefault(DopplRuntimeEnvironment.getApplication()).execute(new NullTask());
                TaskQueue.loadQueueDefault(DopplRuntimeEnvironment.getApplication()).execute(new NullTask());
                TaskQueue.loadQueueDefault(DopplRuntimeEnvironment.getApplication()).execute(new NullTask());
                TaskQueue.loadQueueDefault(DopplRuntimeEnvironment.getApplication()).execute(new NullTask());
                countQueue(innerCount);
            }
        });

        //Let main thread code in previous block run
        Thread.sleep(1000);

        assertEquals(innerCount.get(), 4);

        //Main thread should have cleared out queue by now
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                countQueue(innerCount);
            }
        });

        //Let that shit run
        Thread.sleep(1000);

        assertEquals(innerCount.get(), 0);
    }

    @Test
    public void testTiming() throws InterruptedException
    {
        //Command line
        if(UiThreadContext.isInIosUiThread())
            return;

        final AtomicInteger innerCount1 = new AtomicInteger(0);
        final AtomicInteger innerCount2 = new AtomicInteger(0);
        final AtomicInteger innerCount3 = new AtomicInteger(0);
        final AtomicInteger innerCountEnd = new AtomicInteger(0);
        Handler handler = ThreadHelper.mainHandler();

        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    //Make sure we get through our things first
                    Thread.sleep(2000);
                }
                catch (InterruptedException e)
                {
                    //Ehh
                }
            }
        });

        TaskQueue.loadQueueDefault(DopplRuntimeEnvironment.getApplication()).execute(new NullTask());
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                countQueue(innerCount1);
            }
        });
        TaskQueue.loadQueueDefault(DopplRuntimeEnvironment.getApplication()).execute(new NullTask());
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                countQueue(innerCount2);
            }
        });
        TaskQueue.loadQueueDefault(DopplRuntimeEnvironment.getApplication()).execute(new NullTask());
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                countQueue(innerCount3);
            }
        });
        Thread.sleep(3000);
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                countQueue(innerCountEnd);
            }
        });

        assertEquals(innerCount1.get(), 1);
        assertEquals(innerCount2.get(), 2);
        assertEquals(innerCount3.get(), 3);
        assertEquals(innerCountEnd.get(), 0);
    }

    @Test
    public void testFiles()
    {
        //Command line
        if(UiThreadContext.isInIosUiThread())
            return;

        assertEquals(DopplRuntimeEnvironment.getApplication().getFilesDir().exists(), true);
        File dbPath = DopplRuntimeEnvironment.getApplication().getDatabasePath("test");

        File parentDbPath = dbPath.getParentFile();
        assertEquals(parentDbPath.exists(), true);
        File[] files = parentDbPath.listFiles();
        for (File file : files)
        {
            file.delete();
        }
    }

    private volatile int helloCount = 0;

    private void countQueue(final AtomicInteger innerCount)
    {
        System.out.println("helloCount: "+ helloCount++);
        innerCount.set(0);
        UiThreadContext.assertUiThread();
        TaskQueue.loadQueueDefault(DopplRuntimeEnvironment.getApplication()).query(new BaseTaskQueue.QueueQuery()
        {
            @Override
            public void query(BaseTaskQueue queue, Task task)
            {
                System.out.println("TaskClassName: "+ task.getClass().getSimpleName());

                innerCount.addAndGet(1);
            }
        });
    }
}
