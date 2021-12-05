package world.share.home.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import world.share.lib_base.App
import world.share.lib_base.data.DataRepository
import world.share.lib_base.mvvm.viewmodel.BaseViewModel

/**
 * @author wan
 * 创建日期：2021/12/02
 * 描述：首页分页内容viewmodel
 */
class ContentViewModel(application: App, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    private val mText: MutableLiveData<String> = MutableLiveData()
    val text: LiveData<String>
        get() = mText

    init {
        mText.value = "This is dashboard fragment"
    }
}