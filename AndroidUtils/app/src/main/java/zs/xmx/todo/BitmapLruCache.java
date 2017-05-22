package zs.xmx.todo;
/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/4/8
 * @本类描述	  Lru算法管理多张图片
 * @内容说明   //TODO 归类到BitmapUtils
 * @补充内容
 *
 * ---------------------------------     
 * @更新时间   
 * @新增内容   
 *
 */

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class BitmapLruCache {
    private static final String              TAG   = "xmx";
    //容量8bit,填充比0.75,true表示使用LRU算法
    //LinkedHashMap线程不安全,因此要在外部加个同步
    private              Map<String, Bitmap> cache = Collections.synchronizedMap(
            new LinkedHashMap<String, Bitmap>(8, 0.75f, true));
    private              long                size  = 0; //存 当前内存大小
    private              long                limit = 1000000; //限制最后内存 bytes

    public BitmapLruCache() {
        //使用App允许的 25% 可用内存大小
        setLimit(Runtime.getRuntime().maxMemory() / 4);
    }

    private void setLimit(long new_limit) {
        limit = new_limit;
        Log.i(TAG, "MemoryCache will use up to" + limit / 1024. / 1024. + "MB");
    }

    /**
     * 获取图片
     */
    public Bitmap get(String id) {
        try {
            if (!cache.containsKey(id))
                return null;
            return cache.get(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void put(String id, Bitmap bitmap) {
        try {
            if (cache.containsKey(id))
                size -= getSizeInBytes(cache.get(id));//- 就是将原来的图片删掉
            cache.put(id, bitmap);
            size += getSizeInBytes(bitmap);//+ 将新的图片输入
            checkSize();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null)
            return 0;
        //行子节*高度(低版本)
        //高版本 bitmap.getByteCount();
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 拿到使用最少的图片对象,然后remove掉
     */
    private void checkSize() {
        Log.i(TAG, "Cache size=" + size + "length=" + cache.size());
        if (size > limit) {
            Iterator<Map.Entry<String, Bitmap>> iterator = cache.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Bitmap> entry = iterator.next();
                size -= getSizeInBytes(entry.getValue());
                iterator.remove();
                if (size <= limit)
                    break;
            }
            Log.i(TAG, "Clean size,New size" + cache.size());
        }
    }

    public void clear() {
        cache.clear();

    }

}
