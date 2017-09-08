package zs.xmx.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/10/31 16:07
 * @本类描述	  状态栏_工具类
 * @内容说明   1.设置状态栏为透明
 *            2.获取状态栏高度
 *            3.判断状态栏是否存在
 *            4.设置状态栏颜色
 *            5.添加状态栏view
 *
 * @补充内容  注意:
 *              1.设置状态栏颜色要写在setContentView(..)后面
 *              2.两个重要属性要写在状态栏要跟随颜色的控件上
 *
 * ---------------------------------
 * @新增内容
 *
 */
public class StatusBar {
    /**
     * 设置透明状态栏(相当于隐藏状态栏)
     * <p>
     * 可在Activity的onCreat()中调用
     * <p>
     * 注意:需在顶部控件布局中加入以下属性让内容出现在状态栏之下:
     * android:clipToPadding="true"   // true 会贴近上层布局 ; false 与上层布局有一定间隙
     * android:fitsSystemWindows="true"   //true 会保留actionBar,title,虚拟键的空间 ; false 不保留
     *
     * @param activity activity
     */
    public static void setTransparentStatusBar(Activity activity) {
        //5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            /**
             * 如果上面无效,用这个
             *activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
             *activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
             */
            //4.4到5.0
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            /**
             * 如果上面无效,用这个
             * activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
             */

        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 判断状态栏是否存在
     *
     * @param activity activity
     * @return true :存在   ;  false: 不存在
     */
    public static boolean isStatusBarExists(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams
                .FLAG_FULLSCREEN;
    }

    /**
     * 设置状态栏的颜色
     * 1.将状态栏透明
     * 2.添加一个自定义颜色的View覆盖状态栏
     *
     * @param activity
     * @param argb
     */
    public static void setStatusBarColor(Activity activity, int argb) {
        setTransparentStatusBar(activity);
        addStatusBarView(activity, argb);
    }

    /**
     * 添加状态栏View
     *
     * @param activity 需要设置的 activity
     * @param argb     Color.argb(alpha, 0, 0, 0)  颜色属性
     */
    private static void addStatusBarView(Activity activity, int argb) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        // 移除半透明矩形,以免叠加
        if (contentView.getChildCount() > 1) {
            contentView.removeViewAt(1);
        }
        contentView.addView(createStatusBarView(activity, argb));
    }

    /**
     * 创建矩形 View
     *
     * @param argb Color.argb(alpha, 0, 0, 0)  颜色属性
     * @return View
     */
    private static View createStatusBarView(Activity activity, int argb) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(argb);
        return statusBarView;
    }


}
