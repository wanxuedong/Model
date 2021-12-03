package world.share.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author wan
 * 创建日期：2021/12/02
 * 描述：首页分页适配器
 */
class HomeAdapter(
    fragmentManager: FragmentManager,
    var list: MutableList<Fragment>,
    var lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list.get(position)
    }

}