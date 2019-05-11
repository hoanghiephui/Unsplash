package com.unsplash.photo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.unsplash.photo.UnsplashApp.Companion.ACCESS_KEY
import com.unsplash.photo.UnsplashApp.Companion.UNSPLASH_LOGIN_CALLBACK
import com.unsplash.photo.UnsplashApp.Companion.UNSPLASH_URL
import com.unsplash.photo.model.PhotoResponse
import com.unsplash.photo.ui.widget.CoverImageView
import java.util.regex.Pattern

object Utils {
    @JvmStatic
    fun setNestedScrollingDisable(vararg view: RecyclerView) {
        view.forEach {
            ViewCompat.setNestedScrollingEnabled(it, false)
        }
    }

    @JvmStatic
    fun dpToPx(
        dp: Int,
        context: Context
    ): Float {
        val dpi = context.resources.displayMetrics.densityDpi
        return if (dpi == 0) {
            0f
        } else (dp * (dpi / 160.0)).toFloat()
    }

    fun onLoadImageCover(
        img: CoverImageView,
        photo: PhotoResponse,
        callback: OnLoadImageListener
    ) {
        val matrix = AnimUtils.ObservableColorMatrix()
        matrix.setSaturation(0f)
        img.colorFilter = ColorMatrixColorFilter(matrix)

        val size = photo.getRegularSize(img.context)
        Glide.with(img)
            .load(photo.getRegularSizeUrl(size))
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .override(size[0], size[1])
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback.onCompleted()
                    return false
                }
            }).into(img)
    }

    fun startSaturationAnimation(context: Context, target: ImageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            target.setHasTransientState(true)
            val matrix = AnimUtils.ObservableColorMatrix()
            val saturation = ObjectAnimator.ofFloat(
                matrix, AnimUtils.ObservableColorMatrix.SATURATION, 0f, 1f
            )
            saturation.addUpdateListener { target.colorFilter = ColorMatrixColorFilter(matrix) }
            saturation.duration = 2000
            saturation.interpolator = AnimUtils.getFastOutSlowInInterpolator(context)
            saturation.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    target.clearColorFilter()
                    target.setHasTransientState(false)
                }
            })
            saturation.start()
        }
    }

    fun backgroundColor(context: Context, color: String): Int {
        var mColor = color
        if (TextUtils.isEmpty(mColor) || !Pattern.compile("^#[a-fA-F0-9]{6}").matcher(mColor).matches() && !Pattern.compile(
                "^[a-fA-F0-9]{6}"
            ).matcher(mColor).matches()
        ) {
            return Color.argb(0, 0, 0, 0)
        } else {
            if (Pattern.compile("^[a-fA-F0-9]{6}").matcher(mColor).matches()) {
                mColor = "#$mColor"
            }
            val backgroundColor = Color.parseColor(mColor)
            val red = backgroundColor and 0x00FF0000 shr 16
            val green = backgroundColor and 0x0000FF00 shr 8
            val blue = backgroundColor and 0x000000FF
            return if (true) {
                Color.rgb(
                    (red + (255 - red) * 0.7).toInt(),
                    (green + (255 - green) * 0.7).toInt(),
                    (blue + (255 - blue) * 0.7).toInt()
                )
            } else {
                Color.rgb(
                    (red * 0.3).toInt(),
                    (green * 0.3).toInt(),
                    (blue * 0.3).toInt()
                )
            }
        }
    }

    fun getStatusBarHeight(r: Resources): Int {
        var result = 0
        val resourceId = r.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = r.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getLoginUrl(): String {
        return (UNSPLASH_URL + "oauth/authorize"
                + "?client_id=" + ACCESS_KEY
                + "&redirect_uri=" + "wallsplash%3A%2F%2F" + UNSPLASH_LOGIN_CALLBACK
                + "&response_type=" + "code"
                + "&scope=" + "public+read_user+write_user+read_photos+write_photos+write_likes+write_followers+read_collections+write_collections")
    }

    fun startWebActivity(context: Context, url: String) {
        val packageName = "com.android.chrome"
        val browserIntent = Intent()
        browserIntent.setPackage(packageName)
        val activitiesList = context.packageManager
            .queryIntentActivities(browserIntent, -1)
        if (activitiesList.size > 0) {
            startCustomTabActivity(context, url)
        } else {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    fun startCustomTabActivity(context: Context, url: String) {
        val builder = CustomTabsIntent.Builder().apply {
            setShowTitle(true)
            setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            setStartAnimations(context, R.anim.activity_slide_in, R.anim.activity_fade_out)
            setExitAnimations(context, R.anim.activity_slide_in, R.anim.activity_fade_out)
        }.build().launchUrl(context, Uri.parse(url))
    }
}

interface OnLoadImageListener {
    fun onCompleted()
}