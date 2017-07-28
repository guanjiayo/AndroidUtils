package zs.xmx.versiontest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import zs.xmx.mylibrary.PhoneInfo;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhoneInfo info = new PhoneInfo(this);
        Log.d(TAG, "devices id: " + info.getDeviceId());
        Log.d(TAG, "getPhoneModule: " + info.getPhoneModule());
        Log.d(TAG, "getSerialNumber: " + info.getSerialNumber());
        Log.d(TAG, "getPhoneNumber: " + info.getPhoneNumber());
        Log.d(TAG, "getMacAddress: " + info.getMacAddress());
        Log.d(TAG, "getCpuInfo: " + info.getCpuInfo());
        Log.d(TAG, "getTotalMemory: " + info.getTotalMemory());
    }
}
