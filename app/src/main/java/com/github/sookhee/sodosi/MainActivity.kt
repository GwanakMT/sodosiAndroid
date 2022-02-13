package com.github.sookhee.sodosi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sookhee.sodosi.databinding.ActivityMainBinding
import com.skt.Tmap.TMapView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val tmapview = TMapView(this);
        tmapview.setSKTMapApiKey(TMAP_API_KEY)

        binding.rootView.addView(tmapview)

        setContentView(binding.root)
    }

    companion object {
        private val TMAP_API_KEY = BuildConfig.TMAP_API_KEY
    }
}
