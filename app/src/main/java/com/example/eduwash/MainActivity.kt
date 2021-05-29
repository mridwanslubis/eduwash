package com.example.eduwash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eduwash.config.AppPrefs
import com.example.eduwash.view.OnBoardingActivity
import com.example.eduwash.view.PracticeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (AppPrefs(this).isFirstTimeLaunch()) {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_main)

        buttonPractice.setOnClickListener {
            startActivity(Intent(this, PracticeActivity::class.java))
            finish()
        }
    }
}