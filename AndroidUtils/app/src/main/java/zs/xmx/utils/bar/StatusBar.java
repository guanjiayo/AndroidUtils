package zs.xmx.utils.bar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

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
 *              3.todo 虚拟键怎么处理
 *              4.todo 状态栏文字颜色,根据主题变化而变化(5.0以下统一使用渐变阴影加白色文字?)
 *              5.todo fragment下的沉浸式处理
 *
 *              //TODO 新增状态栏颜色跟随include的ActionBar颜色
 *              //TODO setStatusBarColor 怎么导R.color文件
 *
 *
 * //todo 状态栏颜色,小米和魅族提供了API,我们再判断个原生的就算了
 * //todo 或者拿到DecorView,看里面源码找到状态栏文字图标属性的方法,然后反射换掉(只是思路)
 *
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
     * android:clipToPadding="true"     // true 会贴近上层布局 ; false 与上层布局有一定间隙
     * android:fitsSystemWindows="true"   //true 会保留actionBar,title,虚拟按键的空间 ; false 不保留
     *
     * todo 使用了我们完美方案之后就不需要再设置fitsSystemWindows,不然会没效果
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
             * activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
             */

        } else {
            //todo 4.4以下,不处理其实也可以
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    @SuppressLint("PrivateApi")
    private static int getStatusBarHeight(Context context) {
        int result = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int resourceId = context.getResources()
                    .getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        } else { //5.0之前
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                String heightStr = clazz.getField("status_bar_height").get(object).toString();
                result = Integer.parseInt(heightStr);
                //dp--px
                result = context.getResources().getDimensionPixelSize(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
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
     * @param argb     getResources().getColor(R.color.colorPrimary)
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setStatusBarColor(Activity activity, int argb) {
        setTransparentStatusBar(activity);
        addStatusBarView(activity, argb);
        handlerNavigationBottom(activity);
    }

    /**
     * 处理如果有虚拟按键的情况
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void handlerNavigationBottom(Activity activity) {
        //如果有虚拟按键
        if (hasNavigationBottom(activity)) {
            //            ViewGroup.LayoutParams layoutParams=bottomView.getLayoutParams();
            //            layoutParams.height+=getNavigationBottomHeight();
            //            bottomView.setLayoutParams(layoutParams);
            //            bottomView.setBackgroundColor(styleColor);
        }
    }

    /**
     * 获取虚拟按键的高度
     * //todo 版本判断一下
     *
     * @return
     */
    @SuppressLint("PrivateApi")
    private static int getNavigationBottomHeight(Activity activity) {
        int height = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField("navigation_bar_height").get(object).toString();
            height = Integer.parseInt(heightStr);
            //dp--px
            height = activity.getResources().getDimensionPixelSize(height);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        return height;
    }

    /**
     * 判断屏幕是否有虚拟按键
     *
     * @param activity
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static boolean hasNavigationBottom(Activity activity) {
        //屏幕的高度  真实物理的屏幕
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);//真实的屏幕宽高
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
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(argb);
        return statusBarView;
    }


    /**
     * 5.0  4.4
     * //todo 测试一下与上面的哪个方案好
     * //todo 这边是传控件,跟随控件沉浸式
     *
     * @param topView
     * @param styleColor
     */
    public void setToolBarStyle(View topView, View bottomView, int styleColor) {
        //        5.0  4.4
        //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        //                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        //                if (topView != null) {
        //                    //                ViewGroup.LayoutParams layoutParams=topView.getLayoutParams();
        //                    //                getResources().getDimension(android.R.dimen.s)
        //                    int statusHeight=getStatusHeight();
        //                    Log.i("barry", "   statusHeight  " + statusHeight);
        //                    //
        //                    //                layoutParams.height=statusHeight;
        //                    //                topView.setBackgroundColor(Color.GREEN);
        //
        //                    //第二种
        //                    topView.setPadding(0,toolbar.getPaddingTop()+statusHeight,0,0);
        //                    //下面的导航栏
        //                    if (haveNavgtion()) {
        //                        ViewGroup.LayoutParams layoutParams=bottomView.getLayoutParams();
        //                        layoutParams.height+=getNavigationHeight();
        //                        Log.i("barry", "getNavigationHeight  " + getNavigationHeight());
        //                        bottomView.setLayoutParams(layoutParams);
        //                        bottomView.setBackgroundColor(styleColor);
        //
        //                    }
        //
        //
        //
        //                }
        //
        //            }else if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP) {
        //
        //
        //            }else {
        //                //没救了
        //            }
        //


    }

}
