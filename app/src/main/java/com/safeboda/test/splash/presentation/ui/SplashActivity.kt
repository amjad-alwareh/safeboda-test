package com.safeboda.test.splash.presentation.ui

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.safeboda.test.databinding.ActivitySplashBinding
import com.safeboda.test.main.presntation.ui.newMainActivityIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.startAnimation(
            AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        )

        lifecycleScope.launch {
            delay(2048)
            startActivity(newMainActivityIntent())
            finish()
        }
    }

}