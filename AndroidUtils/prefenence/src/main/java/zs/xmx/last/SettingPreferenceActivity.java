package zs.xmx.last;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Button;

import java.util.List;

import zs.xmx.R;

/**
 * 使用标头,要继承PreferenceActivity,重写onBuildHeaders()和isValidFragment()
 */
public class SettingPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (hasHeaders()) {  //如有header，则在最下面加一个button。本例无论平板还是phone，都返回true
            Button button = new Button(this);
            button.setText("Exit");
            setListFooter(button);
        }

    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.preference_heads, target);
    }

    /**
     * API 19以上的安全机制,需要重写isValidFragment()
     * <p>
     * 1.直接return true
     * <p>
     * 2.return [YOUR_FRAGMENT_NAME].class.getName().equals(fragmentName);
     *
     * @param fragmentName
     * @return
     */
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }
}
