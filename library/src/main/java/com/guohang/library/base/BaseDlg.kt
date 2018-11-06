package com.guohang.library.base

import android.app.Activity
import android.app.FragmentTransaction
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

abstract class BaseDlg: DialogFragment() {
    var mRootView : View? = null


    override fun onAttach(context: Context) {
        if(this.isInject()) AndroidSupportInjection.inject(this)       //默认不注入

        super.onAttach(context)
    }

    override fun onAttach(activity: Activity) {
        if(this.isInject()) AndroidSupportInjection.inject(this)

        super.onAttach(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val layoutId = getLayoutId()
        if (layoutId == 0) return null

       return mRootView?.apply {
            (parent as? ViewGroup)?.removeView(this)
        }?: View.inflate(context , layoutId , null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    open fun isInject() = false
    abstract fun getLayoutId() : Int
    abstract fun initView()
    abstract fun initData()

//    override fun show(transaction: FragmentTransaction?, tag: String?): Int {
//        return super.show(transaction, tag)
//    }

    override fun show(manager: FragmentManager? , tag: String?) {
        try {
            manager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
            super.show(manager, tag)
        } catch (e: Exception) { }
    }
}