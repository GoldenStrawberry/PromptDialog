package com.whow.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.whow.promptdialog.PromptDialog

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: $localClassName")
        PromptDialog.Builder(this).apply {
            setMessage("欢迎来到PromptDialog")
        }.show("1")

    }
}