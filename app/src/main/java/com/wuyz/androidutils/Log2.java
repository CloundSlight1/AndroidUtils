package com.wuyz.androidutils;

import android.util.Log;

public class Log2 {

    private static final String TAG = "androidutils";
    private final static boolean ENABLE = Log.isLoggable(TAG, Log.DEBUG);

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
}
