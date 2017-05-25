package zs.xmx.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import zs.xmx.R;
import zs.xmx.utils.Logger;


public class PermissionsActivity extends AppCompatActivity {


    private static final String TAG = PermissionsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
    }

    public void RECORD_AUDIO(View view) {
        PermissionsUtils.requestPermission(this, PermissionsUtils.CODE_RECORD_AUDIO, mOnPermissionListener);
    }

    public void ACCOUNTS(View view) {
        PermissionsUtils.requestPermission(this, PermissionsUtils.CODE_GET_ACCOUNTS, mOnPermissionListener);
    }


    public void CALL_PHONE(View view) {
        PermissionsUtils.requestPermission(this, PermissionsUtils.CODE_CALL_PHONE, mOnPermissionListener);
    }

    public void FINE_LOCATION(View view) {
        PermissionsUtils.requestPermission(this, PermissionsUtils.CODE_ACCESS_FINE_LOCATION, mOnPermissionListener);
    }

    public void WRITE_EXTERNAL_STORAGE(View view) {
        PermissionsUtils.requestPermission(this, PermissionsUtils.CODE_WRITE_EXTERNAL_STORAGE, mOnPermissionListener);
    }

    public void camera(View view) {
        PermissionsUtils.requestPermission(this, PermissionsUtils.CODE_CAMERA, mOnPermissionListener);
    }

    public void READ_CALENDAR(View view) {
        PermissionsUtils.requestPermission(this, PermissionsUtils.CODE_READ_CALENDAR, mOnPermissionListener);
    }

    public void SENSORS(View view) {
        PermissionsUtils.requestPermission(this, PermissionsUtils.CODE_BODY_SENSORS, mOnPermissionListener);
    }

    public void SMS(View view) {
        PermissionsUtils.requestPermission(this, PermissionsUtils.CODE_SEND_SMS, mOnPermissionListener);
    }

    public void Mutli(View view) {
        PermissionsUtils.requestMultiPermission(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, mOnPermissionListener);
    }

    public void ALL(View view) {
        PermissionsUtils.requestAllPermissions(this, mOnPermissionListener);
    }

    /**
     * 悬浮窗权限
     * <p>
     * 使用Action Settings.ACTION_MANAGE_OVERLAY_PERMISSION启动隐式Intent
     * <p>
     * 使用"package:" + getPackageName()携带App的包名信息
     * <p>
     * 使用Settings.canDrawOverlays方法判断授权结果
     *
     * @param view
     */
    public void Floating_window(View view) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_Floating_WINDOW);
    }

    /**
     * 系统设置
     * <p>
     * 使用Action Settings.ACTION_MANAGE_WRITE_SETTINGS 启动隐式Intent
     * <p>
     * 使用"package:" + getPackageName()携带App的包名信息
     * <p>
     * 使用Settings.System.canWrite方法检测授权结果
     *
     * @param view
     */
    public void System_Setting(View view) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
    }

    private static final int REQUEST_Floating_WINDOW     = 1;
    private static final int REQUEST_CODE_WRITE_SETTINGS = 2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_Floating_WINDOW) {
            if (Settings.canDrawOverlays(this)) {
                Logger.i(TAG, "onActivityResult granted");
            }
        }
        if (requestCode == REQUEST_CODE_WRITE_SETTINGS) {
            if (Settings.System.canWrite(this)) {
                Logger.i(TAG, "onActivityResult write settings granted");
            }
        }
    }

    PermissionsUtils.OnPermissionListener mOnPermissionListener = new PermissionsUtils.OnPermissionListener() {
        @Override
        public void onPermissionGranted(int requestCode) {
            if (requestCode == PermissionsUtils.CODE_ALL_PERMISSION) {
                Toast.makeText(PermissionsActivity.this, requestCode + " permission Granted", Toast.LENGTH_SHORT).show();
            } else if (requestCode == PermissionsUtils.CODE_Mutil_PERMISSION) {
                Toast.makeText(PermissionsActivity.this, requestCode + " permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PermissionsActivity.this, PermissionsUtils.requestPermissions[requestCode] + " permission Granted", Toast.LENGTH_SHORT).show();

            }
        }
    };

    /**
     * 权限结果回调
     *
     * @param requestCode  权限的请求码
     * @param permissions  权限数组
     * @param grantResults 授权结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        for (int i = 0; i < grantResults.length; i++) {
            boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (isTip) {//表明用户没有彻底禁止弹出权限请求
                    Toast.makeText(this, "没有彻底禁止", Toast.LENGTH_SHORT).show();
                } else {//表明用户已经彻底禁止弹出权限请求
                    //   PermissionMonitorService.start(this);//这里一般会提示用户进入权限设置界面
                    Toast.makeText(this, "彻底禁止", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public void plugin(View view) {
        startActivity(new Intent(this, PermissionsPluginActivity.class));
    }
}
