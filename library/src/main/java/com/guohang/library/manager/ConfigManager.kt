package com.guohang.library.manager

import android.content.Context
import com.guohang.library.net.NetConfig
import javax.inject.Inject

class ConfigManager @Inject constructor() {
    lateinit var configModules:List<ConfigModule>

    fun attachBaseContext(base: Context) {
        configModules = ManifestParser.parse(base)
        configModules.forEach {
            it.attachBaseContext(base)
        }
    }


    fun getNetConfig(): NetConfig {
        val netConfig = NetConfig()
        configModules.forEach {
            it.setNetConfig(netConfig)
        }
        return netConfig
    }

    fun getLoadingId(): Int {
        var layoutId = 0
        configModules.forEach {
            val id = it.loadingLayoutId()
            if (id != null && id > 0) layoutId = id
        }
        return layoutId
    }
}