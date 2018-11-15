package zs.xmx.lib_utils.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import zs.xmx.lib_utils.R;
import zs.xmx.lib_utils.constant.PermissionsContants;
import zs.xmx.lib_utils.utils.Logger;

import static android.provider.Settings.canDrawOverlays;


public class PermissionsActivity extends AppCompatActivity {


    private static final String TAG = PermissionsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
    }

    public void RECORD_AUDIO(View view) {
        PermissionsUtils.requestPermission(this, PermissionsContants.CODE_RECORD_AUDIO, mOnPermissionListener);
    }

    public void ACCOUNTS(View view) {
        PermissionsUtils.requestPermission(this, PermissionsContants.CODE_GET_ACCOUNTS, mOnPermissionListener);
    }


    public void CALL_PHONE(View view) {
        PermissionsUtils.requestPermission(this, PermissionsContants.CODE_CALL_PHONE, mOnPermissionListener);
    }

    public void FINE_LOCATION(View view) {
        PermissionsUtils.requestPermission(this, PermissionsContants.CODE_ACCESS_FINE_LOCATION, mOnPermissionListener);
    }

    public void WRITE_EXTERNAL_STORAGE(View view) {
        PermissionsUtils.requestPermission(this, PermissionsContants.CODE_WRITE_EXTERNAL_STORAGE, mOnPermissionListener);
    }

    public void camera(View view) {
        PermissionsUtils.requestPermission(this, PermissionsContants.CODE_CAMERA, mOnPermissionListener);
    }

    public void READ_CALENDAR(View view) {
        PermissionsUtils.requestPermission(this, PermissionsContants.CODE_READ_CALENDAR, mOnPermissionListener);
    }

    public void SENSORS(View view) {
        PermissionsUtils.requestPermission(this, PermissionsContants.CODE_BODY_SENSORS, mOnPermissionListener);
    }

    public void SMS(View view) {
        PermissionsUtils.requestPermission(this, PermissionsContants.CODE_SEND_SMS, mOnPermissionListener);
    }

    public void Mutli(View view) {
        PermissionsUtils.requestMultiPermission(this,
                new String[]{Manifest.permission.CALL_PHONE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,},
                mOnPermissionListener);
    }

    public void Group(View view) {

        PermissionsUtils.requestPermissionGroup(this, PermissionsContants.LOCATION_GROUP);

    }

    public void ALL(View view) {
        PermissionsUtils.requestAllPermissions(this, mOnPermissionListener);
    }

    /**
     * 悬浮窗权限
     * <p>
     * 引导用用户去设置页面设置
     * <p>
     * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 判断是否有SYSTEM_ALERT_WINDOW权限
            if (!Settings.canDrawOverlays(this)) {
                // 申请SYSTEM_ALERT_WINDOW权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_Floating_WINDOW);
            } else {
                //doSomething
            }
        }
    }

    /**
     * 系统设置
     * <p>
     * 引导用用户去设置页面设置
     * <p>
     * <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断是否有WRITE_SETTINGS权限
            if (!Settings.System.canWrite(this)) {
                //申请WRITE_SETTINGS权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
            } else {
                //doSomething
            }
        }

    }

    private static final int REQUEST_Floating_WINDOW     = 1;
    private static final int REQUEST_CODE_WRITE_SETTINGS = 2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_Floating_WINDOW) {
            if (canDrawOverlays(this)) {
                Logger.i(TAG, "onActivityResult Floating_WINDOW granted");
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
            if (requestCode == 100) {
                Toast.makeText(PermissionsActivity.this, requestCode + " permission Granted", Toast.LENGTH_SHORT).show();
            } else if (requestCode == 200) {
                Toast.makeText(PermissionsActivity.this, requestCode + " permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PermissionsActivity.this, PermissionsContants.requestPermissions[requestCode] + " permission Granted", Toast.LENGTH_SHORT).show();

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
        /**实际开发不用写这个,我们测试监听而已**/
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
