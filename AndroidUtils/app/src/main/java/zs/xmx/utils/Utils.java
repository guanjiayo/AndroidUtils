package zs.xmx.utils;

import android.content.Context;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/2/6 14:17
 * @本类描述	   Utils 初始化工具类
 * @内容说明
 * @补充内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class Utils {

    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null)
            return context;
        throw new NullPointerException("u should init first");
    }
}