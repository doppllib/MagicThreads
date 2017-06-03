package co.touchlab.magicthreadsdemo.test.ordering;

import android.util.Log;

import org.junit.runner.RunWith;

import co.touchlab.android.threading.tasks.persisted.PersistedTaskQueue;
import co.touchlab.doppl.testing.DopplContextDelegateTestRunner;
import co.touchlab.doppl.testing.DopplTest;

import co.touchlab.magicthreadsdemo.test.BaseSimpleQueueTestNomatch;
import co.touchlab.magicthreadsdemo.test.StaticLog;


import static org.junit.Assert.assertEquals;

/**
 * Created by kgalligan on 10/5/14.
 */
@RunWith(DopplContextDelegateTestRunner.class)
public class AddLotsTest extends BaseSimpleQueueTestNomatch
{
    public static final int BLOCK_COUNT = 70;
    private PersistedTaskQueue.PersistedTaskQueueState stateAfterAdd;

    public AddLotsTest()
    {
        super(5000, 8000);
    }

    @Override
    protected void runQueueOps()
    {
        PersistedTaskQueue queue = getQueue();

        int entryCount = 0;

        addABunch(queue, entryCount);

        queue.execute(new BlockerCommand());

        addABunch(queue, entryCount);

        stateAfterAdd = queue.copyPersistedState();
    }

    private void addABunch(PersistedTaskQueue queue, int entryCount)
    {
        for(int i=0; i<BLOCK_COUNT; i++)
        {
            queue.execute(new NumberedCommand(entryCount++));
        }
    }

    @Override
    protected void asserQueueState(PersistedTaskQueue.PersistedTaskQueueState endState)
    {
        assertEquals(stateAfterAdd.getAdding().size(), (BLOCK_COUNT * 2) + 1);
        assertEquals(stateAfterAdd.getQueued().size(), 0);

        assertEquals(endState.getPending().size(), 0);
        assertEquals(endState.getQueued().size(), BLOCK_COUNT + 1);

        Log.w("Numbered", StaticLog.logOut());
    }
}
