package zs.xmx.todo;
/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/4/8
 * @本类描述	  软引用方式管理多张图片
 * @内容说明   //TODO 归类到BitmapUtils
 * @补充内容
 *
 * ---------------------------------     
 * @更新时间   
 * @新增内容   
 *
 */

import android.graphics.Bitmap;
import android.os.Build;
import android.util.ArrayMap;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class BitmapCache {
    static private BitmapCache cache;

    private ArrayMap<String, MySoftRef> hashRefs;
    //垃圾引用的队列(所引用的对象已经被回收,则将该引用存入队列)
    private ReferenceQueue<Bitmap>      mBitmapReferenceQueue;

    /**
     * 继承SoftReference,是的每一个实例都具有可识别的标识
     */
    private class MySoftRef extends SoftReference<Bitmap> {
        private String _key = "";

        public MySoftRef(Bitmap referent, ReferenceQueue<Bitmap> q, String key) {
            super(referent, q);
            _key = key;
        }
    }

    private BitmapCache() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            hashRefs = new ArrayMap<String, MySoftRef>();
        }
        mBitmapReferenceQueue = new ReferenceQueue<Bitmap>();
    }

    public static BitmapCache getInstance() {
        if (cache == null) {
            cache = new BitmapCache();
        }
        return cache;
    }

    /**
     * 以软引用的方式对一个Bitmap对象的实例进行引用并保存该引用
     */
    public void addCacheBitmap(Bitmap bmp, String key) {
        cleanCache();//清除垃圾引用
        MySoftRef ref = new MySoftRef(bmp, mBitmapReferenceQueue, key);
        hashRefs.put(key, ref);
    }

    /**
     * 依据所指定的drawable下的图片资源ID号,
     * (可以按需求从网络或本地获取),
     * 重新获取相应Bitmap对象的实例
     */
    //public Bitmap getBitmap(int resId, Context context)
    public Bitmap getBitmap(int resId) {
        Bitmap bmp = null;
        //缓存中是否有该Bitmap实例的引用,如果用,从软引用中取
        try {
            if (hashRefs.containsKey(resId)) {
                MySoftRef ref = (MySoftRef) hashRefs.get(resId);
                bmp = (Bitmap) ref.get();
            }
            return bmp;
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void cleanCache() {
        MySoftRef ref = null;
        while ((ref = (MySoftRef) mBitmapReferenceQueue.poll()) != null) {
            hashRefs.remove(ref._key);
        }
    }

    /**
     * 清除Cache内的全部内容
     */
    public void clearCach() {
        cleanCache();
        hashRefs.clear();
        System.gc();
        System.runFinalization();
    }
}
