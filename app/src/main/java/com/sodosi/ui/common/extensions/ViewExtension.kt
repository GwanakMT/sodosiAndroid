package com.sodosi.ui.common.extensions

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

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

fun Bitmap.resize(size: Int): Bitmap {
    return Bitmap.createScaledBitmap(this, size.dp, size.dp, true)
}

/**
 * 뷰 높이 변경 애니메이션
 */
fun View.heightAnimation(targetHeight: Int, duration: Long = 300L) {
    val prevHeight = this.height
    val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.addUpdateListener { animation ->
        this.layoutParams.height = animation.animatedValue as Int
        this.requestLayout()
    }
    valueAnimator.duration = duration
    valueAnimator.start()
}

// 클릭 이벤트를 flow로 변환
fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        this.trySend(Unit)
    }
    awaitClose { setOnClickListener(null) }
}

// 마지막 발행 시간과 현재 시간 비교해서 이벤트 발행, 나머지는 무시.
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime > windowDuration) {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}

fun View.setClickEvent(
    uiScope: CoroutineScope,
    windowDuration: Long = 1000, // 기본 1000 밀리 세컨드
    onClick: () -> Unit,
) {
    clicks()
        .throttleFirst(windowDuration)
        .onEach { onClick.invoke() }
        .launchIn(uiScope)
}
