package com.gg.mmkvutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private var bb by mmkv<String>("test")

    private var aa by mmkv.string("")

    private var bean by mmkv.parcelable<TestBean>("test")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}