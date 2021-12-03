package world.share.lib_base.bindingadapter

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import world.share.lib_base.mvvm.command.BindingCommand

object PagerAdapter {
    @JvmStatic
    @BindingAdapter(
        value = ["onPageScrolledCommand", "onPageSelectedCommand", "onPageScrollStateChangedCommand"],
        requireAll = false
    )
    fun onScrollChangeCommand(
        viewGroup: ViewGroup?,
        onPageScrolledCommand: BindingCommand<ViewPagerDataWrapper?>?,
        onPageSelectedCommand: BindingCommand<Int?>?,
        onPageScrollStateChangedCommand: BindingCommand<Int?>?
    ) {
        if (viewGroup is ViewPager2) {
            viewGroup.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    onPageSelectedCommand?.execute(position)
                }
            })
        }
        if (viewGroup is ViewPager) {
            viewGroup.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                private var state = 0
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    onPageScrolledCommand?.execute(
                        ViewPagerDataWrapper(
                            position.toFloat(),
                            positionOffset,
                            positionOffsetPixels,
                            state
                        )
                    )
                }

                override fun onPageSelected(position: Int) {
                    onPageSelectedCommand?.execute(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    this.state = state
                    onPageScrollStateChangedCommand?.execute(state)
                }
            })
        }
    }

    class ViewPagerDataWrapper(
        var position: Float,
        var positionOffset: Float,
        var positionOffsetPixels: Int,
        var state: Int
    )
}