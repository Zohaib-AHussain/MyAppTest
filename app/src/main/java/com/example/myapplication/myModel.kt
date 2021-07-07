package com.example.myapplication

data class MyModel(val list: MutableList<ImageListModel> = mutableListOf()) {
    fun getAllImageList(): List<Int> {
        return mutableListOf<Int>().apply {
            list.forEach {
                add(it.image)
            }
        }
    }
}

data class ImageListModel(val image: Int, val data: List<String>)