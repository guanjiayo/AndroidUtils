package zs.xmx.lib_utils.todo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class BitMapUtil {
    //TODO 测试
    //注意权限问题
    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        //将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
        options.inJustDecodeBounds = true;

        /*解析源图片,返回一个 bitmap 对象,当 options.inJustDecodeBounds = true;
        禁止为bitmap分配内存，返回值也不再是一个Bitmap对象，而是null。虽然Bitmap是null了，
         但是BitmapFactory.Options的outWidth、outHeight和outMimeType属性都会被赋值。
         这个技巧让我们可以在加载图片之前就获取到图片的长宽值和MIME类型，从而根据情况对图片进行压缩*/
        BitmapFactory.decodeResource(res, resId, options);

        // 这个方法用来计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;

        /*计算完inSampleSize 的合适大小后,需要把 options.inJustDecodeBounds = false;
        然后把再 BitmapFactory.decodeResource(res,resId,options)
        此时  options.inJustDecodeBounds = false; ,BitmapFactory.decodeResource() 方法返回一个bitmap对象给 imageView.setImageBitmap()方法
        从而显示一个合适大小的图片
        */
        return BitmapFactory.decodeResource(res, resId, options);

    }

    public static Bitmap decodeSampleBitmapFromFile(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
        options.inJustDecodeBounds = true;

        /*解析源图片,返回一个 bitmap 对象,当 options.inJustDecodeBounds = true;
        禁止为bitmap分配内存，返回值也不再是一个Bitmap对象，而是null。虽然Bitmap是null了，
         但是BitmapFactory.Options的outWidth、outHeight和outMimeType属性都会被赋值。
         这个技巧让我们可以在加载图片之前就获取到图片的长宽值和MIME类型，从而根据情况对图片进行压缩*/
        BitmapFactory.decodeFile(path, options);
        // 这个方法用来计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;

        /*计算完inSampleSize 的合适大小后,需要把 options.inJustDecodeBounds = false;
        然后把再 BitmapFactory.decodeResource(res,resId,options)
        此时  options.inJustDecodeBounds = false; ,BitmapFactory.decodeResource() 方法返回一个bitmap对象给 imageView.setImageBitmap()方法
        从而显示一个合适大小的图片
        */
        return BitmapFactory.decodeFile(path, options);
    }

    /*计算出合适的inSampleSize值：*/
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //源图片的高度和宽度
        final int height = options.outHeight;//得到要加载的图片高度
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            //计算出实际宽高和目标宽高的比例
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

        }
        return inSampleSize;

    }
}  