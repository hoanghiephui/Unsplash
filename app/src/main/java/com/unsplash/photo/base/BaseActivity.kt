package com.unsplash.photo.base

import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.unsplash.photo.UnsplashApp.Companion.AUTHEN
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.network_state_item.*
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @LayoutRes
    abstract fun layoutRes(): Int

    abstract fun onActivityCreated(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val layoutRes = layoutRes()
        if (layoutRes > 0) setContentView(layoutRes)
        onActivityCreated(savedInstanceState)
    }

    fun isAuthorized(): Boolean {
        return sharedPreferences.getBoolean(AUTHEN, false)
    }

}