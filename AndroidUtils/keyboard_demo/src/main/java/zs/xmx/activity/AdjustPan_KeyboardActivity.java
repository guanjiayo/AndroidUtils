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
 * @本类描述	  adjustPan(软键盘测试)
 * @内容说明   1. 当控件设置了background属性,或设置了权重的imageView不会发生变形
 *            2. 如果有几个EditText,当点击第一个EditText时,下方的EditText会被软键盘覆盖
 *            3. 如果输入框在软键盘后面,弹出软键盘会把上方布局挤到屏幕外
 *            4. adjustPan不会有失效的情况
 *            5. 父布局不管是线性布局还是相对布局,效果一样
 *
 *
 *
 * @补充内容  //TODO 写一个方法替换根布局
 *
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class AdjustPan_KeyboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题  必须在setContentView()方法之前调用
        setContentView(R.layout.activity_pan);

    }

    public void FullScreen(View view) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
    }

    public void CancelFullScreen(View view) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏

    }
}
