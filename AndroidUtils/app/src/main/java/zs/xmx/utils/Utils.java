package zs.xmx.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/2/6 14:17
 * @本类描述	   Utils 初始化工具类
 * @内容说明  //TODO 结合我们的ActManager 修改一下,用这里的,取消用stack的方式
 *           //TODO 权衡一下在Application类还是Utils类,得到上下文
 * @补充内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Application             sApplication;
    static         WeakReference<Activity> sTopActivityWeakRef;
    static List<Activity> sActivityList = new LinkedList<>();
    @SuppressLint("StaticFieldLeak")
    static Activity sTopActivity;

    private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            sActivityList.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            sTopActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);
        }
    };

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param app Application类
     */
    public static void init(@NonNull final Application app) {
        Utils.sApplication = app;
        app.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * 获取Application
     * <p>
     * 相当于getApplicationCotext()
     * <p>
     * 这里主要为了避免部分不能使用getApplicationContext()的情况
     *
     * @return Application
     */
    public static Application getApp() {
        if (sApplication != null)
            return sApplication;
        throw new NullPointerException("u should init first");
    }

    /**
     * 设置Activity为弱引用
     *
     * @param activity
     */
    private static void setTopActivityWeakRef(Activity activity) {
        if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
            sTopActivityWeakRef = new WeakReference<>(activity);
        }
    }
}