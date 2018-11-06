package com.guohang.library.base

import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * Created by guohang on 2018/6/19.
 */
interface IView {
    fun showLoading()
    fun hideLoading()
    fun showMsg(msg: String)
    fun showMsg(resId: Int)
//    fun showErrorView()
    fun <T> bindLifecycle(): LifecycleTransformer<T>
}