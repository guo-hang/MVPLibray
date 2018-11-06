package com.guohang.library.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by guohang on 2018/6/19.
 */
abstract class BaseFrg<P: IPresenter?> : RxFragment(), IView {
    private var mRootView : View? = null

    @JvmField
    @Inject
    var mPresenter: P? = null

    override fun onAttach(context: Context) {
        initDagger()
        super.onAttach(context)
    }

    override fun onAttach(activity: Activity) {
        initDagger()
        super.onAttach(activity)
    }

    open fun initDagger() {
        if (isInject()) { AndroidSupportInjection.inject(this) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (mRootView?.parent as? ViewGroup)?.removeView(mRootView)

        val layoutId = getLayoutId()
        if (layoutId == 0) return null

        if (null == mRootView) {
            mRootView = View.inflate(context , layoutId , null)
        } else {
            (mRootView?.parent as? ViewGroup)?.removeView(mRootView)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
//        if (isRegister()) RxBus.get().register(this)
    }

//    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initView()
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        if (isRegister()) RxBus.get().unregister(this)
    }

    abstract fun getLayoutId() : Int
    abstract fun initView()
    abstract fun initData()

    open fun isRegister() = false
    open fun isInject() = false

    override fun showLoading() {
        if (this.isVisible) {
            (context as? BaseAct<IPresenter>)?.showLoading()
        }
    }

    override fun hideLoading() {
        (context as? BaseAct<IPresenter>)?.hideLoading()
    }

    override fun showMsg(msg: String) {
        (context as? BaseAct<IPresenter>)?.showMsg(msg)
    }

    override fun showMsg(resId: Int) {
        (context as? BaseAct<IPresenter>)?.showMsg(resId)
    }

    override fun <T> bindLifecycle(): LifecycleTransformer<T> = bindToLifecycle()
}