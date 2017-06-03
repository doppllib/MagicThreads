package co.touchlab.magicthreadsdemo.test;

import android.os.Handler;

import org.junit.Before;

import co.touchlab.magicthreadsdemo.test.utils.ThreadHelper;

/**
 * Created by kgalligan on 10/4/14.
 */
public abstract class BaseQueueTestNomatch
{
    protected Handler handler;

    @Before
    public void setUp() throws Exception
    {
        handler = ThreadHelper.mainHandler();
    }

}
