package world.share.lib_base.bindingadapter

import androidx.databinding.BindingAdapter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import world.share.lib_base.mvvm.command.BindingCommand

/**
 * @author Alwyn
 * @Date 2020/10/29
 * @Description
 */
object TabChangeAdapter {
    @JvmStatic
    @BindingAdapter(value = ["onTabChangeCommand"], requireAll = false)
    fun onTabChangeCommand(
        bar: BottomNavigationBar,
        onTabSelectedCommand: BindingCommand<Int?>?
    ) {
        bar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                onTabSelectedCommand?.execute(position)
            }

            override fun onTabUnselected(position: Int) {}
            override fun onTabReselected(position: Int) {}
        })
    }
}