package com.guohang.library.manager

import android.content.Context
import com.guohang.library.net.NetConfig

interface ConfigModule {
    fun attachBaseContext(base: Context)

    fun setNetConfig(netConfig: NetConfig)

    fun loadingLayoutId(): Int?
}