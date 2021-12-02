package world.share.lib_base.widget.boommenu

import androidx.fragment.app.FragmentActivity
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomButtons.OnBMClickListener
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton
import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.ButtonEnum
import com.nightonke.boommenu.Piece.PiecePlaceEnum
import world.share.lib_base.R
import world.share.widget.toast.ToastBuild
import world.share.widget.toast.ToastClick
import world.share.widget.toast.ToastUtil

/**
 * @author wan
 * 创建日期：2021/12/02
 * 描述：通用弹出悬浮框
 */
object BoomMenuFactory {

    /**
     * 创建首页弹出框
     * **/
    fun createHomeButton(button: BoomMenuButton, activity: FragmentActivity) {
        button.normalColor = R.color.blueColor
        button.buttonEnum = ButtonEnum.TextInsideCircle
        button.piecePlaceEnum = PiecePlaceEnum.DOT_3_1
        button.buttonPlaceEnum = ButtonPlaceEnum.SC_3_3
        button.addBuilder(createButtonBuilder(
            R.drawable.cat, R.string.topic_debate, R.color.redColor
        ) {
            ToastUtil.materialShow(
                activity.supportFragmentManager,
                ToastBuild("您有重要的消息").setAlwaysShow(true).setToastClick(object : ToastClick() {
                    override fun confirm() {
                        super.confirm()
                        ToastUtil.show("你点我干什么?")
                    }

                    override fun cancel() {
                        super.cancel()
                        ToastUtil.show("啊！我被关闭了!")
                    }
                })
            )
        })
        button.addBuilder(createButtonBuilder(
            R.drawable.bear, R.string.love_publishing, R.color.greenColor
        ) { ToastUtil.show("2") })
        button.addBuilder(createButtonBuilder(
            R.drawable.bee, R.string.contribution, R.color.blueColor
        ) { ToastUtil.show("3") })
    }

    /**
     * 悬浮弹出框自定义
     * **/
    private fun createButtonBuilder(
        normalImageRes: Int,
        normalTextRes: Int,
        normalColorRes: Int,
        listener: OnBMClickListener
    ): TextInsideCircleButton.Builder {
        return TextInsideCircleButton.Builder()
            .normalImageRes(normalImageRes)
            .normalColorRes(normalColorRes)
            .normalTextRes(normalTextRes).listener(listener)
    }

}