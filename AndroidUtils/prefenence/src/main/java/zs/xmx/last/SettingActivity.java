package zs.xmx.last;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import zs.xmx.R;

/**
 * 如果不是特需要标头,建议使用这种方式,扩展性更好
 */
public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fl_content, new NormalPreferenceFragment());
        transaction.commit();

    }

    public void toSettingPreferenceActivity(View view) {
        startActivity(new Intent(this, SettingPreferenceActivity.class));
    }

}
