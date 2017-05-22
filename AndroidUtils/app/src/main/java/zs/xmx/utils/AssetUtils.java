package zs.xmx.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/10/19 0:46
 * @本类描述	  获取Asset资源_工具类
 * @内容说明   1.获取Asset中的文件(可以带层级)
 *            2.获取Asset中的Raw,sound,drawable 资源(String,color 资源不能获取)
 *            3.获取Asset中的只能读数据库/可读写数据库
 * ---------------------------------------------     
 * @更新时间   2016/10/19 
 * @更新说明
 */
public class AssetUtils {
    /**
     * 防止被实例化
     */
    private AssetUtils() {

    }

    /**
     * 获取Assets中的文件
     *
     * @param context  上下文
     * @param fileName 文件名(可以带层级的)
     * @return
     */
    public static String geFileFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        StringBuilder s = new StringBuilder("");
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取Assets中的 raw ,drawble, sound资源
     * <p>
     * String 和 color 资源不能获取
     *
     * @param context 上下文
     * @param resId   资源ID
     * @return
     */
    public static String geFileFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取Assets中的文件
     *
     * @param context  上下文
     * @param fileName 文件名(可以带层级的)
     * @return
     */
    public static List<String> geFileToListFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取Assets中的 raw ,drawble, sound资源
     * <p>
     * String 和 color 资源不能获取
     *
     * @param context 上下文
     * @param resId   资源ID
     * @return
     */
    public static List<String> geFileToListFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        BufferedReader reader;
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            reader = new BufferedReader(in);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create and/or open a database that will be used for reading and writing.
     *
     * @return SQLiteDatabase对象
     * @throws RuntimeException if cannot copy database from assets
     * @throws SQLiteException  if the database cannot be opened
     */
    public synchronized SQLiteDatabase getWritableDatabase(Context context, String databaseNme) {
        File dbFile = context.getDatabasePath(databaseNme);
        if (dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(dbFile, context, databaseNme);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        assert dbFile != null;
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * Create and/or open a database that will be used for reading only.
     *
     * @return SQLiteDatabase对象
     * @throws RuntimeException if cannot copy database from assets
     * @throws SQLiteException  if the database cannot be opened
     */
    public synchronized SQLiteDatabase getReadableDatabase(Context context, String databaseNme) {
        File dbFile = context.getDatabasePath(databaseNme);
        if (dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(dbFile, context, databaseNme);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                SQLiteDatabase.OPEN_READONLY);
    }

    private void copyDatabase(File dbFile, Context context, String databaseNme) throws IOException {
        InputStream stream = context.getAssets().open(databaseNme);
        FileUtils.writeFileFromIS(dbFile.getPath(), stream, false);
        stream.close();
    }

}
