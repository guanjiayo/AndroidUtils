package zs.xmx.lib_utils.todo;
/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/4/7
 * @本类描述	  图片处理(防止内存溢出)
 * @内容说明   慕课网防止图片内存溢出
 *            三要素:
 *            缩放比例、解码格式、局部加载
 *            配合软引用BitmapCache类,或Lru算法 BitmapLruCache 优化效果更好
 * @补充内容
 * //todo 这里总结归类到BitmapUtils
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;

import zs.xmx.lib_utils.R;


public class BitmapHandleDemo extends Activity {
    private ImageView mImageView;
    private Bitmap    mBitmap = null;
    private File      mFile   = null;
    private int       SCREEN_WIDTH, SCREEN_HEIGHT;
    private int    shiftpx = 0; //偏移量
    private Button mBtn_choosepic;
    private Button mBtn_change_option;
    private Button mBtn_change_rgb;
    private Button mBtn_partload;
    private Button mBtn_offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_handle);
        initView();
        initEvent();

    }

    private void initEvent() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getApplicationContext().getResources().getDisplayMetrics();
        //这里其实要转dp
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.iv);
        mBtn_choosepic = (Button) findViewById(R.id.choosepic);
        mBtn_change_option = (Button) findViewById(R.id.change_option);
        mBtn_change_rgb = (Button) findViewById(R.id.change_rgb);
        mBtn_partload = (Button) findViewById(R.id.partload);
        mBtn_offset = (Button) findViewById(R.id.offset);
    }

    /**
     * URI方式打开相册获取图片
     */
    private void getPic() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            String path = getRealPathFromURL(data.getData());
            mFile = new File(path);
            if (mFile == null)
                return;
            if (mFile.length() == 0) {
                mFile.delete();
                return;
            }
            Log.i("xmx", "file:" + mFile.getName() + ",原图大小length:" + mFile.length());
            FileInputStream fis = new FileInputStream(mFile);
            mBitmap = BitmapFactory.decodeStream(fis);
            Log.i("xmx", "bitmap,Bitmap解析图片后大小length:" + mBitmap.getByteCount());
            mImageView.setImageBitmap(mBitmap);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("xmx", "bitmap,load error:" + e.getMessage());
        }
    }

    /**
     * 根据URI获得File文件路径
     *
     * @param contentUri
     * @return
     */
    private String getRealPathFromURL(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /**
     * 点击原图按钮
     *
     * @param view
     */
    public void choosepic(View view) {
        getPic();
    }

    /**
     * 点击缩放比例按钮
     *
     * @param view
     */
    public void change_option(View view) {
        if (mFile == null) {
            return;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; //只获取边界不加载
            BitmapFactory.decodeStream(new FileInputStream(mFile), null, options);
            int width_tmp = options.outWidth, height_tmp = options.outHeight;
            int scale = 2;
            while (true) {
                if (width_tmp / scale < SCREEN_WIDTH)
                    break;
                scale += 2;
            }
            scale /= 2;
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = scale; //上述操作得到抽样比之后,在赋给inSampleSize
            Log.i("xmx", "bitmap,缩放比例scale:" + scale);
            FileInputStream fis = new FileInputStream(mFile);
            mBitmap = BitmapFactory.decodeStream(fis, null, options2);
            Log.i("xmx", "scale-bitmap,缩放比例后的图片大小legth:" + mBitmap.getByteCount());
            mImageView.setImageBitmap(mBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击更改解码格式按钮
     *
     * @param view
     */
    public void change_rgb(View view) {
        if (mFile == null) {
            return;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            FileInputStream fis = new FileInputStream(mFile);
            mBitmap = BitmapFactory.decodeStream(fis, null, options);
            Log.i("xmx", "RGB_565-bitmap,更换解码格式后的图片大小legth:" + mBitmap.getByteCount());
            mImageView.setImageBitmap(mBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击局部加载按钮
     *
     * @param view
     */
    public void partload(View view) {
        partload_offset();
    }

    private void partload_offset() {
        if (mFile == null) {
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(mFile);
            //获取图片的宽高
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; //只获取边界不加载
            BitmapFactory.decodeStream(new FileInputStream(mFile), null, options);
            int width = options.outWidth, height = options.outHeight;

            //设置显示图片的中心区域
            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(fis, false);//部分图片解析API
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            mBitmap = bitmapRegionDecoder.decodeRegion(new Rect(width / 2 - SCREEN_WIDTH / 2 + shiftpx, height / 2 - SCREEN_HEIGHT / 2,
                    width / 2 + SCREEN_WIDTH / 2 + shiftpx, height / 2 + SCREEN_HEIGHT / 2), options2);
            mImageView.setImageBitmap(mBitmap);
            Log.i("xmx", "PartLoad-bitmap,局部加载图片的大小legth:" + mBitmap.getByteCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offset(View view) {
        shiftpx += 10;
        partload_offset();
    }


}
