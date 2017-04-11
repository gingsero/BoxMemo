package demo.systran.com.gitmemo.utility;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by dh on 2017-03-28.
 */

public class LogApplication extends Application {
    public static boolean DEBUG = false;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);

    }


    @Override
    public void onCreate() {
        super.onCreate();
        this.DEBUG = isDebuggable(this);
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        MyLog.d("App Finish");
    }

    /**
     * get Debug mode
     *
     * @param context
     * @return
     */
    private boolean isDebuggable(Context context) {
        boolean debuggable = false;
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
			/* debuggable variable will remain false */
        }

        return debuggable;
    }
}
