package zs.xmx.lib_utils.utils.sp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import androidx.collection.SimpleArrayMap;


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @本类描述	  SharedPreferences 工具类
 * @内容说明   SharedPreferences 功能的封装
 *            1.apply是异步的,commit是同步的
 *            2.apply是API>9出现的,用SharedPreferencesCompat适配
 *      ---------------------------------------------
 *
 *             使用前,要在基类(BaseActivity,BaseApplication)先实例化(上下文用全局)
 *             SPUtils.getInstant(Context context, String spName);
 *             调用:
 *             SPUtils.PutXX()
 *
 *    * todo 改造成Kotlin,把object 移除掉,
 * todo getParam 默认值在这里直接给了,后面不再处理
 */
public class SPUtils {

    private static SharedPreferences               sp;
    private static SharedPreferences.Editor        editor; //编辑器
    private static SimpleArrayMap<String, SPUtils> SP_UTILS_MAP = new SimpleArrayMap<>();

    @SuppressLint("CommitPrefEdits")
    private SPUtils(Context context, String spName) {
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sp.edit();

    }

    /*
     * 获取SP实例
     *
     * @param spName sp名
     * @return {@link SPUtils
     */
    public static SPUtils getInstance(Context context, String spName) {
        if (isSpace(spName))
            //默认SP库名称
            spName = "share_data";
        SPUtils spUtils = SP_UTILS_MAP.get(spName);
        if (spUtils == null) {
            spUtils = new SPUtils(context, spName);
            SP_UTILS_MAP.put(spName, spUtils);
        }
        return spUtils;
    }

    /**
     * 获取 SharedPreferences 路径
     * 一般是 /data/data/com.xxx.xxx/shared_prefs
     *
     * @return SharedPreferences 路径
     */
    public static String getSharedPrefs_Path(Context context) {
        return context.getFilesDir().getParent() + File.separator + "shared_prefs";
    }

    /**
     * 根据类型保存数据到SP中
     *
     * @param key    键
     * @param object 值
     */
    public static void putParam(@NotNull String key, @NotNull Object object) {

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Set) {
            editor.putStringSet(key, (Set<String>) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 根据类型拿到SP中对应的值
     *
     * @param key           键
     * @param defaultObject 默认值
     * @return 键对应的值
     * <p>
     * //如果要往Set集合村多个数据,需要在新的集合添加值,否则会报错
     * HashSet<String> hashSet = (HashSet<String>) SPUtils.get(mContext, Constant.HISTORYTEXT, new HashSet<String>());
     * //关键操作 需要在新的集合添加值 然后再提交修改
     * Set<String> changeData = new HashSet<>(hashSet);
     * changeData.add(key);
     * boolean isSuccess = SPUtils.putCommit(mContext, Constant.HISTORYTEXT, changeData);
     */
    public static Object getParam(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Set) {
            return sp.getStringSet(key, (Set<String>) defaultObject);
        } else {
            return sp.getString(key, null);
        }

    }

    /**
     * Set 数据
     * <p>
     * 如果需要保存不会重复,而且不需要顺序,可以使用这种方式
     */
    public static void putStringSet(String key, @NotNull Object object) {

    }


    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(String key) {
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        private static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

    /**
     * 判断输入的文件名不能为空
     */
    private static boolean isSpace(final String str) {
        if (str == null)
            return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
