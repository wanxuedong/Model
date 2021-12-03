package world.share.lib_base.mvvm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.czl.lib_base.callback.ErrorCallback
import com.czl.lib_base.callback.LoadingCallback
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import me.yokeyword.fragmentation.SupportFragment
import org.koin.android.ext.android.get
import world.share.lib_base.R
import world.share.lib_base.internet.bean.BaseResponse
import world.share.lib_base.mvvm.base.BaseRxFragment
import world.share.lib_base.mvvm.container.ContainerActivity
import world.share.lib_base.mvvm.viewmodel.AppViewModelFactory
import world.share.lib_base.mvvm.viewmodel.BaseViewModel
import world.share.lib_base.route.RouteCenter
import java.lang.reflect.ParameterizedType

/**
 * @author wan
 * 创建日期：2021/11/24
 * 描述：标准Mvvm碎片
 */
abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel<*>> : BaseRxFragment() {

    lateinit var binding: V
    lateinit var viewModel: VM

    /**
     * viewModelid
     * **/
    private var viewModelId = 0

    /**
     * 按需加载状态页面
     * **/
    lateinit var loadService: LoadService<BaseResponse<*>?>

    /**
     * 基础视图
     * **/
    private lateinit var rootView: View

    /**
     * 基础视图绑定
     * **/
    protected var rootBinding: ViewDataBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loadSir = LoadSir.Builder()
            .addCallback(ErrorCallback())
            .addCallback(LoadingCallback())
            .setDefaultCallback(SuccessCallback::class.java)
            .build()
        if (useBaseLayout()) {
            rootView = inflater.inflate(R.layout.activity_base, null, false)
                .findViewById(R.id.activity_root)
            rootBinding = DataBindingUtil.bind(rootView)
            binding =
                DataBindingUtil.inflate(inflater, attachView(), rootView as ViewGroup, true)
            // 有标题栏情况下绑定内容View
            loadService = loadSir.register(binding.root,
                Callback.OnReloadListener { reload() },
                Convertor<BaseResponse<*>?> { t ->
                    if (t == null || t.code != 0) {
                        ErrorCallback::class.java
                    } else {
                        SuccessCallback::class.java
                    }
                }) as LoadService<BaseResponse<*>?>
            return rootView
        } else {
            binding = DataBindingUtil.inflate(inflater, attachView(), container, false)
            loadService = loadSir.register(binding.root,
                Callback.OnReloadListener { reload() },
                Convertor<BaseResponse<*>?> { t ->
                    if (t == null || t.code != 0) {
                        ErrorCallback::class.java
                    } else {
                        SuccessCallback::class.java
                    }
                }) as LoadService<BaseResponse<*>?>
            return loadService.loadLayout
        }
    }

    abstract fun attachView(): Int

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        //私有的初始化DataBinding和ViewModel方法
        initViewDataBinding()
        //私有的ViewModel与View的契约事件回调逻辑
        registerUIChangeLiveDataCallBack()
    }

    /**
     * 正常创建启动Fragment情况 onViewCreated-onLazyInitView-onEnterAnimationEnd
     * Viewpager创建实例 onViewCreated-onLazyInitView
     */
    final override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (enableLazy()) {
            //页面数据初始化视图
            initView()
            //页面数据初始化方法
            initData()
            //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
            initEvent()
        }
    }

    /**
     * 入栈动画完毕后执行
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        if (!enableLazy()) {
            //页面数据初始化视图
            initView()
            //页面数据初始化方法
            initData()
            //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
            initEvent()
        }
    }

    /**
     * 是否开启懒加载,默认true
     *
     * @return
     */
    protected open fun enableLazy(): Boolean {
        return true
    }

    /**
     * 初始化视图
     * **/
    open fun initView() {}

    /**
     * 初始化数据
     * **/
    open fun initData() {}

    /**
     * 初始化事件
     * **/
    open fun initEvent() {

    }

    /**
     * 注入绑定
     */
    private fun initViewDataBinding() {
        viewModelId = initVariableId()
        viewModel = initViewModel()
        rootBinding?.setVariable(viewModelId, viewModel)
        rootBinding?.lifecycleOwner = this
        binding.setVariable(viewModelId, viewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.lifecycleOwner = this
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel)
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this)
        //传入VM层交由M层数据驱动处理UI状态
        viewModel.loadService = loadService
    }

    /**
     * 注册ViewModel与View的契约UI回调事件
     */
    private fun registerUIChangeLiveDataCallBack() {
        //加载对话框显示
        viewModel.uC.getShowLoadingEvent()
            .observe(this, { title: String? -> showLoading(title) })
        //加载对话框消失
        viewModel.uC.getDismissDialogEvent()
            .observe(this, { dismissLoading() })
        //跳入新页面
        viewModel.uC.getStartActivityEvent().observe(this, { map ->
            val routePath: String = map[BaseViewModel.ParameterField.ROUTE_PATH] as String
            val bundle = map[BaseViewModel.ParameterField.BUNDLE] as Bundle?
            RouteCenter.navigate(routePath, bundle)
        }
        )
        viewModel.uC.getStartFragmentEvent().observe(this, { map ->
            val routePath: String = map[BaseViewModel.ParameterField.ROUTE_PATH] as String
            val bundle: Bundle? = map[BaseViewModel.ParameterField.BUNDLE] as Bundle?
            start(RouteCenter.navigate(routePath, bundle) as SupportFragment)
        })
        //跳入ContainerActivity
        viewModel.uC.getStartContainerActivityEvent().observe(
            this, { params: Map<String?, Any?> ->
                val canonicalName = params[BaseViewModel.ParameterField.ROUTE_PATH] as String?
                val bundle = params[BaseViewModel.ParameterField.BUNDLE] as Bundle?
                val code = params[BaseViewModel.ParameterField.REQUEST_CODE] as Int?
                startContainerActivity(canonicalName, bundle, code)
            }
        )
        //关闭界面
        viewModel.uC.getFinishEvent().observe(this, {
            back()
        })
        //关闭上一层
        viewModel.uC.getOnBackPressedEvent().observe(
            this, { onBackPressedSupport() }
        )
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    /**
     * 动态实例化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    private fun initViewModel(): VM {
        val type = javaClass.genericSuperclass
        val modelClass: Class<VM> =
            (type as ParameterizedType).actualTypeArguments[1] as Class<VM>
        return ViewModelProvider(this, get() as AppViewModelFactory).get(modelClass)
    }

    /**
     * 跳转容器页面
     * @param routePath Fragment路由地址
     * @param bundle        跳转所携带的信息
     */
    fun startContainerActivity(
        routePath: String?,
        bundle: Bundle? = null, reqCode: Int? = null
    ) {
        val intent = Intent(context, ContainerActivity::class.java)
        intent.putExtra(ContainerActivity.FRAGMENT, routePath)
        if (bundle != null) intent.putExtra(ContainerActivity.BUNDLE, bundle)
        if (reqCode == null)
            startActivity(intent)
        else
            startActivityForResult(intent, reqCode)
    }

    fun showLoading(title: String?) {
    }

    fun dismissLoading() {
    }

    /**
     * 统一处理回退事件
     */
    open fun back() {
        if (preFragment == null) {
            requireActivity().finish()
        } else {
            pop()
        }
    }

    /**
     * @return 是否需要标题栏
     */
    protected open fun useBaseLayout(): Boolean {
        return false
    }

    /**
     * 子类重写页面重试加载逻辑
     */
    open fun reload() {
        loadService.showCallback(LoadingCallback::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
        rootBinding?.unbind()
    }

}