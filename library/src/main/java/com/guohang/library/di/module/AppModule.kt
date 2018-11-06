package com.guohang.library.di.module

import android.os.Bundle
import com.guohang.library.base.BaseApp
import com.guohang.library.base.IPresenter
import com.guohang.library.dlg.LoadingDlg
import com.guohang.library.net.NetConfig
import dagger.Module
import dagger.Provides

@Module()
class AppModule(private var mApp: BaseApp , private var mNetConfig: NetConfig , private var mLoadingId: Int) {
    @Provides
    fun provideApp() = mApp

    @Provides
    fun providePresenter() : IPresenter? = null

    @Provides
    fun provideLoading() = LoadingDlg().apply { arguments = Bundle().apply { putInt("layoutId" , mLoadingId) }}

    @Provides
    fun provideNetConfit() = mNetConfig
}