package zs.xmx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

//TODO 博客的三种方案这里写一遍,重新更新博客,以及重新排版
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   StatusBar.setStatusBarColor(this, Color.BLUE);
        // TextView tv_topView = (TextView) findViewById(R.id.tv_topView);
        LinearLayout ll_topView = (LinearLayout) findViewById(R.id.ll_topView);
        StatusBar.setTransparentStatusBar(this, null);
        //StatusBar.setNavigationBottomColor(this, Color.BLUE);


    }
}
