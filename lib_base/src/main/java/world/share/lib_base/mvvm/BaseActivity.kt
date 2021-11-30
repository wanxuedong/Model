package world.share.lib_base.mvvm

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import world.share.baseutils.statusbar.StatusBarUtil
import world.share.lib_base.R
import world.share.lib_base.data.DataRepository
import world.share.lib_base.mvvm.StandardActivity.BaseRxActivity
import world.share.lib_base.mvvm.viewmodel.AppViewModelFactory
import world.share.lib_base.mvvm.viewmodel.BaseViewModel
import world.share.lib_base.route.RouteCenter
import java.lang.reflect.ParameterizedType

/**
 * @author wan
 * 创建日期：2021/11/23
 * 描述：Mvvm设计Activity基类
 */
abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel<*>> : BaseRxActivity(),
    View.OnClickListener {

    /**
     * 加载框
     * **/
    private var loadingDialog: AlertDialog? = null

    /**
     * 最终绑定的视图
     * **/
    protected lateinit var binding: V

    /**
     * 视图数据容器
     * **/
    lateinit var viewModel: VM

    /**
     * 视图数据绑定id
     * **/
    private var viewModelId = 0

    /**
     * 启用标题栏时的根布局
     * **/
    private var rootBinding: ViewDataBinding? = null

    /**
     * 连续点击间隔
     */
    private val clickInterval = 1500
    private var currentTime: Long = 0

    val dataRepository: DataRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setStatusBar()
        //私有的初始化dataBinding和ViewModel方法
        initViewDataBinding()
        initData()
        initEvent()
    }

    /**
     * 添加根内容布局id（目前在xml内加了标题栏）
     *
     * @return
     */
    protected open fun addParentContentView(): Int {
        return 0
    }

    /**
     * 获取页面布局
     *
     * @return 页面布局id
     */
    abstract fun attachView(): Int

    /**
     * 注入绑定
     */
    private fun initViewDataBinding() {
        if (useBaseLayout()) {
            setContentView(R.layout.activity_base)
            val mActivityRoot = findViewById<ViewGroup>(R.id.activity_root)
            var parentContent: View = mActivityRoot
            // 绑定根布局
            rootBinding = DataBindingUtil.bind(parentContent)
            rootBinding?.setVariable(initVariableId(), initViewModel())
            rootBinding?.lifecycleOwner = this
            // 在根布局添加公共布局 目前只添加了标题栏
            if (addParentContentView() != 0) {
                parentContent = LayoutInflater.from(this).inflate(addParentContentView(), null)
                mActivityRoot.addView(parentContent)
            }
            binding = DataBindingUtil.inflate(
                layoutInflater,
                attachView(),
                parentContent as ViewGroup,
                true
            )
        } else {
            binding = DataBindingUtil.setContentView(this, attachView())
        }
        viewModelId = initVariableId()
        viewModel = initViewModel()
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.lifecycleOwner = this
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel)
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this)
    }

    /**
     * @return 是否需要标题栏
     */
    protected open fun useBaseLayout(): Boolean {
        return true
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    /**
     * 根据继承的泛型动态初始化ViewModel
     * 子类无须重写该方法 只需指定泛型即可
     * @return 继承BaseViewModel的ViewModel
     */
    private fun initViewModel(): VM {
        val type = javaClass.genericSuperclass
        val modelClass = (type as ParameterizedType).actualTypeArguments[1] as Class<VM>
        return ViewModelProvider(this, get<AppViewModelFactory>()).get(modelClass)
    }

    open fun initData() {}
    open fun initEvent() {}
    override fun onClick(v: View) {
        if (System.currentTimeMillis() - currentTime > clickInterval) {
            currentTime = System.currentTimeMillis()
            onClick(v.id)
        }
    }

    /**
     * 防重复点击监听
     * **/
    open fun onClick(id: Int) {}

    /**
     * 路由页面跳转
     */
    fun jump(target: String?) {
        target?.let { RouteCenter.navigate(it) }
    }

    /**
     * 网络请求的时候显示正在加载的对话框
     */
    private val onKeyListener =
        DialogInterface.OnKeyListener { _: DialogInterface?, keyCode: Int, event: KeyEvent -> keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0 }

    fun showDialog() {
        try {
            if (null == loadingDialog) {
                loadingDialog = AlertDialog.Builder(this).setView(ProgressBar(this))
                    .setCancelable(false)
                    .setOnKeyListener(onKeyListener)
                    .create()
                loadingDialog!!.setCanceledOnTouchOutside(false)
                val window = loadingDialog!!.window
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            if (!loadingDialog!!.isShowing) {
                loadingDialog!!.show()
            }
        } catch (e: Exception) {
        }
    }

    fun disMissDialog() {
        try {
            if (null != loadingDialog) {
                if (loadingDialog!!.isShowing) {
                    loadingDialog!!.dismiss()
                }
                loadingDialog = null
            }
        } catch (e: Exception) {
        }
    }

    /**
     * 是否设置成透明状态栏，即就是全屏模式
     */
    protected open val isUseFullScreenMode: Boolean
        get() = false

    /**
     * 更改状态栏颜色，只有非全屏模式下有效
     */
    protected val statusBarColor: Int
        get() = R.color.white

    /**
     * 是否改变状态栏文字颜色为黑色，默认为黑色
     */
    protected open val isUseBlackFontWithStatusBar: Boolean
        get() = false

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isUseFullScreenMode) {
                StatusBarUtil.transparencyBar(this)
            } else {
                StatusBarUtil.setStatusBarColor(this, statusBarColor)
            }
            if (isUseBlackFontWithStatusBar) {
                StatusBarUtil.setLightStatusBar(this, true, isUseFullScreenMode)
            }
        }
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {}
}