package co.touchlab.android.threading.loaders.networked;

import android.content.Context;
import co.touchlab.android.threading.eventbus.EventBusExt;
import de.greenrobot.event.EventBus;

/**
 * Created by kgalligan on 7/29/14.
 */
public abstract class AbstractDoubleTapEventBusLoader<D, E> extends AbstractDoubleTapLoader<D, E>
{
    private EventBus eventBus;

    public AbstractDoubleTapEventBusLoader(Context context)
    {
        this(context, EventBusExt.getDefault());
    }

    public AbstractDoubleTapEventBusLoader(Context context, EventBus eventBus)
    {
        super(context);
        this.eventBus = eventBus;
    }

    @Override
    protected void registerContentChangedObserver()
    {
        eventBus.register(this);
    }

    @Override
    protected void unregisterContentChangedObserver()
    {
        eventBus.unregister(this);
    }
}