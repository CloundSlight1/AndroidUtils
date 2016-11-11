package com.wuyz.androidutils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Log2 {

    private static final String TAG = "androidutils";
    private final static boolean ENABLE = Log.isLoggable(TAG, Log.DEBUG);
    private final static int LENGTH = 100 * 1024;
    private final static StringBuilder stringBuilder = new StringBuilder(LENGTH);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private static final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());

    public static void v(String className, String format, Object... args) {
        if (BuildConfig.DEBUG || ENABLE) {
            String msg = className + ", " + String.format(format, args);
            Log.v(TAG, msg);
            writeToFile("v", msg);
        }
    }

    public static void i(String className, String format, Object... args) {
        if (BuildConfig.DEBUG || ENABLE) {
            String msg = className + ", " + String.format(format, args);
            Log.i(TAG, msg);
            writeToFile("i", msg);
        }
    }

    public static void d(String className, String format, Object... args) {
        if (BuildConfig.DEBUG || ENABLE) {
            String msg = className + ", " + String.format(format, args);
            Log.d(TAG, msg);
            writeToFile("d", msg);
        }
    }

    public static void w(String className, String format, Object... args) {
        String msg = className + ", " + String.format(format, args);
        Log.w(TAG, msg);
        if (BuildConfig.DEBUG || ENABLE) {
            writeToFile("w", msg);
        }
    }

    public static void e(String className, String msg) {
        e(className, msg, null);
    }

    public static void e(String className, Throwable tr) {
        e(TAG, className, tr);
    }

    public static void e(String className, String msg, Throwable tr) {
        String ret;
        if (tr != null) {
            String s = Log.getStackTraceString(tr);
            if (s.isEmpty()) s = tr.getMessage();
            ret = className + ", " + msg + System.lineSeparator() + s;
        } else {
            ret = className + ", " + msg;
        }
        Log.e(TAG, ret);
        if (BuildConfig.DEBUG || ENABLE) {
            writeToFile("e", ret);
        }
    }

    private static String getTrace() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        return TAG +
                " : " + traceElement.getFileName() +
                " | " + traceElement.getLineNumber() +
                " | " + traceElement.getMethodName() + "() ";
    }

    private static void writeToFile(String level, String msg) {
        if (level == null || msg == null || msg.isEmpty())
            return;
        stringBuilder.append(dateFormat2.format(new Date())).append(' ').append(level).append(' ')
                .append(msg).append(System.lineSeparator());
        if (stringBuilder.length() < LENGTH)
            return;
        flush();
    }

    public static void flush() {
        final String s = stringBuilder.toString();
        stringBuilder.setLength(0);

        final File path = new File(Application.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "log");
        if (!path.exists())
            path.mkdirs();
        if (!path.exists() || !path.isDirectory()) {
            Log2.e(TAG, "dir not exist: " + path.getAbsolutePath());
            return;
        }

        File file = new File(path, dateFormat.format(new Date()) + ".txt");
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
