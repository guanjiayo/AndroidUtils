package zs.xmx.utils;
/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/4
 * @本类描述   针对adjustResize失效处理类(输入框父布局是NestedScrollView)
 * @内容说明   ${TODO} 全屏模式下有问题
 * @补充内容
 *
 * ---------------------------------     
 * @更新时间   
 * @新增内容   
 *
 */

import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * 注意:
 * 在Activity对NestedScrollView要设置一下滑动
 * <p>
 * NestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {         //scroll为parent外面那层布局（）最好用NestedScrollView，ScrollView会有版本问题
 *
 * @Override public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
 * v.scrollTo(0, 450);     //这个是滑动距离，随便大一点就好
 * }
 * });
 */
public class NestedScrollView_KeyboardUtils {
    private static final String TAG = "NestedScrollView_KeyboardUtils";

    /**
     * @param activity 上下文
     * @param viewId   NestedScrollView_ID
     */
    public static void assistActivity(Activity activity, int viewId) {
        new NestedScrollView_KeyboardUtils(activity, viewId);
    }

    private View             mChildOfContent;
    /**android.support.v4包中,新版的ScrollView**/
    private NestedScrollView mScrollView;

    private NestedScrollView_KeyboardUtils(Activity activity, int viewId) {
        //拿到当前XML文件的根布局
        FrameLayout content = (FrameLayout) activity
                .findViewById(android.R.id.content);
        //监听当前View的状态,进行通知回调,即"软键盘弹出""
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        possiblyResizeChildOfContent();
                    }
                });
        mScrollView = (NestedScrollView) content.findViewById(viewId);
    }

    private void possiblyResizeChildOfContent() {
        int contentHeight = mChildOfContent.getRootView().getHeight();
        int curDisplayHeight = computeUsableHeight();
        if (contentHeight - curDisplayHeight > contentHeight / 4) {
            Log.e(TAG, "possiblyResizeChildOfContent: 1");
            mScrollView.scrollTo(0, 600);
            //                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        } else {
            Log.e(TAG, "possiblyResizeChildOfContent: 2");
        }
    }

    /**
     * 软键盘弹出后,获取屏幕可显示区域高度
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
        return r.height();
    }
}
