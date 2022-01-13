package world.share.widget.toast

/**
 * @author wan
 * 创建日期：2021/12/02
 * 描述：material风格吐司点击事件回调
 */
abstract class ToastClick {

    /**
     * 点击吐司消息体回调
     * **/
    open fun confirm(){}

    /**
     * 点击关闭吐司消息体按钮回调
     * **/
    open fun cancel(){}

}