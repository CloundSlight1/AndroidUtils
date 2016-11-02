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

    private Thread.UncaughtExceptionHandler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
//                Toast.makeText(MyApp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                File file = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(file, "error.txt");
                try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
                    writer.println(System.lineSeparator() + "***************************************"
                            + System.lineSeparator() + StringUtils.longDateFormat.format(new Date()) + System.lineSeparator());
                    e.printStackTrace(writer);
                    Throwable throwable;
                    while ((throwable = e.getCause()) != null) {
                        throwable.printStackTrace(writer);
                    }
                    writer.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (handler != null) {
                    handler.uncaughtException(t, e);
                }
            }
        });
    }
}
