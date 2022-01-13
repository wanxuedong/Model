音频播放器使用手册

一：初始化
AudioPlayer audioPlayer = new AudioPlayer();

二：设置播放源
audioPlayer.setSource("...mp3");

三：设置监听器
（1）播放状态监听器
```
audioPlayer.setPlayerStateListener(new OnPlayerStateListener() {
    @Override
    public void play(PlayState state) {
    }
});
```
（2）播放进度监听器
```
audioPlayer.setPlayerProgressListener(new OnPlayerProgressListener() {
    @Override
    public void progress(PlayTime playTime) {
    }
});
```
（3）播放错误监听器
```
audioPlayer.setPlayerErrorListener(new OnPlayerErrorListener() {
    @Override
    public void onError(int code, String message) {
    }
});
```
返回的错误code，1000开头的为c++层错误，2000开头的为java层错误。

四：基本播放状态控制
开始播放
```
audioPlayer.start();
```
暂停播放
```
audioPlayer.pause();
```
恢复暂停播放
```
audioPlayer.resume();
```
停止播放
```
audioPlayer.stop();
```

其他api
设置播放进度，范围在0到最大音频长度
```
audioPlayer.seek(int progress);
```
获取音频总时长，需要调用load后才可获取
```
audioPlayer.getDuration()
```
设置播放音量，0-100，0为静音，100为最大
```
audioPlayer.setVolume(int percent)
```
设置播放声道，分为左右和立体声道
```
audioPlayer.setMute(MuteEnum mute)
```
设置播放音调，需大于0，1.0f为正常音调，小于1.0f为低音，大于1.0f为高音
```
audioPlayer.setPitch(float pitch)
```
设置播放速度，需大于0，1.0f为正常播放速度，小于1.0f为减速，大于1.0f为加速
```
audioPlayer.setSpeed(float speed)
```

注意点：
（1）重复调用start无效，播放下一个音频必须调用stop结束当前播放后才可以调用start播放新的音频
（2）想要无缝衔接下一首音频播放，需要提前调用audioPlayer.setNextSource("...mp3")设置下一个音频播放路径，
设置之后当前播放结束会检查是否设置了下一首音频路径，不为空则自动播放，并且播放后会重置为空，想要再接着播放下一首，
需要再次调用audioPlayer.setNextSource("...mp3")进行设置，建议在播放状态中的播放结束中设置下一首播放路径(也可以通过
播放进度自行判断)。
如下：
```
//设置准备播放的音频
audioPlayer.setSource("A.mp3");
//想要无缝播放，必须要提前设置好下一首
audioPlayer.setNextSource("B.mp3");
audioPlayer.setPlayerStateListener(new OnPlayerStateListener() {
    @Override
    public void play(PlayState state) {
        if (state == PlayState.ON_STOP){
            //设置下一首播放路径
            audioPlayer.setNextSource("C.mp3");
        }
    }
});
```
按照上面的设置，当A.mp3播放结束后，调用状态回调，返回PlayState.ON_STOP，并且开始播放B.mp3,此时设置C.mp3，那么
当B.mp3播放结束后则会播放C.mp3