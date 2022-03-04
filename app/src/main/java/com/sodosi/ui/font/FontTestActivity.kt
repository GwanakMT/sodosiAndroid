package com.sodosi.ui.font

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sodosi.databinding.ActivityFontTestBinding

class FontTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFontTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFontTestBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}