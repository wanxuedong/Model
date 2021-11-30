package world.share.lib_base

/**
 * 组件化跳转路由
 * 2021/9/10
 * 详情见：https://segmentfault.com/a/1190000040305342?utm_source=tag-newest
 */
object RouterUrl {
    private const val LOGIN_GROUP = "/login"
    const val SPLASH_ACTIVITY = "$LOGIN_GROUP/SplashActivity"
    const val LOGIN_ACTIVITY = "$LOGIN_GROUP/LoginActivity"
    private const val HOME_GROUP = "/home"
    const val HOME_ACTIVITY = "$HOME_GROUP/HomeActivity"
    const val USER_CENTER_ACTIVITY = "$HOME_GROUP/UserCenterActivity"
}