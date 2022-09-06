package com.sodosi.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sodosi.R
import com.sodosi.databinding.ActivitySplashBinding
import com.sodosi.ui.main.MainActivity
import com.sodosi.ui.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    val viewModel: IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.sub_black)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.setVisitedTime(System.currentTimeMillis())

            delay(1000)

            val intent = if (viewModel.checkHasToken()) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, OnboardingActivity::class.java)
            }

            startActivity(intent)
            overridePendingTransition(R.anim.splash_enter, R.anim.splash_exit)

            finish()
        }
    }
}
