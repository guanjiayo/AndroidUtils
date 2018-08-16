package zs.xmx.lib_utils.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.annotation.NonNull;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/9/7 14:17
 * @本类描述	   GlobalUtils 初始化工具类
 * @内容说明   1.获取Application
 *             2.调用ActManage_LinkList 管理所有Activity集合
 *
 * @补充内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public final class GlobalUtils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;


    private GlobalUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     * <p>
     * 在Application类初始化
     * <p>
     * 如果使用LinkList那套,需要在Application调用一下方法
     * app.registerActivityLifecycleCallbacks(ActListManager.mCallbacks);
     *
     * @param app Application类
     */
    public static void init(@NonNull final Application app) {
        GlobalUtils.sApplication = app;
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
}