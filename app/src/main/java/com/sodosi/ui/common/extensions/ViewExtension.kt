package com.sodosi.ui.common.extensions

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.res.Resources
import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 *  ViewExtension.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Fragment.navigate(from: Int, navigate: () -> Unit) {
    if (findNavController().currentDestination?.id == from) {
        navigate.invoke()
    }
}

fun ViewPager2.setCurrentItemWithDuration(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxWidth: Int = width // Default value taken from getWidth() from ViewPager2 view
) {
    val pxToDrag: Int = pagePxWidth * (item - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
            beginFakeDrag()
        }

        override fun onAnimationEnd(animation: Animator?) {
            endFakeDrag()
        }

        override fun onAnimationCancel(animation: Animator?) { /* Ignored */
        }

        override fun onAnimationRepeat(animation: Animator?) { /* Ignored */
        }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setGoneWithAnimation() {
    val transition = Fade()
    transition.duration = 100
    transition.addTarget(this)

    TransitionManager.beginDelayedTransition(parent as ViewGroup?, transition)
//    visibility = View.GONE
}

@SuppressLint("CheckResult")
fun ImageView.setImageWithUrl(url: String, radius: Int = 0) {
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // 디스크 캐시 리소스
        .skipMemoryCache(true) // 메모리 캐시 사용안함.
        .priority(Priority.HIGH) // 처리순서
        .centerCrop()

    if (radius != 0) options.transform(RoundedCorners(radius))

    Glide.with(context)
        .load(url)
        .apply(options)
        .into(this)
}
