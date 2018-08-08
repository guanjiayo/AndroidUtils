package zs.xmx;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//TODO 博客的三种方案这里写一遍,重新更新博客,以及重新排版
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBar.setStatusBarColor(this, Color.BLUE);
    }
}
