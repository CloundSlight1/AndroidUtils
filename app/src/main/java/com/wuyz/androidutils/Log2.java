package com.wuyz.androidutils;

import android.os.Environment;
import android.util.Log;

import com.wuyz.androidutils.manager.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Log2 {

    private static final String TAG = "androidutils";
    private final static boolean ENABLE = Log.isLoggable(TAG, Log.DEBUG);
    private final static int LENGTH = 1024 * 1024;
    private final static StringBuilder stringBuilder = new StringBuilder(LENGTH);

    public static void v(String className, String format, Object... args) {
        if (BuildConfig.DEBUG || ENABLE)
            Log.v(TAG, className + ", " + String.format(format, args));
    }

    public static void i(String className, String format, Object... args) {
        if (BuildConfig.DEBUG || ENABLE)
            Log.i(TAG, className + ", " + String.format(format, args));
    }

    public static void d(String className, String format, Object... args) {
        if (BuildConfig.DEBUG || ENABLE)
            Log.d(TAG, className + ", " + String.format(format, args));
    }

    public static void w(String className, String format, Object... args) {
        Log.w(TAG, className + ", " + String.format(format, args));
    }

    public static void e(String className, String msg) {
        e(className, msg, null);
    }

    public static void e(String className, Throwable tr) {
        e(TAG, className, tr);
    }

    public static void e(String className, String msg, Throwable tr) {
        if (tr != null) {
            String s = Log.getStackTraceString(tr);
            if (s.isEmpty()) s = tr.getMessage();
            Log.e(TAG, className + ", " + msg + System.lineSeparator() + s);
        } else {
            Log.e(TAG, className + ", " + msg, tr);
        }
    }

    private static String getTrace() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        return TAG +
                " : " + traceElement.getFileName() +
                " | " + traceElement.getLineNumber() +
                " | " + traceElement.getMethodName() + "() ";
    }

    private static void writeToFile(String msg) {
        if (msg == null || msg.isEmpty())
            return;
        stringBuilder.append(StringUtils.dateFormat.format(new Date())).append(' ').append(msg);
        if (stringBuilder.length() < LENGTH)
            return;
        flush();
    }

    public static void flush() {
        String s = stringBuilder.toString();
        stringBuilder.setLength(0);

        File path = new File(Application.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "log");
        if (!path.mkdirs()) {
            Log2.e(TAG, "failed to mkdir " + path.getAbsolutePath());
            return;
        }
        File file = new File(path, StringUtils.dateFormat2.format(new Date()) + ".txt");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file, true);
            outputStream.write(s.getBytes());
        } catch (Exception e) {
            Log2.e(TAG, e);
        } finally {
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
