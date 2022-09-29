package com.siamsquared.news2.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.siamsquared.news2.R
import com.siamsquared.news2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}