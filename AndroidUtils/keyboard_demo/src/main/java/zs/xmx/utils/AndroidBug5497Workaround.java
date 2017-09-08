package zs.xmx.utils;
/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/1
 * @本类描述	  网上针对adjustResize全屏模式失效处理类
 *
 * @内容说明   本类经过几天测试,在WebView或者多个EditText控价的页面并没什么用,
 *           不过可以用作借鉴如何绘制我们想要显示的内容在软键盘界面上方
 *
 *
 *
 * @补充内容
 *
 *
 * ---------------------------------     
 * @更新时间   
 * @新增内容   
 *
 */

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class AndroidBug5497Workaround {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity(Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private View                     mChildOfContent;
    private int                      usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private AndroidBug5497Workaround(Activity activity) {

        //拿到当前XML文件的根布局
        FrameLayout content = (FrameLayout) activity.
                findViewById(android.R.id.content);

        //监听当前View的状态,进行通知回调,即"软键盘弹出""
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    /**
     * 重新设置高度
     * <p>
     * 把界面高度设置为可用高度
     */
    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            // int usableHeightSansKeyboard = activity.getWindowManager().getDefaultDisplay().getHeight();//获取屏幕尺寸，不包括虚拟功能高度 用这个可以完美解决
            //findViewById(android.R.id.content).getMeasuredHeight() 也可以解决虚拟按键问题
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            //排除其他View引起的变化,专注软键盘变化
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 软键盘弹出后,可以显示内容的高度
     *
     * @return
     */
    private int computeUsableHeight() {
        Rect r = new Rect();
        //这行代码能够获取到去除标题栏和被软键盘挡住的部分,所剩下的矩形区域
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        //r.top : 标题栏的高度
        //屏幕高度-r.bottom : 软键盘的高度
        //可用高度(全屏模式) : rect.bottom
        //可用高度(非全屏模式) : rect.bottom - rect.top
        return (r.bottom - r.top);// 全屏模式下： return r.bottom
    }

}
