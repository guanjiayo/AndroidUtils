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
 * @本类描述	  ScrollView 嵌套布局(软键盘测试)
 * @内容说明   1. 用ScrollView嵌套的布局,当弹出软键盘时,ScrollView布局上移,但不会挤出屏幕外
 *            2. xml布局文件不能直接把宽高之类的参数定死
 *            3. ScrollView : 弹出软键盘时,可以全部滑动
 *            4. ScrollView + adjustPan : 弹出软键盘时,只能滑动部分,若输入框在界面下方时,标题栏会被挤出屏幕外
 *            5. ScrollView + adjustResize : 弹出软键盘时,可以全部滑动,点击输入框,会自动调整到合适的位置
 *            6. 全屏模式下,ScrollView + adjustResize失效,效果会变成 ScrollView + adjustPan
 *            7. 使用ScrollView,不存在下方EditText被覆盖问题,因为可以滑动
 *
 * @补充内容  //TODO 写一个可以切换根布局的方法
 *
 *
 *
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class Scroll_keyboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题  必须在setContentView()方法之前调用
        setContentView(R.layout.activity_scroll);


    }

    public void FullScreen(View view) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
    }

    public void CancelFullScreen(View view) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏

    }

    public void AdjustPan(View view) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void AdjustResize(View view) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
