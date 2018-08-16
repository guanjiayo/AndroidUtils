package zs.xmx.lib_utils.manager;
/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/9/7
 * @本类描述	  Activity管理类
 * @内容说明   1.使用LinkList对项目中所有Activity进行管理
 *
 * @使用: 1.在Application类的registerActivityLifecycleCallbacks(ActManager.mCallbacks);传入mCallBack
 *        2.后续的Activity页面跳转,使用ActivityUtils
 *
 */

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class ActListManager {

    public static WeakReference<Activity> sTopActivityWeakRef;
    public static List<Activity> sActivityList = new LinkedList<>();

    private ActListManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            /**添加Activity到我们的LinkList 管理**/
            sActivityList.add(activity);
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivityWeakRef(activity);
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

    private static void setTopActivityWeakRef(Activity activity) {
        if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
            sTopActivityWeakRef = new WeakReference<>(activity);
        }
    }
}
