package com.abc.pythondemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xxx.xxx.zzzz.MainActivity


@SuppressLint("UnsafeDynamicallyLoadedCode", "SdCardPath")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this,MainActivity::class.java))
    }
}