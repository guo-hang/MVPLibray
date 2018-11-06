package com.guohang.library.di.component

import com.guohang.library.base.BaseApp
import com.guohang.library.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class , AndroidSupportInjectionModule::class  , AppModule::class])
interface AppComponent {
    fun inject(app: BaseApp)
}