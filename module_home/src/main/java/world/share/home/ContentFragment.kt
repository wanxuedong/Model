package world.share.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import world.share.home.adapter.ContentAdapter
import world.share.home.bean.ContentBean
import world.share.home.databinding.HomeFragmentContentBinding
import world.share.home.model.ContentViewModel
import world.share.lib_base.mvvm.BaseFragment

/**
 * @author wan
 * 创建日期：2021/12/02
 * 描述：首页分页内容
 */
class ContentFragment : BaseFragment<HomeFragmentContentBinding, ContentViewModel>() {

    private var contentList = mutableListOf<ContentBean>()
    private lateinit var contentAdapter: ContentAdapter

    companion object {
        private const val SORT_ID = "sort_id"
        fun getInstance(id: String): ContentFragment = ContentFragment().apply {
            arguments = Bundle().apply {
                putString(SORT_ID, id)
            }
        }
    }

    override fun attachView(): Int {
        return R.layout.home_fragment_content
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        super.initData()
        for (int in 1..10){
            contentList.add(ContentBean())
        }
        contentAdapter = ContentAdapter(contentList)
        binding.recyclerContent.apply {
            adapter = contentAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}