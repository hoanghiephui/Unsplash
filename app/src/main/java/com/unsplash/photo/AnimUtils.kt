package com.unsplash.photo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.ColorMatrix
import android.os.Build
import android.util.Property
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import com.unsplash.photo.Utils.dpToPx

object AnimUtils {

    private var fastOutSlowIn: Interpolator? = null

    /**
     * An implementation of [android.util.Property] to be used specifically with fields of
     * type
     * `float`. This type-specific subclass enables performance benefit by allowing
     * calls to a [set()][.set] function that takes the primitive
     * `float` type and avoids autoboxing and other overhead associated with the
     * `Float` class.
     *
     * @param <T> The class on which the Property is declared.
    </T> */
    internal abstract class FloatProperty<T>(name: String) : Property<T, Float>(Float::class.java, name) {

        /**
         * A type-specific override of the [.set] that is faster when dealing
         * with fields of type `float`.
         */
        abstract fun setValue(`object`: T, value: Float)

        override fun set(`object`: T, value: Float?) {
            setValue(`object`, value!!)
        }
    }

    /**
     * An extension to [ColorMatrix] which caches the saturation value for animation purposes.
     */
    class ObservableColorMatrix : ColorMatrix() {

        private var saturation = 1f

        private fun getSaturation(): Float {
            return saturation
        }

        override fun setSaturation(saturation: Float) {
            this.saturation = saturation
            super.setSaturation(saturation)
        }

        companion object {

            val SATURATION: Property<ObservableColorMatrix, Float> =
                object : FloatProperty<ObservableColorMatrix>("saturation") {

                    override fun setValue(cm: ObservableColorMatrix, value: Float) {
                        cm.setSaturation(value)
                    }

                    override fun get(cm: ObservableColorMatrix): Float {
                        return cm.getSaturation()
                    }
                }
        }
    }

    fun translationYInitShow(v: View, delay: Int) {
        v.visibility = View.INVISIBLE
        val anim = ObjectAnimator
            .ofFloat(v, "translationY", dpToPx(72, v.context), 0f)
            .setDuration(300)

        anim.interpolator = DecelerateInterpolator()
        anim.startDelay = delay.toLong()
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                v.visibility = View.VISIBLE
            }
        })
        anim.start()
    }

    fun alphaInitShow(v: View, delay: Int) {
        v.visibility = View.INVISIBLE
        val anim = ObjectAnimator
            .ofFloat(v, "alpha", 0f, 1f)
            .setDuration(300)

        anim.interpolator = DecelerateInterpolator()
        anim.startDelay = delay.toLong()
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                v.visibility = View.VISIBLE
            }
        })
        anim.start()
    }

    @JvmOverloads
    fun animShow(v: View, duration: Int = 300, from: Float = 0f, to: Float = 1f) {
        if (v.visibility == View.GONE) {
            v.visibility = View.VISIBLE
        }
        v.clearAnimation()
        if (from != to) {
            ObjectAnimator
                .ofFloat(v, "alpha", from, to)
                .setDuration(duration.toLong())
                .start()
        }
    }

    @JvmOverloads
    fun animHide(v: View, duration: Int = 300, from: Float = v.alpha, to: Float = 0f, gone: Boolean = true) {
        v.clearAnimation()
        if (from != to) {
            val anim = ObjectAnimator
                .ofFloat(v, "alpha", from, to)
                .setDuration(duration.toLong())
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    if (gone) {
                        v.visibility = View.GONE
                    }
                }
            })
            anim.start()
        }
    }

    fun animScale(v: View, duration: Int, delay: Int, scaleTo: Float) {
        val scaleX = ObjectAnimator.ofFloat(v, "scaleX", v.scaleX, scaleTo)
        val scaleY = ObjectAnimator.ofFloat(v, "scaleY", v.scaleY, scaleTo)

        val set = AnimatorSet()
        set.duration = duration.toLong()
        if (delay > 0) {
            set.startDelay = delay.toLong()
        }
        set.interpolator = DecelerateInterpolator()

        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                if (v.visibility != View.VISIBLE) {
                    v.visibility = View.VISIBLE
                }
            }
        })

        set.play(scaleX).with(scaleY)
        set.start()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun getFastOutSlowInInterpolator(context: Context): Interpolator? {
        if (fastOutSlowIn == null) {
            fastOutSlowIn = AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_slow_in)
        }
        return fastOutSlowIn
    }
}