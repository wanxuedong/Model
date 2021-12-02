package world.share.lib_base

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
     * module_home统一group
     * **/
    private const val HOME_GROUP = "/home"
    /**
     * 首页页
     * **/
    const val HOME_ACTIVITY = "$HOME_GROUP/HomeActivity"
    /**
     * 用户中心页
     * **/
    const val USER_CENTER_ACTIVITY = "$HOME_GROUP/UserCenterActivity"
}