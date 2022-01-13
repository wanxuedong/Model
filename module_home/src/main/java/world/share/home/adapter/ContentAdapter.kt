package world.share.home.adapter

import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import world.share.home.R
import world.share.home.bean.ContentBean

class ContentAdapter(data: MutableList<ContentBean>) :
    BaseMultiItemQuickAdapter<ContentBean, ContentAdapter.ContentViewHolder>(data) {

    companion object {
        /**
         * 辩论
         * **/
        const val contentDebate = 1001

        /**
         * 爱发
         * **/
        const val contentSocial = 1002

        /**
         * 投稿
         * **/
        const val contentArticle = 1003
    }

    init {
        addItemType(contentDebate, R.layout.home_item_content_debate)
        addItemType(contentSocial, R.layout.home_item_content_social)
        addItemType(contentArticle, R.layout.home_item_content_article)
    }

    open class ContentViewHolder(view: View) : BaseViewHolder(view) {
    }

    override fun convert(helper: ContentViewHolder?, item: ContentBean?) {
        when (helper?.itemViewType) {
            contentDebate -> {

            }
        }
    }

}