package com.sodosi.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.sodosi.R
import com.sodosi.databinding.ActivitySplashBinding
import com.sodosi.ui.onboarding.OnboardingActivity
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.sub_black)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)

            startActivity(Intent(this@SplashActivity, OnboardingActivity::class.java))
            overridePendingTransition(R.anim.splash_enter, R.anim.splash_exit)

            finish()
        }
    }
}
