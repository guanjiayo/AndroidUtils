package zs.xmx.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import java.util.Stack;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/4 20:51
 * @本类描述	  Activity管理工具类
 * @内容说明   主要用于项目Activity的管理(增删)
 *
 *
 *
 */

/**
 * 使用:
 * <p>
 * 如果我们要更加方便的是用这个管理类，推荐创建一个Activity的基类，并在onCreate和onDestory方法里面调用对应的方法，这样我们就不需要单独在某个Activity里面添加重复逻辑，精简代码，比如像下面这样
 * <p>
 * /**
 * 对context进行初始化，并将当前的Activity加入到堆栈中，便于管理
 *
 * @Override protected void onCreate(Bundle savedInstanceState){
 * super.onCreate(savedInstanceState);
 * <p>
 * // 添加Activity到堆栈
 * ActManager.getAppManager().addActivity(this);
 * <p>
 * }
 * @Override protected void onDestroy(){
 * super.onDestroy();
 * // 结束Activity&从堆栈中移除
 * ActManager.getAppManager().finishActivity(this);
 * }
 */

public class ActManager {
    private static Stack<Activity> activityStack;
    private static ActManager      instance;

    /**
     * 单一实例:创建的类不会多次new,不占用资源
     */
    public static ActManager getInstance() {
        if (instance == null) {
            instance = new ActManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();// 其实没有做出栈操作
        activityStack.pop();// 出栈;
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束到指定类名的Activity（最后一个不摧毁）
     */
    public void finishToActivity(Class<?> cls) {
        Activity lastActivity = activityStack.lastElement();
        // 直到指定的类名cls
        // while (lastActivity.getClass() != cls) {
        // // 摧毁并出栈
        // System.out.println("activity=" + lastActivity);
        // lastActivity.finish();
        // activityStack.pop();
        // lastActivity = activityStack.lastElement();
        // }
        for (int i = activityStack.size() - 1; i > 0; i--) {
            System.out.println("activity="
                    + activityStack.get(i).getClass().toString());
            if (activityStack.get(i).getClass() == cls) {// 跟指定的activity相同，跳出循环
                break;

            } else {
                // 摧毁并出栈
                lastActivity.finish();
                activityStack.pop();
                //				if (i != 0)// 最后一个，则没下一个
                lastActivity = activityStack.lastElement();
            }
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     * <p>
     * 需要加权限:
     * <p>
     * <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>
     */
    public void AppExit(Context context) {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1) {
                finishAllActivity();
                android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
                System.exit(0);
            } else {
                finishAllActivity();
                android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
                        .getSystemService(Context.ACTIVITY_SERVICE);
                activityMgr.killBackgroundProcesses(context.getPackageName());
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
