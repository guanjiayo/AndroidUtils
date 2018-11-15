package zs.xmx.last;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import androidx.annotation.Nullable;

/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/5/27
 * @本类描述	  自定义Preference(满足内置方法不能满足我们需求的情况)
 * @内容说明   扩展 Preference 类时，您需要执行以下几项重要操作：

             1.指定在用户选择设置时显示的用户界面。
              2.适时保存设置的值。
              3.使用显示的当前（默认）值初始化 Preference。
              4.在系统请求时提供默认值。
              5.如果 Preference 提供自己的 UI（例如对话框），请保存并恢复状态以处理生命周期变更（例如，用户旋转屏幕）。

              说白这个类的操作:就是自定义控件,重写Preference
 * @补充内容
 *
 * ---------------------------------     
 * @更新时间   
 * @新增内容   
 *
 */

public class CustomPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
