package zs.xmx.last;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.RingtonePreference;
import android.support.annotation.Nullable;
import android.widget.Toast;

import zs.xmx.R;

/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/5/27
 * @本类描述	  ${TODO}
 * @内容说明   1.实现SharedPreferences.OnSharedPreferenceChangeListener接口,
 *               可以监听任一首选项发生更改时的回调
 *            2.
 * @补充内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */

public class NormalPreferenceFragment extends PreferenceFragment  {

    private RingtonePreference mKey_ringtonePreference;
    private Preference         mKey_preference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.normal_preference);
        //  String preference_headers = getArguments().getString("headers_key");
        // Toast.makeText(getActivity(), preference_headers, Toast.LENGTH_SHORT).show();
        mKey_ringtonePreference = (RingtonePreference) findPreference("key_RingtonePreference");
        /**
         * alarm	4	Alarm sounds.
         * <p>
         * all	7	All available ringtone sounds.
         * <p>
         * notification	2	Notification sounds.
         * <p>
         * ringtone	1	Ringtones.
         */
        int ringtoneType = mKey_ringtonePreference.getRingtoneType();
        boolean showDefault = mKey_ringtonePreference.getShowDefault();
        boolean showSilent = mKey_ringtonePreference.getShowSilent();

        mKey_preference = findPreference("key_Preference");
        mKey_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity(), "Preference 被点击了", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    SharedPreferences.OnSharedPreferenceChangeListener listener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    // listener implementation
                    if (key.equals("key_EditTextPreference")) {
                        Preference connectionPref = findPreference(key);
                        // Set summary to be the user-description for the selected value
                        Preference key_EditTextPreference = findPreference("key_EditTextPreference");
                        key_EditTextPreference.setSummary(sharedPreferences.getString(key, "") + "");
                    }
                }
            };



    @Override
    public void onResume() {
        super.onResume();
        //注册监听
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        //取消监听
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(listener);
    }

}
