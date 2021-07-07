package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener


class MainActivity : AppCompatActivity() {
    private lateinit var carousel: ViewPager
    private lateinit var dataList: RecyclerView
    private lateinit var searchBox: EditText

    private var model: MyModel = MyModel()
    private val a = intArrayOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_background,
        R.mipmap.ic_launcher
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        carousel = findViewById(R.id.viewpager)
        dataList = findViewById(R.id.recycler)
        searchBox = findViewById(R.id.search_edit_text)
        setupModel()
        initView()
    }


    private fun setupModel() {
        a.forEachIndexed { index, it ->
            val dataList = mutableListOf<String>()
            for (i in 0..50) {
                dataList.add("Image $index: Data item ${i + 1} ")
            }
            model.list.add(ImageListModel(it, dataList))
        }
    }

    private fun initView() {
        setupDataList()
        setupCarousel()
        setupSearchBox()
    }

    private fun setupSearchBox() {
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val displayedDataList = model.list[carousel.currentItem]
                (dataList.adapter as? RecyclerAdapter)?.apply {
                    if (s.isNullOrEmpty()) {
                        updateDataSet(displayedDataList.data)
                    } else {
                        updateDataSet(displayedDataList.data.filter { item -> item.contains(s) })
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun setupCarousel() {
        val adapter = ViewPagerAdapter(this, model.getAllImageList())
        carousel.adapter = adapter

        carousel.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                searchBox.text.clear()
                searchBox.clearFocus()
            }

            override fun onPageSelected(position: Int) {
                (dataList.adapter as? RecyclerAdapter)?.updateDataSet(model.list[position].data)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setupDataList() {
        dataList.adapter = RecyclerAdapter().apply {
            updateDataSet(model.list[0].data)
        }
        dataList.layoutManager = LinearLayoutManager(this)
    }


}