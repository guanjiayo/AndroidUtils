package zs.xmx.lib_utils.utils;
/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/2/7 17:58
 * @本类描述	  MediaPlayer 工具类
 * @内容说明   ${TODO}
 * @补充内容
 *
 * ---------------------------------     
 * @更新时间   $Date$
 * @新增内容   ${TODO}
 *
 */

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;

public class MediaPlayerUtils {
    static MediaPlayer mMediaPlayer;


    public static MediaPlayer getMediaPlayer() {
        if (null == mMediaPlayer) {
            mMediaPlayer = new MediaPlayer();
        }

        return mMediaPlayer;
    }


    /**
     * 播放音频
     */
    public static void playAudio(Context mContext, String fileName) {
        try {
            stopAudio();//如果正在播放就停止
            AssetManager assetManager = mContext.getAssets();
            AssetFileDescriptor afd = assetManager.openFd(fileName);
            MediaPlayer mediaPlayer = getMediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setLooping(false);//循环播放
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            Log.e("播放音频失败", "");
        }
    }


    /**
     * 停止播放音频
     */
    public static void stopAudio() {
        try {
            if (null != mMediaPlayer) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mMediaPlayer.reset();
                    mMediaPlayer.stop();
                }
            }
        } catch (Exception e) {
            Log.e("stopAudio", e.getMessage());
        }


    }
}
