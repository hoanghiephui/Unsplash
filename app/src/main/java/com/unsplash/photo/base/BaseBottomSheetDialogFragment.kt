package com.unsplash.photo.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.LayoutRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.unsplash.photo.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    private var disposal = CompositeDisposable()
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null

    @LayoutRes
    abstract fun layoutRes(): Int

    abstract fun onFragmentCreatedWithUser(view: View, savedInstanceState: Bundle?)
    protected open fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {}

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val contextThemeWrapper = ContextThemeWrapper(context, context?.theme)
        val themeAwareInflater = inflater.cloneInContext(contextThemeWrapper)
        val view = themeAwareInflater.inflate(layoutRes(), container, false)
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                onGlobalLayoutChanged(view)
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        disposal.clear()
        super.onDestroyView()
    }

    fun dismissDialog() = dialog?.dismiss()

    protected fun addDisposal(disposable: Disposable) {
        disposal.add(disposable)
    }

    protected fun removeAndAddDisposal(disposable: Disposable) {
        disposal.remove(disposable)
        disposal.add(disposable)
    }

    private fun onGlobalLayoutChanged(view: View) {
        val parent = dialog?.findViewById<ViewGroup>(R.id.design_bottom_sheet);
        if (parent != null) {
            parent.setBackgroundColor(Color.TRANSPARENT)
            bottomSheetBehavior = BottomSheetBehavior.from(parent)
            bottomSheetBehavior?.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(p0: View, p1: Float) {
                }

                override fun onStateChanged(p0: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) dialog?.cancel()
                }
            })
        }
    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment>? = this.childFragmentInjector
}