package world.share.lib_base.mvvm.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.kingja.loadsir.core.LoadService
import com.trello.rxlifecycle3.LifecycleProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import world.share.lib_base.App
import world.share.lib_base.internet.bean.BaseResponse
import world.share.lib_base.mvvm.command.BindingAction
import world.share.lib_base.mvvm.command.BindingCommand
import world.share.lib_base.mvvm.event.UIChangeLiveData
import java.lang.ref.WeakReference

/**
 * @author wan
 * 创建日期：2021/11/10
 * 描述：基础ViewModel
 */
open class BaseViewModel<M : BaseModel>(application: App, val model: M) :
    AndroidViewModel(application), IBaseViewModel,
    Consumer<Disposable?> {
    /**
     * 弱引用持有
     * **/
    private lateinit var lifecycle: WeakReference<LifecycleProvider<*>>
    lateinit var loadService: LoadService<BaseResponse<*>?>

    /**
     * 界面变化事件
     * **/
    val uC: UIChangeLiveData = UIChangeLiveData()

    // 标题栏标题
    val tvTitle = ObservableField("")

    val toolbarRightText = ObservableField("")

    // 标题栏右图标id
    val ivToolbarIconRes = ObservableInt(0)

    // 标题栏返回箭头的显示隐藏 1 显示 0 隐藏
    val btnBackVisibility = ObservableField("1")

    /**
     * 标题栏右图标点击事件 VM层重写setToolbarRightClick()
     */
    var ivToolbarIconOnClick = BindingCommand<Void>(BindingAction {
        if (ivToolbarIconRes.get() != 0 || !toolbarRightText.get().isNullOrBlank())
            setToolbarRightClick()
    })

    // 子类重写
    open fun setToolbarRightClick() {

    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    fun injectLifecycleProvider(lifecycle: LifecycleProvider<*>) {
        this.lifecycle = WeakReference(lifecycle)
    }

    val lifecycleProvider: LifecycleProvider<*>?
        get() = lifecycle.get()

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {}
    override fun onCreate() {}
    override fun onDestroy() {}
    override fun onStart() {}
    override fun onStop() {}
    override fun onResume() {}
    override fun onPause() {}

    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    val scrollToTopCommand: BindingCommand<Void> =
        BindingCommand(BindingAction { scrollToTop() })

    fun scrollToTop() {
        uC.scrollTopEvent?.call()
    }

    open fun addSubscribe(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    /**
     * 关闭界面
     */
    fun finish() {
        uC.finishEvent?.call()
    }

    override fun onCleared() {
        super.onCleared()
        model.onCleared()
        //ViewModel销毁时会执行，同时取消所有异步任务
        mCompositeDisposable.clear()
    }

    /**
     * 跳转容器页面
     * @param routePath Fragment路由地址
     * @param bundle    跳转所携带的信息
     */
    fun startContainerActivity(
        routePath: String,
        bundle: Bundle? = null, requestCode: Int? = null
    ) {
        val params: MutableMap<String, Any> = HashMap()
        params[ParameterField.ROUTE_PATH] = routePath
        if (bundle != null) params[ParameterField.BUNDLE] = bundle
        if (requestCode != null) params[ParameterField.REQUEST_CODE] = requestCode
        uC.startContainerActivityEvent?.postValue(params)
    }

    override fun accept(disposable: Disposable?) {
        disposable?.let { addSubscribe(it) }
    }

    object ParameterField {
        const val ROUTE_PATH = "ROUTE_PATH"
        const val BUNDLE = "BUNDLE"
        const val REQUEST_CODE = "REQUEST_CODE"
    }

}