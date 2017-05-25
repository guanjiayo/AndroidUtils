package zs.xmx.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import zs.xmx.R;

/**
 * Android 6.0 动态权限机制 插件方法
 * <p>
 * PS:
 * 1，执行request.proceed()调用系统申请权限的弹窗；
 * 如果在系统申请弹窗中勾选了不在提示并且拒绝，会调用@OnNeverAskAgain的方法；
 * <p>
 * 2，执行request.cancel()会调用@OnPermissionDenied的方法。
 */
@RuntimePermissions
public class PermissionsPluginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_plugin);
        //代理类检查权限(在要检查权限的业务里调用这个方法)
        PermissionsPluginActivityPermissionsDispatcher.getCameraWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void getCamera() {
        //权限申请成功,在这里写逻辑
    }

    /**
     * 权限回调处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限回调结果交由代理类PermissionsActivityPermissionsDispatcher处理
        PermissionsPluginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("权限申请")
                .setMessage("应用需要使用相机权限，您是否确定要使用")
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("权限申请")
                .setMessage("在设置-应用-当前应用权限中开启相机权限，以正常使用拍照")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //开启设置页
                        startActivity(new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS));
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("权限申请")
                .setMessage("您已禁止不再询问，请前往设置-应用-当前应用权限中开启相机权限，以正常使用拍照")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //开启设置页
                        startActivity(new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS));
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
