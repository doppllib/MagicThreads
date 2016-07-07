package co.touchlab.magicthreadsdemo.test;
import android.app.Application;
import android.content.Context;
import android.content.IOSContext;
import android.os.Looper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.util.concurrent.CountDownLatch;

import co.touchlab.android.threading.utils.UiThreadContext;

/**
 * Created by kgalligan on 7/6/16.
 */
public class AndroidTestCase
{
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private IOSContext iosContext;


    @Before
    public void runBefore()
    {
        if(iosContext == null)
        {
            iosContext = new IOSContext();
        }

        if(Looper.getMainLooper() == null)
        {
            initMainLooper();
        }

        Assert.assertFalse(UiThreadContext.isInUiThread());
    }

    public synchronized Application getApp()
    {
        if(iosContext == null)
        {
            iosContext = new IOSContext(); //new TestingContext(testFolder.getRoot());

            initMainLooper();
        }

        return iosContext;
    }

    private synchronized void initMainLooper()
    {
        final CountDownLatch latch = new CountDownLatch(1);
        //We want the "main thread" to be a different thread.
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                if(Looper.getMainLooper() == null) {
                    Looper.prepareMainLooper();
                }
                latch.countDown();
            }
        };
        thread.start();

        try
        {
            latch.await();
        }
        catch(InterruptedException e)
        {
            throw new RuntimeException("Whoa!!!");
        }
    }

    public Context getContext()
    {
        return getApp();
    }
}
