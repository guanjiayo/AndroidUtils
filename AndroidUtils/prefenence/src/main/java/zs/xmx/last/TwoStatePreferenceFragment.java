package zs.xmx.last;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import androidx.annotation.Nullable;
import android.widget.Toast;

import zs.xmx.R;

/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/5/27
 * @本类描述	  两种状态的首选项
 * @内容说明
 * @补充内容
 *
 * ---------------------------------     
 * @更新时间   
 * @新增内容   
 *
 */

public class TwoStatePreferenceFragment extends PreferenceFragment {

    private CheckBoxPreference mCheckbox_preference;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.two_preference);
        init();
        initEvent();
    }

    private void initEvent() {
        mCheckbox_preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getActivity(), "Checkbox_preference 状态发生改变了", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    private void init() {
        mCheckbox_preference = (CheckBoxPreference) findPreference("key_checkbox_preference");
    }


}
