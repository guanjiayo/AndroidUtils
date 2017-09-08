package zs.xmx.dialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.lang.ref.WeakReference;

import zs.xmx.R;
import zs.xmx.utils.KeyboardToggleUtils;


public class KeyboardDialog implements View.OnClickListener {

    private WeakReference<Activity> mActivityWeakReference;

    public KeyboardDialog(Activity activity) {
        mActivityWeakReference = new WeakReference<Activity>(activity);
    }

    private AlertDialog dialog;
    private EditText etInput;

    public void show(){
        final Activity activity = mActivityWeakReference.get();
        if (activity != null) {
            final View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_keyboard, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            etInput = (EditText) dialogView.findViewById(R.id.et_input);
            dialog = builder.setView(dialogView).create();
            dialog.setCanceledOnTouchOutside(false);
            dialogView.findViewById(R.id.btn_hide_soft_input).setOnClickListener(this);
            dialogView.findViewById(R.id.btn_show_soft_input).setOnClickListener(this);
            dialogView.findViewById(R.id.btn_toggle_soft_input).setOnClickListener(this);
            dialogView.findViewById(R.id.btn_close_dialog).setOnClickListener(this);
            dialog.show();
            KeyboardToggleUtils.showSoftInput(activity);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hide_soft_input:
                KeyboardToggleUtils.hideSoftInput(etInput);
                break;
            case R.id.btn_show_soft_input:
                KeyboardToggleUtils.showSoftInput(etInput);
                break;
            case R.id.btn_toggle_soft_input:
                KeyboardToggleUtils.toggleSoftInput();
                break;
            case R.id.btn_close_dialog:
                KeyboardToggleUtils.hideSoftInput(etInput);
                dialog.dismiss();
                break;
        }
    }
}
