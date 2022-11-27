package com.sodosi.ui.onboarding.start

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.sodosi.R
import com.sodosi.databinding.FragmentStartBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.common.extensions.navigate
import com.sodosi.ui.onboarding.OnboardingType
import com.sodosi.ui.onboarding.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *  StartFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class StartFragment : BaseFragment<OnboardingViewModel, FragmentStartBinding>() {
    override fun getViewBinding() = FragmentStartBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        (activity as BaseActivity<*, *>).changeStatusBarColorBlack()

        binding.btnOnBoardingStart.setOnClickListener {
            navigate(R.id.fragment_start) {
                findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentPhoneNumber(
                    OnboardingType.SIGNUP
                ))
            }
        }

        binding.btnLogin.setOnClickListener {
            navigate(R.id.fragment_start) {
                findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentPhoneNumber(
                    OnboardingType.LOGIN
                ))
            }
        }

        initOnboardingGif()
    }

    override fun observeData() {

    }

    private fun initOnboardingGif() {
        Glide.with(this)
            .load(R.raw.onboarding)
            .into(binding.ivOnboarding)

        Glide.with(this)
            .asGif()
            .load(R.raw.onboarding_message)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    resource?.setLoopCount(1)
                    return false
                }
            })
            .into(binding.ivFirstTitle)
    }
}
