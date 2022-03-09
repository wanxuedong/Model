package world.share.lib_base.constant

/**
 * 组件化跳转路由
 * 2021/9/10
 * 详情见：https://segmentfault.com/a/1190000040305342?utm_source=tag-newest
 */
object RouterUrl {

    /**
     * module_login统一group
     * **/
    private const val LOGIN_GROUP = "/login"

    /**
     * 启动页
     * **/
    const val SPLASH_ACTIVITY = "$LOGIN_GROUP/SplashActivity"

    /**
     * 登陆页
     * **/
    const val LOGIN_ACTIVITY = "$LOGIN_GROUP/LoginActivity"

    /**
     * 注册页
     * **/
    const val REGISTER_ACTIVITY = "$LOGIN_GROUP/RegisterActivity"

    /**
     * module_main统一group
     * **/
    private const val MAIN_GROUP = "/main"

    /**
     * 首页
     * **/
    const val MAIN_ACTIVITY = "$MAIN_GROUP/MainActivity"

    /**
     * module_home统一group
     * **/
    private const val HOME_GROUP = "/home"

    /**
     * 首页
     * **/
    const val HOME_FRAGMENT = "$HOME_GROUP/HomeFragment"

    /**
     * module_collect统一group
     * **/
    private const val COLLECT_GROUP = "/collect"

    /**
     * 收藏
     * **/
    const val COLLECT_FRAGMENT = "$COLLECT_GROUP/CollectFragment"

    /**
     * module_user统一group
     * **/
    private const val USER_GROUP = "/user"

    /**
     * 个人
     * **/
    const val USER_FRAGMENT = "$USER_GROUP/UserFragment"

    /**
     * 用户中心页
     * **/
    const val USER_CENTER_ACTIVITY = "$USER_GROUP/UserCenterActivity"

    /**
     * module_audioPlayer统一group
     * **/
    private const val AUDIO_PLAYER_GROUP = "/audioPlayer"

    /**
     * module_audio统一group
     * **/
    private const val AUDIO_GROUP = "/audio"

    /**
     * 音频播放器
     * **/
    const val AUDIO_PLAYER_ACTIVITY = "$AUDIO_GROUP/AudioPlayerActivity"
}