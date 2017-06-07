package zs.xmx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/6/7
 * @本类描述	  ${TODO}
 * @内容说明   ${TODO}
 * @补充内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void include(View view) {
        startActivity(new Intent(this, IncludeActivity.class));
    }

    public void merge(View view) {
        startActivity(new Intent(this, MergeActivity.class));

    }

    public void vuewStub(View view) {
        startActivity(new Intent(this, ViewStubActivity.class));
    }
}
