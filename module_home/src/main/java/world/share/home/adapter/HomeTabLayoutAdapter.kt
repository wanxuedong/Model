package world.share.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author wan
 * 创建日期：2021/12/02
 * 描述：备注
 */
class HomeTabLayoutAdapter(
    fragmentManager: FragmentManager,
    var list: MutableList<String>,
    var fragments: MutableList<Fragment>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }
}