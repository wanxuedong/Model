package world.share.widget.toast

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import world.share.baseutils.threadutil.LifeRunnable
import world.share.baseutils.threadutil.ThreadX
import world.share.widget.BaseDialogFragment
import world.share.widget.R

/**
 * @author wan
 * 创建日期：2021/12/02
 * 描述：自定义Toast样式
 */
class ToastFragment() : BaseDialogFragment() {

    /**
     * 消息内容控件
     * **/
    lateinit var noticeMessage: TextView

    /**
     * 展示内容
     * **/
    open lateinit var message: String

    /**
     * 吐司展示时长
     * **/
    open var showTime = 3000

    /**
     * 吐司点击事件回调
     * **/
    open lateinit var toastClick: ToastClick

    constructor(message: String?) : this() {
        if (message != null) {
            this.message = message
        }
    }

    override fun getDialogView(): Int {
        return R.layout.dialog_toast_notice
    }

    override fun initView(rootView: View?) {
        super.initView(rootView)
        noticeMessage = rootView!!.findViewById(R.id.toast_notice_message)
        noticeMessage.text = message
    }

    override fun isOutClick(): Boolean {
        return true
    }

    override fun isShowBack(): Boolean {
        return false
    }

    override fun getGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun setDialogStyle(): Int {
        return R.style.ToastNotice
    }

    override fun isCancelOutSide(): Boolean {
        return true
    }

    /**
     * 展示后自动触发消失
     * **/
    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        ThreadX.getInstance().execute(object : LifeRunnable() {
            override fun running() {
                Thread.sleep(3000)
            }

            override fun end() {
                super.end()
                if (this@ToastFragment.dialog != null && this@ToastFragment.dialog!!.isShowing) {
                    dismiss()
                }
            }
        })
    }

}