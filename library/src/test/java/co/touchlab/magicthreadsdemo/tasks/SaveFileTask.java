package co.touchlab.magicthreadsdemo.tasks;
import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import co.touchlab.android.threading.eventbus.EventBusExt;
import co.touchlab.android.threading.tasks.sticky.StickyTask;
import co.touchlab.android.threading.tasks.sticky.StickyTaskManager;

/**
 * Created by kgalligan on 12/14/14.
 */
public class SaveFileTask extends StickyTask
{
    private String data;

    public SaveFileTask(StickyTaskManager stickyTaskManager, String data)
    {
        super(stickyTaskManager);
        this.data = data;
    }

    @Override
    protected void run(Context context) throws Throwable
    {
        File outFile = new File(context.getFilesDir(), "test_" + System.currentTimeMillis() + ".data");
        saveStringToFile(outFile, data);
    }

    private void saveStringToFile(File outFile, String strData) throws IOException
    {
        File tempFile = new File(outFile.getParent(), outFile.getName() + ".temp");

        FileWriter fileWriter = new FileWriter(tempFile);
        fileWriter.append(strData);
        fileWriter.close();

        tempFile.renameTo(outFile);
    }

    @Override
    protected boolean handleError(Context context, Throwable e)
    {
        return false;
    }

    @Override
    protected void onComplete(Context context)
    {
        EventBusExt.getDefault().post(this);
    }
}
