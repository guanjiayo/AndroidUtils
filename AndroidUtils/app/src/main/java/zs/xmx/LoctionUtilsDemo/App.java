package zs.xmx.LoctionUtilsDemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import zs.xmx.utils.CrashUtils;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/12
 *     desc  : App
 * </pre>
 */
public class App extends Application {

    private static App ourInstance;

    public static App getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 内存泄露检查工具
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        ourInstance = this;
        CrashUtils.getInstance().init(this);

    }
}
