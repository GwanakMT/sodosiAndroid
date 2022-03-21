package com.sodosi.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sodosi.databinding.ActivitySodosiListBinding

class SodosiListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySodosiListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySodosiListBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}