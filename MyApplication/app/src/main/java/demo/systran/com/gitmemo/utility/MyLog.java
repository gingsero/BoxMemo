package demo.systran.com.gitmemo.utility;

import android.util.Log;

/**
 * Created by dh on 2017-03-28.
 */

public class MyLog {

    static final String TAG = "EzTalky Suwon";


    /** MyLog Level Error **/
    public static final void e(String message) {
        if (LogApplication.DEBUG) Log.e(TAG, buildMyLogMsg(message));
    }
    public static final void e(String tag, String message) {
        if (LogApplication.DEBUG) Log.e(tag, buildMyLogMsg(message));
    }

    /** MyLog Level Warning **/
    public static final void w(String message) {
        if (LogApplication.DEBUG)Log.w(TAG, buildMyLogMsg(message));
    }
    public static final void w(String tag, String message) {
        if (LogApplication.DEBUG)Log.w(tag, buildMyLogMsg(message));
    }

    /** MyLog Level Information **/
    public static final void i(String message) {
        if (LogApplication.DEBUG)Log.i(TAG, buildMyLogMsg(message));
    }
    public static final void i(String tag, String message) {
        if (LogApplication.DEBUG)Log.i(tag, buildMyLogMsg(message));
    }

    /** MyLog Level Debug **/
    public static final void d(String message) {
        if (LogApplication.DEBUG)Log.d(TAG, buildMyLogMsg(message));
    }
    public static final void d(String tag, String message) {
        if (LogApplication.DEBUG)Log.d(tag, buildMyLogMsg(message));
    }

    /** MyLog Level Verbose **/
    public static final void v(String message) {
        if (LogApplication.DEBUG)Log.v(TAG, buildMyLogMsg(message));
    }
    public static final void v(String tag, String message) {
        if (LogApplication.DEBUG)Log.v(tag, buildMyLogMsg(message));
    }

    public static String buildMyLogMsg(String message) {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);

        return sb.toString();

    }
}
