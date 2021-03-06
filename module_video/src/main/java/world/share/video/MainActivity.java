package world.share.video;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import world.share.video.opengl.WlGLSurfaceView;
import world.share.video.player.MusicPlayer;
import world.share.video.player.bean.MuteEnum;
import world.share.video.player.bean.WlTimeInfoBean;
import world.share.video.player.listener.WlOnCompleteListener;
import world.share.video.player.listener.WlOnErrorListener;
import world.share.video.player.listener.WlOnLoadListener;
import world.share.video.player.listener.WlOnPauseResumeListener;
import world.share.video.player.listener.WlOnPreparedListener;
import world.share.video.player.listener.WlOnTimeInfoListener;
import world.share.video.player.listener.WlOnValumeDBListener;
import world.share.video.player.log.MyLog;
import world.share.video.utils.PermissionsUtils;
import world.share.video.utils.WlTimeUtil;


/**
 * @author simpo
 */
public class MainActivity extends AppCompatActivity {

    private MusicPlayer wlPlayer;
    private TextView tvTime;
    private TextView tvVolume;
    private TextView volumeShow;
    private EditText musicUrl;
    private EditText musicSpeed;
    private EditText musicTone;
    private SeekBar seekBarSeek;
    private SeekBar seekBarVolume;
    private int position = 0;
    private boolean isSeekBar = false;
    private WlGLSurfaceView wlGLSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        tvTime = findViewById(R.id.tv_time);
        tvVolume = findViewById(R.id.tv_volume);
        musicUrl = findViewById(R.id.music_url);
        musicSpeed = findViewById(R.id.music_speed);
        musicTone = findViewById(R.id.music_tone);
        seekBarSeek = findViewById(R.id.seekbar_seek);
        seekBarVolume = findViewById(R.id.seekbar_volume);
        volumeShow = findViewById(R.id.volume_show);
        wlGLSurfaceView = findViewById(R.id.wlglsurfaceview);
        wlPlayer = new MusicPlayer();
        wlPlayer.setWlGLSurfaceView(wlGLSurfaceView);
        wlPlayer.setVolume(50);
        tvVolume.setText("?????????" + wlPlayer.getVolumePercent() + "%");
        seekBarVolume.setProgress(wlPlayer.getVolumePercent());
        wlPlayer.setWlOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                MyLog.d("??????????????????????????????????????????");
                wlPlayer.start();
            }
        });
        wlPlayer.setWlOnLoadListener(new WlOnLoadListener() {
            @Override
            public void onLoad(boolean load) {
                if (load) {
                    MyLog.d("?????????...");
                } else {
                    MyLog.d("?????????...");
                }
            }
        });

        wlPlayer.setWlOnPauseResumeListener(new WlOnPauseResumeListener() {
            @Override
            public void onPause(boolean pause) {
                if (pause) {
                    MyLog.d("?????????...");
                } else {
                    MyLog.d("?????????...");
                }
            }
        });

        wlPlayer.setWlOnTimeInfoListener(new WlOnTimeInfoListener() {
            @Override
            public void onTimeInfo(WlTimeInfoBean timeInfoBean) {
                Message message = Message.obtain();
                message.what = 1;
                message.obj = timeInfoBean;
                handler.sendMessage(message);
            }
        });

        wlPlayer.setWlOnErrorListener(new WlOnErrorListener() {
            @Override
            public void onError(int code, String msg) {
                MyLog.d("code:" + code + ", msg:" + msg);
            }
        });
        wlPlayer.setWlOnCompleteListener(new WlOnCompleteListener() {
            @Override
            public void onComplete() {
                MyLog.d("???????????????");
                isPlay = false;
            }
        });

        wlPlayer.setWlOnValumeDBListener(new WlOnValumeDBListener() {
            @Override
            public void onDbValue(int db) {
                volumeShow.setText("????????????:" + db);
            }
        });
        seekBarSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                position = wlPlayer.getDuration() * progress / 100;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekBar = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                wlPlayer.seek(position);
                isSeekBar = false;
            }
        });

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                wlPlayer.setVolume(progress);
                tvVolume.setText("?????????" + wlPlayer.getVolumePercent() + "%");
                MyLog.d("progress is " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void begin(View view) {
//        wlPlayer.updateFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AFeel/????????????_??????.flv", "rtmp://192.168.3.98:1935/rtmplive/test");
        if (PermissionsUtils.getStoragePermission(this)) {
            isPlay = true;
//            if (musicUrl.getText() != null && !TextUtils.isEmpty(musicUrl.getText().toString())) {
//                wlPlayer.setSource(musicUrl.getText().toString());
//            } else {
//            wlPlayer.setSource("http://mpge.5nd.com/2015/2015-11-26/69708/1.mp3");
//                wlPlayer.playNext("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319222227698228.mp4");
//                wlPlayer.setSource("https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4");
//                wlPlayer.setSource(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AFeel/???????????????.mkv");
            wlPlayer.setSource(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/USE.mp4");
//            }
            wlPlayer.prepare();
        }
    }

    private boolean hasPause = false;
    private boolean isPlay = false;

    @Override
    protected void onPause() {
        super.onPause();
        hasPause = true;
        wlPlayer.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasPause && isPlay) {
            wlPlayer.resume();
        }
    }

    public void pause(View view) {
        wlPlayer.pause();
        isPlay = false;

    }

    public void resume(View view) {
        isPlay = true;
        wlPlayer.resume();
    }

    public void stop(View view) {
        wlPlayer.stop();
    }

    public void seek(View view) {
        wlPlayer.seek(200);
    }

    public void next(View view) {
//        wlPlayer.playNext("http://ngcdn004.cnr.cn/live/dszs/index.m3u8");
        wlPlayer.playNext("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319222227698228.mp4");
    }

    public void left(View view) {
        wlPlayer.setMute(MuteEnum.MUTE_LEFT);
    }

    public void right(View view) {
        wlPlayer.setMute(MuteEnum.MUTE_RIGHT);
    }

    public void center(View view) {
        wlPlayer.setMute(MuteEnum.MUTE_CENTER);
    }

    public void speed(View view) {
        if (musicSpeed.getText() != null && !"".equals(musicSpeed.getText().toString())) {
            wlPlayer.setSpeed(Float.parseFloat(musicSpeed.getText().toString()) / 5);
        } else {
            wlPlayer.setSpeed(1.0f);
        }
    }

    public void pitch(View view) {
        if (musicSpeed.getText() != null && !"".equals(musicSpeed.getText().toString())) {
            wlPlayer.setPitch(Float.parseFloat(musicTone.getText().toString()) / 5);
        } else {
            wlPlayer.setPitch(1.0f);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                WlTimeInfoBean wlTimeInfoBean = (WlTimeInfoBean) msg.obj;
                tvTime.setText(WlTimeUtil.secdsToDateFormat(wlTimeInfoBean.getTotalTime(), wlTimeInfoBean.getTotalTime())
                        + "/" + WlTimeUtil.secdsToDateFormat(wlTimeInfoBean.getCurrentTime(), wlTimeInfoBean.getTotalTime()));


                if (!isSeekBar && wlTimeInfoBean.getTotalTime() > 0) {
                    seekBarSeek.setProgress(wlTimeInfoBean.getCurrentTime() * 100 / wlTimeInfoBean.getTotalTime());
                }
            }
        }
    };
}
