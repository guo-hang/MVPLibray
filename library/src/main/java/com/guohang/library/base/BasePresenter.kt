package com.guohang.library.base

open class BasePresenter<V:IView , M:IModel>(open var mView: V?, open var mModel: M?): IPresenter {
    override fun onDestroy() {
        mView = null

        mModel?.onDestroy()
        mModel = null

    }
}