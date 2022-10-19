package com.whow.app

import android.os.Bundle
import android.util.Log
import android.widget.CheckedTextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val checkedTextView = findViewById<CheckedTextView>(R.id.text_checked)
        checkedTextView.setOnClickListener {
            checkedTextView.toggle()
            Log.d(TAG, checkedTextView.isChecked.toString())
        }
        Log.d(TAG, "onCreate: $localClassName")
        /*PromptDialog.Builder(this).apply {
            setMessage("欢迎来到PromptDialog")
        }.show("1")*/

    }
}