package zs.xmx.lib_utils.utils;
/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/3/3 0:40
 * @本类描述	  线程工具类
 * @内容说明  用于主线程与子线程切换
 *
 * @补充内容  静态修饰,防止内存泄漏
 *
 * ---------------------------------     
 * @更新时间   $Date$
 * @新增内容
 *
 */

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadUtils {
    //让Handler一直绑定在主线程,防止该类在子线程使用时,任务到不了主线程
    private static Handler  handler  = new Handler(Looper.getMainLooper());
    private static Executor executor = Executors.newSingleThreadExecutor();

    public static void runOnSubThread(Runnable runnable) {
        executor.execute(runnable);
    }

    public static void runOnMainThread(Runnable runnable) {
        handler.post(runnable);
    }

}
