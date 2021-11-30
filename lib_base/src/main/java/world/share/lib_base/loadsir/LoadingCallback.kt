package com.czl.lib_base.callback

import com.kingja.loadsir.callback.Callback
import world.share.lib_base.R

/**
 * @author wan
 * 创建日期：2021/11/23
 * 描述：页面加载中页面
 */
class LoadingCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.common_loading_layout
    }
}