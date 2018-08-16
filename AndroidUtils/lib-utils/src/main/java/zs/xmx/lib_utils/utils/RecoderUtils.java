package zs.xmx.lib_utils.utils;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/10/13 13:13
 * @本类描述	  录音/播放_相关工具类
 * @内容说明   1.开始录音
 *            2.停止录音
 *            3.获取音量大小
 *            4.开始/停止播放
 *            5.播放完成
 *
 * 注意:
 *      构造方法调用,传路径调用
 *
 * ---------------------------------------------     
 * @更新时间   2016/10/13 
 * @更新说明
 */
public class RecoderUtils {
    private MediaRecorder recorde = null;
    private String path;
    //采样率
    private static int         SAMPLE_RATE_IN_HZ = 8000;
    //播放录音
    private        MediaPlayer mPlayer           = null;
    private        boolean     playState         = false; // 录音的播放状态

    public RecoderUtils() {

    }

    public RecoderUtils(String path) {
        this.path = sanitizePath(path);
    }

    public MediaPlayer getmPlayer() {
        return mPlayer;
    }

    public void setmPlayer(MediaPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }

    private String sanitizePath(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!path.contains(".")) {
            path += ".amr";
        }
        if (recorde == null) {
            recorde = new MediaRecorder();
        }
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/myvoice" + path;
    }

    /**
     * 开始录音
     */
    public void start() {
        if (recorde == null) {
            recorde = new MediaRecorder();
        }
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        File directory = new File(path).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            return;
        }

        try {
            // 设置音频源为MIC
            recorde.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置声音的输出格式
            recorde.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            // 设置声音的编码格式
            recorde.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // 设置音频采样率
            recorde.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);
            // 设置输出文件
            recorde.setOutputFile(path);
            // 准备录音
            recorde.prepare();
            // 开始录音
            recorde.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            recorde = null;
            recorde = new MediaRecorder();
        }
    }

    /**
     * 停止录音
     */
    public String stopRecording() {

        try {
            recorde.stop();
        } catch (Exception e) {
            //释放资源
            recorde = null;
            recorde = new MediaRecorder();
        }
        //释放资源
        recorde.release();
        recorde = null;

        return path;
    }

    /**
     * @param Filename   Filename
     * @param completion completion
     */
    //
    public void startPlaying(String Filename, OnCompletionListener completion) {

        if (!playState) {
            if (mPlayer == null) {
                mPlayer = new MediaPlayer();
            }

            try {
                mPlayer.setDataSource(Filename);
                mPlayer.prepare();
                playState = true;
                mPlayer.start();
                mPlayer.setOnCompletionListener(completion);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
                playState = false;
                //				startPlaying(Filename);
            } else {
                playState = false;
            }

        }
    }

    /**
     * 播放完后释放资源
     */
    public void playingFinish() {
        Log.i("spoort_list", "RecorderControl 播放结束释放资源");
        if (playState) {
            playState = false;
        }
        mPlayer.release();
        mPlayer = null;
    }

    /**
     * 停止播放
     *
     * @return boolean
     */
    public boolean stopPlaying() {

        if (mPlayer != null) {
            //			if(mPlayer!=null&&mPlayer.isPlaying()){
            Log.i("spoort_list", "RecorderControl mPlayer.stop()");
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            playState = false;
            return true;
        } else {
            Log.i("spoort_list", "RecorderControl mPlayer.stop() is null");
            return false;
        }
    }
    //当文件播放结束后调用此方法
    //	OnCompletionListener completion = new OnCompletionListener() {
    //		@Override
    //		public void onCompletion(MediaPlayer mp) {
    ////			if(playState){
    ////				playState = false;
    ////			}
    //			Log.i("spoort_list", "RecorderControl 播放结束");
    //			mPlayer.release();
    //			mPlayer = null;
    //		}
    //	};


    /**
     * 获取音量大小
     *
     * @return 比率
     */
    public double getAmplitude() {
        if (recorde != null) {
            return (recorde.getMaxAmplitude());
        } else
            return 0;
    }
}
