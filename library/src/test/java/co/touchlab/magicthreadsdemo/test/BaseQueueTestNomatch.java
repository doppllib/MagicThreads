package co.touchlab.magicthreadsdemo.test;

import android.os.Handler;

import org.junit.Before;

import co.touchlab.magicthreadsdemo.test.utils.ThreadHelper;

/**
 * Created by kgalligan on 10/4/14.
 */
public abstract class BaseQueueTestNomatch extends AndroidTestCase
{
    protected Handler handler;

    @Before
    public void setUp() throws Exception
    {
        handler = ThreadHelper.mainHandler();

//        dropDatabases();
    }

    /*protected void dropDatabases()
    {
        File test = getContext().getDatabasePath("test");
        File[] files = test.getParentFile().listFiles();
        if(files != null)
        {
            for(File file : files)
            {
                file.delete();
            }
        }
    }*/
}
