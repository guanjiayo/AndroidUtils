package zs.xmx.lib_utils.manager;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import java.lang.reflect.Method;


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/10/19 2:42
 * @本类描述	  下载相关工具类
 * @内容说明   1.得到下载信息和状态
 *            2.暂停或重新开始下载
 *            3.判断是否有暂停和重新下载的方法
 *            4.得到下载文件路径/uri
 *            5.得到下载暂停或失败的原因
 *
 * 需要权限:
 *        <uses-permission android:name="android.permission.INTERNET" />
 *         <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *
 * 具体Demo:
 * https://github.com/Trinea/android-demo/blob/master/src/cn/trinea/android/demo/DownloadManagerDemo.java
 * ---------------------------------------------     
 * @更新时间   2016/10/19 
 * @更新说明   ${TODO} 多任务下载,下载使用看DownloadUtils的initDownload()和start()
 */
public class DownloadManagerPro {

    public static final Uri    CONTENT_URI           = Uri.parse("content://downloads/my_downloads");
    /**
     * represents downloaded file above api 11 *
     */
    public static final String COLUMN_LOCAL_FILENAME = "local_filename";
    /**
     * represents downloaded file below api 11 *
     */
    public static final String COLUMN_LOCAL_URI      = "local_uri";

    public static final String METHOD_NAME_PAUSE_DOWNLOAD  = "pauseDownload";
    public static final String METHOD_NAME_RESUME_DOWNLOAD = "resumeDownload";

    private static boolean isInitPauseDownload  = false;
    private static boolean isInitResumeDownload = false;

    private static Method pauseDownload  = null;
    private static Method resumeDownload = null;

    private DownloadManager downloadManager;

    public DownloadManagerPro(DownloadManager downloadManager) {
        this.downloadManager = downloadManager;
    }

    /**
     * 得到下载状态
     *
     * @param downloadId
     * @return
     */
    public int getStatusById(long downloadId) {
        return getInt(downloadId, DownloadManager.COLUMN_STATUS);
    }

    /**
     * 得到下载进度信息
     *
     * @param downloadId
     * @return a int array with two elements
     * <ul>
     * <li>result[0] represents downloaded bytes, This will initially be
     * -1.</li>
     * <li>result[1] represents total bytes, This will initially be -1.</li>
     * </ul>
     */
    public int[] getDownloadBytes(long downloadId) {
        int[] bytesAndStatus = getBytesAndStatus(downloadId);
        return new int[]{bytesAndStatus[0], bytesAndStatus[1]};
    }

    /**
     * 得到下载进度信息和状态
     *
     * @param downloadId
     * @return a int array with three elements
     * <ul>
     * <li>result[0] represents downloaded bytes, This will initially be
     * -1.</li>
     * <li>result[1] represents total bytes, This will initially be -1.</li>
     * <li>result[2] represents download status, This will initially be
     * 0.</li>
     * </ul>
     */
    public int[] getBytesAndStatus(long downloadId) {
        int[] bytesAndStatus = new int[]{-1, -1, 0};
        DownloadManager.Query query = new DownloadManager.Query()
                .setFilterById(downloadId);
        Cursor c = null;
        try {
            c = downloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c
                        .getInt(c
                                .getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                bytesAndStatus[1] = c
                        .getInt(c
                                .getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                bytesAndStatus[2] = c.getInt(c
                        .getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return bytesAndStatus;
    }

    /**
     * 暂停下载
     *
     * @param ids the IDs of the downloads to be paused
     * @return the number of downloads actually paused, -1 if exception or
     * method not exist
     */
    public int pauseDownload(long... ids) {
        initPauseMethod();
        if (pauseDownload == null) {
            return -1;
        }

        try {
            return (Integer) pauseDownload.invoke(downloadManager, ids);
        } catch (Exception e) {
            /**
             * accept all exception, include ClassNotFoundException,
             * NoSuchMethodException, InvocationTargetException,
             * NullPointException
             */
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 重新开始下载
     *
     * @param ids the IDs of the downloads to be resumed
     * @return the number of downloads actually resumed, -1 if exception or
     * method not exist
     */
    public int resumeDownload(long... ids) {
        initResumeMethod();
        if (resumeDownload == null) {
            return -1;
        }

        try {
            return (Integer) resumeDownload.invoke(downloadManager, ids);
        } catch (Exception e) {
            /**
             * accept all exception, include ClassNotFoundException,
             * NoSuchMethodException, InvocationTargetException,
             * NullPointException
             */
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 判断暂停和重新下载的方法是否存在
     *
     * @return boolean
     */
    public static boolean isExistPauseAndResumeMethod() {
        initPauseMethod();
        initResumeMethod();
        return pauseDownload != null && resumeDownload != null;
    }

    private static void initPauseMethod() {
        if (isInitPauseDownload) {
            return;
        }

        isInitPauseDownload = true;
        try {
            pauseDownload = DownloadManager.class.getMethod(
                    METHOD_NAME_PAUSE_DOWNLOAD, long[].class);
        } catch (Exception e) {
            // accept all exception
            e.printStackTrace();
        }
    }

    private static void initResumeMethod() {
        if (isInitResumeDownload) {
            return;
        }

        isInitResumeDownload = true;
        try {
            resumeDownload = DownloadManager.class.getMethod(
                    METHOD_NAME_RESUME_DOWNLOAD, long[].class);
        } catch (Exception e) {
            // accept all exception
            e.printStackTrace();
        }
    }

    /**
     * 得到下载文件路径
     *
     * @param downloadId
     * @return
     */
    public String getFilePath(long downloadId) {
        return getString(
                downloadId,
                (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? COLUMN_LOCAL_URI
                        : COLUMN_LOCAL_FILENAME));
    }

    /**
     * 得到下载url
     *
     * @param downloadId
     * @return
     */
    public String getUri(long downloadId) {
        return getString(downloadId, DownloadManager.COLUMN_URI);
    }

    /**
     * 得到下载失败或暂停原因
     *
     * @param downloadId
     * @return <ul>
     * <li>if status of downloadId is
     * {@link DownloadManager#STATUS_PAUSED}, return
     * {@link #getPausedReason(long)}</li>
     * <li>if status of downloadId is
     * {@link DownloadManager#STATUS_FAILED}, return
     * {@link #getErrorCode(long)}</li>
     * <li>if status of downloadId is neither
     * {@link DownloadManager#STATUS_PAUSED} nor
     * {@link DownloadManager#STATUS_FAILED}, return 0</li>
     * </ul>
     */
    public int getFailorPauseReason(long downloadId) {
        return getInt(downloadId, DownloadManager.COLUMN_REASON);
    }

    /**
     * 得到下载暂停原因
     *
     * @param downloadId
     * @return <ul>
     * <li>if status of downloadId is
     * {@link DownloadManager#STATUS_PAUSED}, return one of
     * {@link DownloadManager#PAUSED_WAITING_TO_RETRY}<br/>
     * {@link DownloadManager#PAUSED_WAITING_FOR_NETWORK}<br/>
     * {@link DownloadManager#PAUSED_QUEUED_FOR_WIFI}<br/>
     * {@link DownloadManager#PAUSED_UNKNOWN}</li>
     * <li>else return {@link DownloadManager#PAUSED_UNKNOWN}</li>
     * </ul>
     */
    public int getPausedReason(long downloadId) {
        return getInt(downloadId, DownloadManager.COLUMN_REASON);
    }

    /**
     * 得到下载错误吗
     *
     * @param downloadId
     * @return one of {@link DownloadManager#ERROR_*}
     */
    public int getErrorCode(long downloadId) {
        return getInt(downloadId, DownloadManager.COLUMN_REASON);
    }

    public static class RequestPro extends DownloadManager.Request {

        public static final String METHOD_NAME_SET_NOTI_CLASS  = "setNotiClass";
        public static final String METHOD_NAME_SET_NOTI_EXTRAS = "setNotiExtras";

        private static boolean isInitNotiClass  = false;
        private static boolean isInitNotiExtras = false;

        private static Method setNotiClass  = null;
        private static Method setNotiExtras = null;

        /**
         * @param uri the HTTP URI to download.
         */
        public RequestPro(Uri uri) {
            super(uri);
        }

        /**
         * set noti class, only init once
         *
         * @param className full class name
         */
        public void setNotiClass(String className) {
            synchronized (this) {

                if (!isInitNotiClass) {
                    isInitNotiClass = true;
                    try {
                        setNotiClass = DownloadManager.Request.class.getMethod(
                                METHOD_NAME_SET_NOTI_CLASS, CharSequence.class);
                    } catch (Exception e) {
                        // accept all exception
                        e.printStackTrace();
                    }
                }
            }

            if (setNotiClass != null) {
                try {
                    setNotiClass.invoke(this, className);
                } catch (Exception e) {
                    /**
                     * accept all exception, include ClassNotFoundException,
                     * NoSuchMethodException, InvocationTargetException,
                     * NullPointException
                     */
                    e.printStackTrace();
                }
            }
        }

        /**
         * set noti extras, only init once
         *
         * @param extras
         */
        public void setNotiExtras(String extras) {
            synchronized (this) {

                if (!isInitNotiExtras) {
                    isInitNotiExtras = true;
                    try {
                        setNotiExtras = DownloadManager.Request.class
                                .getMethod(METHOD_NAME_SET_NOTI_EXTRAS,
                                        CharSequence.class);
                    } catch (Exception e) {
                        // accept all exception
                        e.printStackTrace();
                    }
                }
            }

            if (setNotiExtras != null) {
                try {
                    setNotiExtras.invoke(this, extras);
                } catch (Exception e) {
                    /**
                     * accept all exception, include ClassNotFoundException,
                     * NoSuchMethodException, InvocationTargetException,
                     * NullPointException
                     */
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * get string column
     *
     * @param downloadId
     * @param columnName
     * @return
     */
    private String getString(long downloadId, String columnName) {
        DownloadManager.Query query = new DownloadManager.Query()
                .setFilterById(downloadId);
        String result = null;
        Cursor c = null;
        try {
            c = downloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                result = c.getString(c.getColumnIndex(columnName));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }

    /**
     * get int column
     *
     * @param downloadId
     * @param columnName
     * @return
     */
    private int getInt(long downloadId, String columnName) {
        DownloadManager.Query query = new DownloadManager.Query()
                .setFilterById(downloadId);
        int result = -1;
        Cursor c = null;
        try {
            c = downloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                result = c.getInt(c.getColumnIndex(columnName));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }
}
