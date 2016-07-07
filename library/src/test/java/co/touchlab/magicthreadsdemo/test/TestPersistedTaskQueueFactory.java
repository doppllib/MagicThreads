package co.touchlab.magicthreadsdemo.test;
import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import co.touchlab.android.threading.tasks.persisted.ConfigException;
import co.touchlab.android.threading.tasks.persisted.PersistedTaskQueue;
import co.touchlab.android.threading.tasks.persisted.PersistedTaskQueueConfig;
import co.touchlab.android.threading.tasks.persisted.storage.sqlite.SimpleDatabaseHelper;

/**
 * Created by kgalligan on 7/7/16.
 */
public class TestPersistedTaskQueueFactory
{
    private static Map<Class, PersistedTaskQueue> INSTANCE = new HashMap<Class, PersistedTaskQueue>();

    public static synchronized PersistedTaskQueue getInstance(Context context, Class source)
    {
        PersistedTaskQueue persistedTaskQueue = INSTANCE.get(source);
        if(persistedTaskQueue == null)
        {
            PersistedTaskQueueConfig build;
            try
            {
                PersistedTaskQueueConfig.Builder builder = new PersistedTaskQueueConfig.Builder();
                builder.setDatabase(new SimpleDatabaseHelper(context, source.getName() + "_" + System.currentTimeMillis())
                        .getWritableDatabase());
                build = builder.build(context);
            }
            catch(ConfigException e)
            {
                throw new RuntimeException(e);
            }
            persistedTaskQueue = new PersistedTaskQueue((Application) context.getApplicationContext(), build);
            INSTANCE.put(source, persistedTaskQueue);
        }

        return persistedTaskQueue;
    }
}
