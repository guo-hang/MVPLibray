package com.guohang.library.dlg

import android.app.FragmentManager
import com.guohang.library.base.BaseDlg

class LoadingDlg: BaseDlg() {

    override fun getLayoutId(): Int = arguments?.getInt("layoutId") ?: 0

    override fun initView() {
    }

    override fun initData() {
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            attributes = attributes.apply { dimAmount = 0.5f }
        }
    }

}