package zs.xmx.lib_utils.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/4 19:35
 * @本类描述	   Toast工具类
 * @内容说明    1.防止多次弹出,并且Toast能马上更新;
 *             2.Toast属于UI,放在UI线程,防止ANR
 *             3.建议在基类写定义好 BaseActivity  Context;防止重复操作
 *      
 */
public class ToastUtils {
    private static Toast mToast;

    /**
     * 默认显示Toast
     *
     * @param context
     * @param text
     */
    public static void showToast(final Activity context,final String text) {

        // 如果在主线程直接显示,否者在子线程显示
        if ("main".equalsIgnoreCase(Thread.currentThread().getName())
                || Looper.getMainLooper().getThread() == Thread.currentThread()) {
            initToast(context,text);
        } else {
            context.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    initToast(context,text);
                }
            });
        }

    }

    /**
     * 可设置时间长短的显示Toast
     *
     * @param context
     * @param text
     * @param length
     */
    public static void showToast(final Activity context,final String text,
                                 final int length) {
        if ("main".equalsIgnoreCase(Thread.currentThread().getName())
                || Looper.getMainLooper().getThread() == Thread.currentThread()) {
            initToast(context,text,length);
        } else {
            context.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    initToast(context,text,length);

                }
            });
        }

    }

    /**
     * 取消Toast
     */
    public static void cancelShow() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 初始化 可设置时间的 Toast
     *
     * context.getApplicationContext() 这样写,可以防止内存泄漏
     *
     * @param context
     * @param text
     * @param length
     */
    private static void initToast(Context context,String text,int length) {
        if (context != null) {
            if (mToast == null) {// 防止多次点击多次弹出(点击多次,来不及判断内容,Toast会滞空)

                mToast = Toast.makeText(context.getApplicationContext(),text,length);
            }
            //如果这个Toast已经在显示了,那么这里会立即修改文本
            mToast.setText(text);
            mToast.setDuration(length);
            mToast.show();
        }
    }

    /**
     * 初始化默认 Toast(短时)
     *
     * context.getApplicationContext() 这样写,可以防止内存泄漏
     *
     * @param context
     * @param text
     */
    private static void initToast(Context context,String text) {
        if (context != null) {
            if (mToast == null) {// 防止多次点击多次弹出(点击多次,来不及判断内容,Toast会滞空)

                mToast = Toast.makeText(context.getApplicationContext(),text,Toast.LENGTH_SHORT);
            }
            //如果这个Toast已经在显示了,那么这里会立即修改文本
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.show();
        }
    }
}
