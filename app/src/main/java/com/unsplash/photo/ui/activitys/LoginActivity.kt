package com.unsplash.photo.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import com.unsplash.photo.R
import com.unsplash.photo.UnsplashApp.Companion.ACCESS_TOKEN
import com.unsplash.photo.UnsplashApp.Companion.AUTHEN
import com.unsplash.photo.UnsplashApp.Companion.UNSPLASH_LOGIN_CALLBACK
import com.unsplash.photo.Utils.getLoginUrl
import com.unsplash.photo.Utils.startWebActivity
import com.unsplash.photo.base.BaseActivity
import com.unsplash.photo.extensions.Bundle
import com.unsplash.photo.extensions.observeNotNull
import com.unsplash.photo.repository.base.NetworkState
import com.unsplash.photo.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class LoginActivity : BaseActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
    }

    override fun layoutRes(): Int {
        return R.layout.activity_login
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        btnLoginn.setOnClickListener {
            startWebActivity(this, getLoginUrl())
        }
        viewModel.authens.observeNotNull(this) {
            sharedPreferences.edit {
                putString(ACCESS_TOKEN, it.accessToken)
                putBoolean(AUTHEN, true)

                Intent(this@LoginActivity, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    putExtra("login", true)
                    this@LoginActivity.startActivity(this)
                    finish()
                }
            }
        }
        viewModel.networkState.observeNotNull(this) {
            progressBar.isVisible = it == NetworkState.LOADING
            btnLoginn.isVisible = it.msg != null
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null
            && intent.data != null
            && !TextUtils.isEmpty(intent.data!!.authority)
            && UNSPLASH_LOGIN_CALLBACK == intent.data!!.authority
        ) {
            intent.data?.getQueryParameter("code")?.let {
                viewModel.onAccessToken(it)
                btnLoginn.visibility = View.INVISIBLE
            }
        }
    }
}