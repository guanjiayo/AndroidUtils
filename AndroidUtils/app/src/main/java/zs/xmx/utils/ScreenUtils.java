package zs.xmx.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import java.lang.reflect.Method;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/9/21 16:58
 * @本类描述	  屏幕相关 工具类
 * @内容说明   1.Activity 横屏竖屏
 *            2.获取屏幕宽高
 *            3.设置透明状态栏
 *            4.隐藏状态栏,获取状态栏高度,状态栏是否存在
 *            5.显示/隐藏通知栏
 *            6.判断锁屏状态
 *            7.获取ActiconBar高度
 *            8.获取当前窗口的旋转角度
 *            9.判断是否当前屏幕状态(横屏/竖屏)
 *            10.设置窗口的透明度  1.0f,0.5f 变暗
 *      
 */
public class ScreenUtils {
    //TODO 1.虚拟按键 宽高测量  2.ToolBar,snackbar高以及显示隐藏 3.右下角圆圈控件


    /**
     * 获取当前Activity屏幕信息
     *
     * @param context
     * @return 屏幕信息
     */
    public static String getScreenMetrics(Activity context) {
        String str = "";
        DisplayMetrics metrics = new DisplayMetrics(); //Android 自带 屏幕度量
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        int densityDpi = metrics.densityDpi;
        float density = metrics.density;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;

        str += "The absolute width:  " + String.valueOf(screenWidth) + "  pixels\n";
        str += "The absolute height:  " + String.valueOf(screenHeight) + "  pixels\n";
        str += "The DPI :  " + String.valueOf(densityDpi) + "  dpi\n";
        str += "The density of the display:  " + String.valueOf(density) + "\n";
        str += "X dimension :  " + String.valueOf(xdpi) + "  pixels per inch\n";
        str += "Y dimension :  " + String.valueOf(ydpi) + "  pixels per inch\n";

        return str;

    }

    /**
     * 获取屏幕的宽度px
     *
     * @param context 上下文
     * @return 屏幕宽px
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度px
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.heightPixels;
    }

    /**
     * 设置透明状态栏
     * <p>
     * 可在Activity的onCreat()中调用
     * <p>
     * 注意:需在顶部控件布局中加入以下属性让内容出现在状态栏之下:
     * android:clipToPadding="true"   // true 会贴近上层布局 ; false 与上层布局有一定间隙
     * android:fitsSystemWindows="true"   //true 会保留actionBar,title,虚拟键的空间 ; false 不保留
     *
     * @param activity activity
     */
    public static void setHideStatusBar(Activity activity) {
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
            LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
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
        LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & LayoutParams.FLAG_FULLSCREEN) != LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * 获取ActionBar高度
     *
     * @param activity activity
     * @return ActionBar高度
     */
    public static int getActionBarHeight(Activity activity) {
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return 0;
    }

    /**
     * 显示通知栏
     * <p>
     * 注意:需添加权限
     * <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>
     *
     * @param context        上下文
     * @param isSettingPanel {@code true}: 打开设置<br>{@code false}: 打开通知
     */
    public static void showNotificationBar(Context context, boolean isSettingPanel) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "expand"
                : (isSettingPanel ? "expandSettingsPanel" : "expandNotificationsPanel");
        invokePanels(context, methodName);
    }

    /**
     * 隐藏通知栏
     * <p>
     * 注意:需添加权限
     * <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>
     *
     * @param context 上下文
     */
    public static void hideNotificationBar(Context context) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        invokePanels(context, methodName);
    }

    /**
     * 反射唤醒通知栏
     *
     * @param context    上下文
     * @param methodName 方法名
     */
    private static void invokePanels(Context context, String methodName) {
        try {
            Object service = context.getSystemService("statusbar");
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置屏幕为横屏
     * <p>
     * <p>
     * 还有一种就是在Activity中加属性android:screenOrientation="landscape"
     * 不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次
     * 设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
     * 设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法
     *
     * @param activity activity
     */
    public static void setLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     * <p>
     * <p>
     * 还有一种就是在Activity中加属性android:screenOrientation="portrait"
     * 不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次
     * 设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
     * 设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法
     *
     * @param activity
     */
    public static void setPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 设置屏幕为全屏
     * <p>
     * 也就是设置全屏，一定要在setContentView之前调用，否则报错
     * 此方法Activity可以继承AppCompatActivity
     * 启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面
     * 在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
     * 如加了以上配置Activity不能继承AppCompatActivity，会报错
     *
     * @param activity activity
     */
    public static void setFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置窗口的透明度  1.0f,0.5f 变暗
     * @param from  from>=0&&from<=1.0f
     * @param to  to>=0&&to<=1.0f
     * @param context  当前的activity
     */
    public static void setTransBackground(final float from, final float to, Activity context) {
        final Window window = context.getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        WindowManager.LayoutParams params
                                = window.getAttributes();
                        params.alpha = (Float) animation.getAnimatedValue();
                        window.setAttributes(params);
                    }
                });
        valueAnimator.start();
    }


    /**
     * 判断是否锁屏
     *
     * @param context 上下文
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isScreenLock(Context context) {
        KeyguardManager km = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            default:
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
    }

    /**
     * 当前是否是横屏
     *
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 当前是否是竖屏
     *
     * @param context
     * @return
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }


}
