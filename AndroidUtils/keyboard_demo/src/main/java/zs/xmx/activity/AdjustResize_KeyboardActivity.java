package zs.xmx.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import zs.xmx.R;

/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/3
 * @本类描述	  adjustResize(软键盘测试)
 * @内容说明   1. "全屏模式"下,adjustResize失效
 *            2. 设置了沉浸式状态栏但不设置fitSystemWindow为true时,adjustResize失效
 *            3. 弹出软键盘时不会将上方布局挤出屏幕外
 *            4. 当控件设置了background属性,或设置了权重的imageView会发生变形
 *            5. 当使用LinearLayout作为输入框的父布局时,软键盘会覆盖输入框
 *
 * @补充内容  //TODO 写一个方法替换根布局
 *           官方认为的"全屏模式":
 *           全屏模式指的是App自己接管了状态栏的控制,
 *           如使用了Fullscreen主题、使用了『状态色着色』、『沉浸式状态栏』、『Immersive Mode』等等
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class AdjustResize_KeyboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题  必须在setContentView()方法之前调用
        setContentView(R.layout.activity_resize);


    }


    public void FullScreen(View view) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
    }

    public void CancelFullScreen(View view) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏

    }
}
