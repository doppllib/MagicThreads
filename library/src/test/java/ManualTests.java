import org.junit.runner.notification.RunListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import co.touchlab.doppl.testing.DopplJunitTestHelper;

/**
 * Created by kgalligan on 4/25/17.
 */

public class ManualTests
{
    public static final Class[] classes = new Class[]{
            co.touchlab.magicthreadsdemo.test.app.HeadlessTest.class,
            co.touchlab.magicthreadsdemo.test.fromdb.FirstSaveTest.class,
            co.touchlab.magicthreadsdemo.test.ordering.AddLotsTest.class,
            co.touchlab.magicthreadsdemo.test.ApplicationTest.class,
            co.touchlab.magicthreadsdemo.test.RestartQueueTest.class,
            co.touchlab.magicthreadsdemo.test.SimpleSimpleQueueTest.class,
    };

    public static void runTests() throws InterruptedException
    {
        final AtomicInteger resultValue = new AtomicInteger();
        new Thread(){
            @Override
            public void run()
            {
                resultValue.set(DopplJunitTestHelper.run(classes));
            }
        }.start();

    }
}
