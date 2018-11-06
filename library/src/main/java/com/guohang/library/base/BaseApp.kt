package com.guohang.library.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.guohang.library.di.component.DaggerAppComponent
import com.guohang.library.di.module.AppModule
import com.guohang.library.manager.ConfigManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import java.lang.ref.WeakReference
import javax.inject.Inject


/**
 * Created by guohang on 2018/6/19.
 */

class BaseApp : Application() , HasActivityInjector , HasSupportFragmentInjector{
    companion object {
        lateinit var mInstance : BaseApp
    }

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject lateinit var mConfigManager: ConfigManager

    var mCurAct: WeakReference<Activity>? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        mConfigManager.attachBaseContext(base)
    }

    override fun onCreate() {

        super.onCreate()
        mInstance = this

        //init dagger2
        DaggerAppComponent.builder().appModule(AppModule(this , mConfigManager.getNetConfig() , mConfigManager.getLoadingId()))
                .build().inject(this)

        registerActivity()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    private fun registerActivity() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks{
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}

            override fun onActivityStarted(activity: Activity?) {}

            override fun onActivityResumed(activity: Activity?) {
                activity?.apply { mCurAct = WeakReference(this) }
            }

            override fun onActivityPaused(activity: Activity?) {}

            override fun onActivityStopped(activity: Activity?) {}

            override fun onActivityDestroyed(activity: Activity?) {}

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
        })
    }
}


