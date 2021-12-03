package world.share.widget.toast

import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import world.share.baseutils.ScreenUtil
import world.share.baseutils.threadutil.LifeRunnable
import world.share.baseutils.threadutil.ThreadX
import world.share.widget.BaseDialogFragment
import world.share.widget.R

/**
 * @author wan
 * 创建日期：2021/12/02
 * 描述：自定义Toast样式
 */
open class ToastFragment() : BaseDialogFragment() {

    /**
     * 占位布局
     * **/
    private lateinit var noticeSeat: View

    /**
     * 消息内容控件
     * **/
    private lateinit var noticeMessage: TextView

    /**
     * 消息关闭控件
     * **/
    private lateinit var noticeClose: ImageView

    /**
     * 展示内容
     * **/
    open lateinit var message: String

    /**
     * 吐司展示时长
     * **/
    open var showTime = 3000

    /**
     * 吐司距离底部或顶部的距离
     * **/
    open var margin = 80F

    /**
     * 是否展示Toast关闭按钮
     * **/
    open var showClose = false

    /**
     * 吐司点击事件回调
     * **/
    open var toastClick: ToastClick? = null

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
        rootView?.let {
            noticeSeat = it.findViewById(R.id.toast_notice_seat)
            noticeMessage = it.findViewById(R.id.toast_notice_message)
            noticeClose = it.findViewById(R.id.toast_notice_close)
        }
    }

    override fun initData() {
        super.initData()
        noticeMessage.text = message
        noticeClose.visibility = if (showClose) View.VISIBLE else View.GONE
        //设置消息体宽度
        var messageParams = noticeMessage.layoutParams
        var messageHeight = messageParams.height
        messageParams.width = ScreenUtil.getScreenWidth(activity?.application) * 2 / 3
        noticeMessage.layoutParams = messageParams

        //设置消息弹出位置
        var seatParams = noticeSeat.layoutParams
        when (gravity) {
            Gravity.TOP -> {
                seatParams.height = ScreenUtil.dip2px(
                    context,
                    margin
                ) + ScreenUtil.getStatusHeight(context) + messageHeight
            }
            Gravity.CENTER -> {
                seatParams.height =
                    ScreenUtil.getStatusHeight(activity?.application) / 2 - messageHeight / 2
            }
            Gravity.BOTTOM -> {
                seatParams.height =
                    ScreenUtil.getStatusHeight(activity?.application) - messageHeight - ScreenUtil.dip2px(
                        context,
                        margin
                    )
            }
        }
        noticeSeat.layoutParams = seatParams
    }

    override fun initEvent() {
        super.initEvent()
        noticeMessage.setOnClickListener {
            toastClick?.confirm()
        }
        noticeClose.setOnClickListener {
            toastClick?.cancel()
            if (this@ToastFragment.dialog != null && this@ToastFragment.dialog?.isShowing == true) {
                dismiss()
            }
        }
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
        when (gravity) {
            Gravity.TOP -> {
                return R.style.ToastShowTop
            }
            Gravity.CENTER -> {
                return R.style.ToastShowCenter
            }
            Gravity.BOTTOM -> {
                return R.style.ToastShowBottom
            }
        }
        return R.style.ToastShowBottom
    }

    override fun isCancelOutSide(): Boolean {
        return !showClose
    }

    override fun isCancelable(): Boolean {
        return !showClose
    }

    /**
     * 展示后自动触发消失
     * **/
    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        if (!showClose) {
            ThreadX.getInstance().execute(object : LifeRunnable() {
                override fun running() {
                    Thread.sleep(showTime.toLong())
                }

                override fun end() {
                    super.end()
                    if (this@ToastFragment.dialog != null && this@ToastFragment.dialog?.isShowing == true) {
                        dismiss()
                    }
                }
            })
        }
    }

}