package com.example.challengechapter6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.challengechapter6.helper.SliderAdapter
import com.example.challengechapter6.databinding.ActivityLandingPageBinding

class LandingPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingPageBinding
    lateinit var viewAdapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        viewAdapter = SliderAdapter(this)
        binding.viewPager.adapter = viewAdapter;
        binding.viewPager.addOnPageChangeListener(changeListener)
        binding.dot3.attachTo(binding.viewPager);

        binding.ivChevron.setOnClickListener(View.OnClickListener {
            binding.viewPager.setCurrentItem(getItem(+1), true)
        })
    }

    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    private fun getItem(i: Int): Int {
        if (binding.viewPager.currentItem >= 1){
            binding.ivChevron.visibility = View.INVISIBLE
        }
        return binding.viewPager.currentItem + i
    }

    var changeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            if (position == 0) {
                binding.ivChevron.visibility = View.VISIBLE
            } else if (position == 1) {
                binding.ivChevron.visibility = View.VISIBLE
            } else if (position == 2) {
                binding.ivChevron.visibility = View.INVISIBLE
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }
}