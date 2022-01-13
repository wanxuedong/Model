package com.czl.lib_base.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import world.share.lib_base.App
import world.share.lib_base.data.DataRepository
import world.share.lib_base.data.HttpDataSource
import world.share.lib_base.data.LocalDataSource
import world.share.lib_base.data.impl.HttpDataImpl
import world.share.lib_base.data.impl.LocalDataImpl
import world.share.lib_base.internet.http.retrofit.ApiService
import world.share.lib_base.internet.http.retrofit.RetrofitClient
import world.share.lib_base.mvvm.viewmodel.AppViewModelFactory

/**
 * @author wan
 * @Date 2021/11/24
 * 描述: 注入的module
 */
val appModule = module {
    single { androidApplication() as App }
    // single->单例式  factory->每次都创建不同实例  viewModel->VM注入
    // androidApplication()->获取当前Application , androidContext() -> 获取context
    // 1 . 获取api实例
    single { RetrofitClient.getInstance().create(ApiService::class.java) }
    // 2. 创建实例前若构造方法内有参数 则需先注入构造中的参数实例
    single<HttpDataSource> { HttpDataImpl(get()) }
    // 3. 获取本地数据调用的实例
    single<LocalDataSource> { LocalDataImpl() }
    // 4 .综合以上本地+网络两个数据来源 得到数据仓库
    single { DataRepository(get(), get()) }
    // bind 将指定的实例绑定到对应的class  single { AppViewModelFactory(androidApplication(), get()) } bind TestActivity::class
    single { AppViewModelFactory(get(), get()) }

}
val factoryModule = module {
}
val allModule = appModule + factoryModule

