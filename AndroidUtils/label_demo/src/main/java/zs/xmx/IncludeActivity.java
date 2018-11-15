package zs.xmx;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/6/7
 * @本类描述	  Include标签基本使用
 * @内容说明
 * 1. 一个xml布局文件有多个<include>标签需要设置ID,才能找到相应子View的控件,否则只能找到第一个include的layout布局,以及该布局的控件
 * 2. <include>标签如果使用layout_xx属性,会覆盖被include的xml文件根节点对应的layout_xx属性,建议在<include>标签调用的布局设置好宽高位置,防止不必要的bug
 * 3. include 添加id,会覆盖被include的xml文件根节点ID,这里建议<include>和被<include>覆盖的xml文件根节点设置同名的ID,不然有可能会报空指针异常
 * 4. 如果要在<include>标签下使用RelativeLayout,如layout_margin等其他属性,记得要同时设置layout_width和layout_height,不然其它属性会没反应
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class IncludeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_include);

        initView();
    }

    private void initView() {
        //如果include布局根容器和include标签中的id设置的是不同的值，这里获取的mToolbar值将为null
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(mToolbar);

        //普通include标签用法,直接拿子View属性实现
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("不加ID实现的include标签");

        //多个include标签用法,添加ID,findViewByID找到layout,再找子控件
        View view_include = findViewById(R.id.include_text1);
        TextView view_include_textView = (TextView) view_include.findViewById(R.id.textView);
        view_include_textView.setText("加了ID实现的include标签");

        //多个include标签用法,添加ID,findViewByID找到layout,再找子控件
        View view_include_Relative = findViewById(R.id.include_text2);
        TextView view_textView_relative = (TextView) view_include_Relative.findViewById(R.id.textView);
        view_textView_relative.setText("加了ID实现的include标签(RelaviteLayout)");

    }
}
