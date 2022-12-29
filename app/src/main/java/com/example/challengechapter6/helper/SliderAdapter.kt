package com.example.challengechapter6.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.challengechapter6.R


class SliderAdapter(context: Context) : PagerAdapter() {

    var context: Context? = context
    private lateinit var layoutInflater: LayoutInflater
    private val images = arrayOf<Int>(
        R.drawable.landing_page1,
        R.drawable.landing_page2,
        R.drawable.landing_page3,
    )

    private val string = arrayOf<Int>(
        R.string.slider_1,
        R.string.slider_2,
        R.string.slider_3,
    )

    override fun getCount(): Int {
        return images.size;
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context).context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.layout_slides, null)
        val imageView: ImageView = view.findViewById(R.id.slider_image)
        val textView: TextView = view.findViewById(R.id.slider_heading)
        val btnPlay: Button = view.findViewById(R.id.btn_play)

        btnPlay.visibility = View.INVISIBLE

        imageView.setImageResource(images[position])
        textView.setText(string[position])

        if (position == 2){
            btnPlay.visibility = View.VISIBLE
        }

        val viewPager = container as ViewPager
        viewPager.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }
}
