package zs.xmx.lib_utils.utils.bar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/10/31 16:07
 * @本类描述	  状态栏_工具类
 * @内容说明   1.设置状态栏为透明
 *            2.设置透明的虚拟按键
 *            3.设置状态栏/虚拟按键纯色
 *            4.设置亮色主体(白底黑字)
 *
 *
 * @使用方法  不传布局
 *             setTransparentNavigationBottom/statusBar(context,null) 需要自己处理视图延伸至状态栏/虚拟按键区域
 *           传布局
 *
 *           直接设置颜色
 *           setNavigationBottomColor/statusBarColor 直接改颜色,其他问题默认不处理
 *
 *           日常使用
 *           1.BaseActivity中设置亮色主题(部分页面恢复即可) setStatusBarLightMode()
 *           2.BaseActivity/BaseFragment设置沉浸式状态栏/虚拟按键(或者设置纯色)
 *
 * @补充内容  注意:
 *              1.设置状态栏颜色要写在setContentView(..)后面
 *              2.两个重要属性要写在状态栏要跟随颜色的控件上
 *              3.使用了fitsSystemWindows属性+ScrollView+EditText的布局要注意软键盘问题
 *
 *              (思路,博客讲几种方案说明一下
 *              1.状态栏设置只在Fragment中独立做(ViewPager+Fragment的场景只有第一个Fragment生效)
 *              2.代码动态将顶部控件paddingTop/marginTop状态栏高度,目前测试下来可行的方案,而且这种方案可以不在布局设置fitsSystemWindows属性,防止软键盘问题
 *              3.Activity实现了Fragment的标题栏
 *              4.如果Fragment的状态栏效果是纯色的,可以在该Fragment顶部动态设置同状态栏高度的View
 *              5.DrawerLayout与fragment当作主界面类似,在其显示内容的界面处理即可
 *              6.toolbar直接设置padding有位置显示不正确问题
 *             //todo 小米也已经重归正途了
 *
 *
 *
 *
 * //todo 有时间看下--> 拿到DecorView,看里面源码找到状态栏文字图标属性的方法,然后反射换掉(只是思路)
 *
 *
 *
5.0以上虚拟按键处理??? 在values-v21目录的style文件主题加上以下属性(todo 查资料看下,下面代码用作笔记)
<!-- API 21 theme customizations can go here. -->
        <item name="android:windowTranslucentStatus">true</item>
        <item name="android:windowTranslucentNavigation">false</item>

       同时不要设置这句代码,防止虚拟键挡住布局
       activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
 *
 * ---------------------------------
 *
 *
 */
public class StatusBar {

    /**
     * 直接设置虚拟按键颜色
     *
     * @param activity
     * @param argb
     */
    public static void setNavigationBottomColor(Activity activity, int argb) {
        //判断屏幕是否有虚拟按键
        if (hasNavigationBottom(activity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.0以上API设置虚拟按键颜色
                activity.getWindow().setNavigationBarColor(argb);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //4.4到5.0版本,使用我们的方案设置虚拟按键颜色
                createNavigationBottomView(activity, argb);
            }
        }
    }

    /**
     * 设置沉浸式虚拟按键
     * <p>
     * 给底部第一个布局高度加上状态栏高度
     * <p>
     * 不传布局,需要自己处理虚拟按键覆盖界面问题
     *
     * @param activity
     * @param bottomView
     */
    public static void setTransparentNavigationBottom(Activity activity, View bottomView) {
        if (bottomView == null) {
            setTransparentNavigationBottom(activity);
        } else {
            setTransparentNavigationBottom(activity);
            ViewGroup.LayoutParams layoutParams = bottomView.getLayoutParams();
            layoutParams.height += getNavigationBottomHeight(activity);
            bottomView.setLayoutParams(layoutParams);
        }

    }

    /**
     * 设置透明状态栏(这个就是沉浸式)
     * <p>
     * 注意:需在顶部控件布局中加入以下属性让内容出现在状态栏之下:
     * android:clipToPadding="true"     // true 会贴近上层布局 ; false 与上层布局有一定间隙
     * android:fitsSystemWindows="true"   //true 会保留actionBar,title,虚拟按键的空间 ; false 不保留
     * <p>
     * 这个方法一般用在BaseActivity的onCreate()中,如果单独的Activity,继承该activity,然后重写颜色方法即可
     * <p>
     * Android 4.4以下没API,不处理
     */
    private static void setTransparentStatusBar(Activity activity) {
        //5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  //API16 视图延伸至状态栏区域，状态栏悬浮于视图之上
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE); //保持整个View稳定, 常和控制System UI悬浮, 隐藏的Flags共用, 使View不会因为System UI的变化而重新layout。
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS //状态栏透明
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //虚拟按键透明
            //SDK21 用于去除部分机型默认透明状态栏有黑色阴影背景
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//6.0设置状态栏透明
            //4.4到5.0
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /**
     * 设置透明虚拟按键(这个就是沉浸式)
     * <p>
     * 注意:需在顶部控件布局中加入以下属性让内容出现在状态栏之下:
     * android:clipToPadding="true"     // true 会贴近上层布局 ; false 与上层布局有一定间隙
     * android:fitsSystemWindows="true"   //true 会保留actionBar,title,虚拟按键的空间 ; false 不保留
     * <p>
     * 这个方法一般用在BaseActivity的onCreate()中,如果单独的Activity,继承该activity,然后重写颜色方法即可
     * <p>
     * Android 4.4以下没API,不处理
     */
    private static void setTransparentNavigationBottom(Activity activity) {
        //5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //API16 视图延伸至虚拟按键区域，虚拟按键悬浮于视图之上
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE); //保持整个View稳定, 常和控制System UI悬浮, 隐藏的Flags共用, 使View不会因为System UI的变化而重新layout。
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS //状态栏透明
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //虚拟按键透明
            //SDK21 用于去除部分机型默认透明状态栏有黑色阴影背景
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(Color.TRANSPARENT);//6.0设置虚拟按键透明
            //4.4到5.0
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


    /**
     * 设置透明状态栏(这个就是沉浸式)
     * <p>
     * 给顶部第一个布局高度加上状态栏高度
     * <p>
     * 不传布局,需要自己处理虚拟按键覆盖界面问题
     *
     * @param activity
     * @param topView  顶部延伸到状态栏的布局
     */
    public static void setTransparentStatusBar(Activity activity, View topView) {
        if (topView == null) {
            setTransparentStatusBar(activity);
        } else {
            setTransparentStatusBar(activity);
            ViewGroup.LayoutParams layoutParams = topView.getLayoutParams();
            layoutParams.height += getStatusBarHeight(activity);
            topView.setLayoutParams(layoutParams);
        }
    }


    /**
     * 判断机型,设置状态栏图标主题(只关注状态栏颜色)
     * <p>
     * decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  //API16 视图延伸至状态栏区域，状态栏悬浮于视图之上
     * | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
     * | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR  //API23 设置状态栏为黑色文字,Flag只有在使用了FLAG_DRWS_SYSTEM_BAR_BACKGROUNDS，并且没有使用FLAG_TRANSLUCENT_STATUS时才有效
     * | View.SYSTEM_UI_FLAG_LAYOUT_STABLE); //保持整个View稳定, 常和控制System UI悬浮, 隐藏的Flags共用, 使View不会因为System UI的变化而重新layout。
     */
    public static void setStatusBarLightMode(Activity activity, boolean isLightMode) {
        switch (Build.MANUFACTURER.toUpperCase()) {

            case "MEIZU"://魅族Flyme4+
                setStatusBarDarkIcon(activity, isLightMode);
                break;
            default://默认
                View decorView = activity.getWindow().getDecorView();
                //6.0以上Android版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (isLightMode) {
                        //设置状态栏黑色字体
                        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                } else {
                    //6.0以下版本自定义黑色半透明背景,白色字体
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//恢复状态栏白色字体
                    setStatusBarColor(activity, Color.argb(90, 0, 0, 0));

                }
                break;
        }
    }

    private static void setStatusBarDarkIcon(Activity activity, boolean isLightMode) {
        Window window = activity.getWindow();
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (isLightMode) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);

            } catch (Exception e) {
                Log.e("MeiZu", "setStatusBarDarkIcon: failed");
            }
        }

    }

    /**
     * 获取状态栏高度
     *
     * @param activity 上下文
     * @return 状态栏高度
     */
    @SuppressLint("PrivateApi")
    private static int getStatusBarHeight(Activity activity) {
        int result = -1;
        //5.0才出来的API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int resourceId = activity.getResources()
                    .getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = activity.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        } else { //5.0之前
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                String heightStr = clazz.getField("status_bar_height").get(object).toString();
                result = Integer.parseInt(heightStr);
                //dp--px
                result = activity.getResources().getDimensionPixelSize(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }


    /**
     * 判断状态栏是否存在
     * todo 全屏的情况,后面再加
     *
     * @param activity activity
     * @return true :存在   ;  false: 不存在
     */
    public boolean hasStatusBar(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams
                .FLAG_FULLSCREEN;
    }

    /**
     * 设置状态栏的颜色
     * 1.不具备沉浸式状态栏的功能
     * 2.不需要设置fitsSystemWindows属性和paddingTop高度适配
     * <p>
     * 5.0以上使用getWindow().setStatusBarColor() API
     * 4.4到5.0 添加一个自定义颜色的View覆盖状态栏
     *
     * @param activity
     * @param argb     getResources().getColor(R.color.colorPrimary)
     */
    public static void setStatusBarColor(Activity activity, int argb) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上API设置状态栏颜色
            activity.getWindow().setStatusBarColor(argb);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4到5.0版本,使用我们的方案设置状态栏颜色
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            // 移除半透明矩形,以免叠加
            if (contentView.getChildCount() > 1) {
                contentView.removeViewAt(1);
            }
            contentView.addView(createStatusBarView(activity, argb));
        }
    }

    /**
     * 创建状态栏矩形 View
     *
     * @param argb Color.argb(alpha, 0, 0, 0)  颜色属性
     * @return View
     */
    private static View createStatusBarView(Activity activity, int argb) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(argb);
        return statusBarView;
    }

    /**
     * 获取虚拟按键的高度
     *
     * @return
     */
    @SuppressLint("PrivateApi")
    private static int getNavigationBottomHeight(Activity activity) {
        int height = -1;
        /**5.0之后(包括5.0)的版本**/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int resourceId = activity.getResources()
                    .getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //返回值单位为px
                height = activity.getResources().getDimensionPixelSize(resourceId);
            }
            return height;
        } else {
            //5.0之前的版本
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                String heightStr = clazz.getField("navigation_bar_height").get(object).toString();
                height = Integer.parseInt(heightStr);
                //返回值单位为px
                height = activity.getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return height;
        }
    }

    /**
     * 判断屏幕是否有虚拟按键
     * <p>
     * 原理:
     * 屏幕的真正高度减去屏幕内容显示的高度
     */

    private static boolean hasNavigationBottom(Activity activity) {
        //屏幕的高度  真实物理的屏幕
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(displayMetrics);//真实的屏幕宽高
        }
        int heightDisplay = displayMetrics.heightPixels;
        //为了防止横屏
        int widthDisplay = displayMetrics.widthPixels;
        DisplayMetrics contentDisplaymetrics = new DisplayMetrics();
        display.getMetrics(contentDisplaymetrics);//屏幕内容显示的宽高
        int contentDisplay = contentDisplaymetrics.heightPixels;
        int contentDisplayWidth = contentDisplaymetrics.widthPixels;
        //屏幕内容高度  显示内容的屏幕
        int w = widthDisplay - contentDisplayWidth;
        //哪一方大于0   就有导航栏
        int h = heightDisplay - contentDisplay;
        return w > 0 || h > 0;
    }

    /**
     * 创建虚拟按键矩形 View
     *
     * @param argb Color.argb(alpha, 0, 0, 0)  颜色属性
     * @return View
     */
    private static View createNavigationBottomView(Activity activity, int argb) {
        // 绘制一个和状态栏一样高的矩形
        View navigationBottomView = new View(activity);
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getNavigationBottomHeight(activity));
        navigationBottomView.setLayoutParams(params);
        navigationBottomView.setBackgroundColor(argb);
        return navigationBottomView;
    }


    /**
     * //todo 看情况写在其他类
     * 隐藏虚拟按键
     *
     * @param activity
     */
    public static void setNavBarImmersive(@NonNull final Activity activity) {
        Window window = activity.getWindow();
        View decorView = window.getDecorView();
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        int uiOptions = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        decorView.setSystemUiVisibility(uiOptions);
    }


}

