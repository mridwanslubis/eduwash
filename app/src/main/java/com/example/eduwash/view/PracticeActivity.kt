package com.example.eduwash.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.eduwash.MainActivity
import com.example.eduwash.R
import com.example.eduwash.config.AppPrefs
import com.example.eduwash.hide
import com.example.eduwash.show
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.android.synthetic.main.activity_on_boarding.nextBtn
import kotlinx.android.synthetic.main.activity_on_boarding.startBtn
import kotlinx.android.synthetic.main.activity_practice.*

class PracticeActivity : AppCompatActivity() {

    private lateinit var sliderAdapter: SliderAdapter
    private var dots: Array<TextView?>? = null
    private lateinit var layouts: Array<Int>
    private val sliderChangeListener = object: ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            //do nothing
        }

        override fun onPageSelected(position: Int) {
            addBottomDots(position)
            if (position == layouts.size.minus(1)) {
                nextBtn.hide()
                prevBtn.hide()
                homeBtn.show()
            } else {
                nextBtn.show()
                prevBtn.show()
                homeBtn.hide()
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            //do nothing
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        init()
        dataSet()
        interaction()
    }

    private fun init() {
        layouts = arrayOf(
            R.layout.practice_slide1,
            R.layout.practice_slide2,
            R.layout.practice_slide3
        )
        sliderAdapter = SliderAdapter(this, layouts)
    }

    private fun dataSet() {
        addBottomDots(0)
        sliderPractice.apply {
            adapter = sliderAdapter
            addOnPageChangeListener(sliderChangeListener)
        }
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)

        dotsPracticeLayout.removeAllViews()
        for (i in 0 until dots!!.size) {
            dots!![i] = TextView(this)
            dots!![i]?.text = Html.fromHtml("&#8226;")
            dots!![i]?.textSize = 35f
            dots!![i]?.setTextColor(resources.getColor(R.color.colorGrey))
            dotsPracticeLayout.addView(dots!![i])
        }

        if (dots!!.isNotEmpty()) {
            dots!![currentPage]?.setTextColor(resources.getColor(R.color.colorIndigo))
        }
    }

    private fun interaction() {
        prevBtn.setOnClickListener {
            val current = getCurrentScreen(-1)
            if (current < layouts.size) {
                sliderPractice.currentItem = current
            } else {
                navigateToHome()
            }
        }
        homeBtn.setOnClickListener {
            // Launch login screen
            navigateToHome()
        }
        nextBtn.setOnClickListener {
            val current = getCurrentScreen(+1)
            if (current < layouts.size) {
                sliderPractice.currentItem = current
            } else {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        AppPrefs(this).setFirstTimeLaunch(false)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun getCurrentScreen(i: Int): Int = sliderPractice.currentItem.plus(i)
}