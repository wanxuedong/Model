package world.share.lib_base.mvvm.viewmodel

interface IModel {
    /**
     * ViewModel销毁时清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
    // TODO: 2021/12/1 目前未用到,考虑是否要去掉
    fun onCleared()
}