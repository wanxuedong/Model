package world.share.collect.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import world.share.lib_base.App
import world.share.lib_base.data.DataRepository
import world.share.lib_base.mvvm.viewmodel.BaseViewModel

class CollectViewModel(application: App, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model){
    private val mText: MutableLiveData<String>
    val text: LiveData<String>
        get() = mText

    init {
        mText = MutableLiveData()
        mText.value = "This is dashboard fragment"
    }
}