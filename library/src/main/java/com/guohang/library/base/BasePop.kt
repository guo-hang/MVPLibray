package com.guohang.library.base

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.PopupWindow
import javax.inject.Inject

abstract class BasePop constructor(var mContext: Activity)  : PopupWindow(mContext) {

    init {
        contentView = View.inflate(mContext , getLayoutId() , null)
        initSetting()
        initView()
    }

    private fun initSetting() {
        isFocusable = true
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    abstract fun getLayoutId(): Int
    abstract fun initView()

    public fun setWindowAlpha(alpha: Float) {
        mContext.window?.apply {
            attributes = attributes.apply { this.alpha = alpha }
        }
    }
}