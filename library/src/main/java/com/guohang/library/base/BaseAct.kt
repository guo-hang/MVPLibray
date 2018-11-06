package com.guohang.library.base

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.guohang.library.util.ToastUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by guohang on 2018/6/15.
 */
abstract class BaseAct<P: IPresenter>: RxAppCompatActivity() , IView {
    @JvmField @Inject var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isInject()) AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        setContentView()
        initView()

//        if (isRegister()) RxBus.get().register(this)
        initData()
    }

    private fun setContentView() {
        getLayoutId()?.apply {
            if (0 != this) setContentView(this)
        }
    }

    abstract fun getLayoutId(): Int?
    abstract fun initView()
    abstract fun initData()

    open fun isInject() = true
    open fun isFullScreen() = false
    open fun isRegister() = false

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
        mPresenter = null
//        if (isRegister()) RxBus.get().unregister(this)
    }

    override fun onBackPressed() {
        if (isSoftInputShown()) {
            hideSoftInput()
        } else {
            super.onBackPressed()
        }
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commitAllowingStateLoss()
    }

    inline fun EditText.addTextChangedListener(crossinline func: Editable.() -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.func()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showMsg(msg: String) {
        ToastUtil.show(msg)
    }

    override fun showMsg(resId: Int) {
        ToastUtil.show(resId)
    }

    override fun <T> bindLifecycle(): LifecycleTransformer<T> = this.bindToLifecycle()

    //===========================================   键盘  ===========================================================
    private fun isSoftInputShown(): Boolean {
        val screenHeight = window.decorView.height

        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)

        return screenHeight - rect.bottom > screenHeight * 0.15
    }

    private fun hideSoftInput(view: View = window.decorView) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showSoftInput(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.showSoftInput(view, 0)
    }
}