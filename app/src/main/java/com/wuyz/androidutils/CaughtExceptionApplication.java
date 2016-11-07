package com.wuyz.androidutils;

import android.app.Application;
import android.os.Environment;

import com.wuyz.androidutils.manager.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by wuyz on 2016/10/8.
 *
 */

public class CaughtExceptionApplication extends Application {

    private static final String TAG = "CaughtExceptionApplication";

    private Thread.UncaughtExceptionHandler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log2.e(TAG, e);
                File file;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    file = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                } else {
                    file = getExternalFilesDir("Documents");
                }
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(file, "error");
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
                    writer.println(StringUtils.longDateFormat.format(new Date()));
                    e.printStackTrace(writer);
                    Throwable throwable = e.getCause();
                    while (throwable != null) {
                        throwable.printStackTrace(writer);
                        throwable = throwable.getCause();
                    }
                    writer.close();
                } catch (Exception e2) {
                    Log2.e(TAG, e2);
                } finally {
                    if (writer != null)
                        writer.close();
                }
                if (handler != null) {
                    handler.uncaughtException(t, e);
                }
            }
        });
    }
}
