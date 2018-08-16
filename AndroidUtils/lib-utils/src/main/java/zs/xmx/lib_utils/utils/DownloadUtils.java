package zs.xmx.lib_utils.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/10/14 22:53
 * @本类描述	  下载_相关工具类
 * @内容说明   1.设置下载文件名
 *            2.设置下载通知Notification 显示状态
 *
 * 使用:
 * DownloadUtil downloadUtil = new DownloadUtil(activity, downloadUrl);
 * //下载显示名字，不能是中文
 * downloadUtil.setDownloadFileName("apkName" + System.currentTimeMillis() + ".apk");
 * downloadUtil.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
 * downloadUtil.start();
 *
 * ---------------------------------------------     
 * @更新时间   2016/10/14 
 * @更新说明   ${TODO} 1.后期添加 下载进度/暂停/缓存/取消  (自定义控件)
 *                      2.根据已有的360/谷歌/公司项目继续完善
 */
public class DownloadUtils {

    private Context mContext;
    private String downloadFileName = "weiyan.apk";
    private static long                    myReference;
    private static DownloadManager         downloadManager;
    private        DownloadManager.Request downloadRequest;

    public DownloadUtils(Context context, String downloadUrl) {
        this.mContext = context;
        initDownload(downloadUrl);
    }

    private void initDownload(String downloadUrl) {
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(downloadUrl);//"http://app.mi.com/download/25323"
        downloadRequest = new DownloadManager.Request(uri);
        // 设置目标存储在外部目录，一般位置可以用
        downloadRequest.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, downloadFileName);
        //下载的文件能被其他应用扫描到
        downloadRequest.allowScanningByMediaScanner();
        //设置被系统的Downloads应用扫描到并管理,默认true
        downloadRequest.setVisibleInDownloadsUi(true);
        //限定在WiFi还是手机网络(NETWORK_MOBILE)下进行下载
        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // 设置mime类型，这里看服务器配置，一般国家化的都为utf-8编码。
        downloadRequest.setMimeType("application/vnd.android.package-archive");
        /**
         * 设置notification显示状态
         * Request.VISIBILITY_VISIBLE：在下载进行的过程中，通知栏中会一直显示该下载的Notification，当下载完成时，该Notification会被移除，这是默认的参数值。
         * Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED：在下载过程中通知栏会一直显示该下载的Notification，在下载完成后该Notification会继续显示，直到用户点击该
         * Notification或者消除该Notification。
         * Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION：只有在下载完成后该Notification才会被显示。
         * Request.VISIBILITY_HIDDEN：不显示该下载请求的Notification。如果要使用这个参数，需要在应用的清单文件中加上android.permission
         * .DOWNLOAD_WITHOUT_NOTIFICATION
         */
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        //设置notification的标题
        downloadRequest.setTitle("");
        //设置notification的描述
        downloadRequest.setDescription("");
    }

    public void start() {
        myReference = downloadManager.enqueue(downloadRequest);
    }

    /**
     * 须static，不然在manife注册时报错：java.lang.InstantiationException has no zero argument constructor
     * 或者must be registered and unregistered inside the Parent class
     */
    public static class DownloadManagerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //Notification点击
            if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                String extraID = DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS;
                long[] references = intent.getLongArrayExtra(extraID);
                for (long reference : references)
                    if (reference == myReference) {
                    }
            }
            //下载完成
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (completeDownloadId == myReference) {
                    Cursor cursor = downloadManager.query(new DownloadManager.Query()
                            .setFilterById(completeDownloadId));
                    cursor.moveToFirst();
                    String filePath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    cursor.close();
                    if (filePath != null) {
                        if (filePath.contains(context.getPackageName())) {
                            AppUtils.installApp(context, filePath.trim().substring(7));
                        }
                    } else {
                        Toast.makeText(context, "网络不给力", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * 设置目标存储在外部目录，一般位置可以用
     *
     * @param downloadFileName
     */
    public void setDownloadFileName(String downloadFileName) {
        // 设置目标存储在外部目录，一般位置可以用
        downloadRequest.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, downloadFileName);
    }

    /**
     * 设置notification的标题
     *
     * @param title
     */
    public void setNotificationTitle(CharSequence title) {
        //设置notification的标题
        downloadRequest.setTitle(title);

    }

    /**
     * 设置notification的描述
     *
     * @param description
     */
    public void setNotificationDescription(CharSequence description) {
        //设置notification的描述
        downloadRequest.setDescription(description);
    }

    /**
     * 设置notification显示状态
     * Request.VISIBILITY_VISIBLE：在下载进行的过程中，通知栏中会一直显示该下载的Notification，当下载完成时，该Notification会被移除，这是默认的参数值。
     * Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED：在下载过程中通知栏会一直显示该下载的Notification，在下载完成后该Notification会继续显示，直到用户点击该
     * Notification或者消除该Notification。
     * Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION：只有在下载完成后该Notification才会被显示。
     * Request.VISIBILITY_HIDDEN：不显示该下载请求的Notification。如果要使用这个参数，需要在应用的清单文件中加上android.permission
     * .DOWNLOAD_WITHOUT_NOTIFICATION
     *
     * @param visibility 显示标识
     */
    public void setNotificationVisibility(int visibility) {

        downloadRequest.setNotificationVisibility(visibility);
    }

    public DownloadManager.Request getDownloadRequest() {
        return downloadRequest;
    }
}
