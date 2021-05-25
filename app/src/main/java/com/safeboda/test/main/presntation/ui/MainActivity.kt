package com.safeboda.test.main.presntation.ui

import com.safeboda.test.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.safeboda.test.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

fun Context.newMainActivityIntent() = Intent(this, MainActivity::class.java)

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}