package com.tera.custom_timepicker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.tera.custom_timepicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        const val HOUR = "hour"
        const val MIN = "min"
        const val SEC = "sec"
        const val PICKER_VALUE_2 = "picker_value_2"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sp: SharedPreferences
    private var mHour = 0
    private var mMin = 0
    private var mSec = 0
    private var pickerValue2 = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ориентация
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        sp = getSharedPreferences("settings", Context.MODE_PRIVATE)
        mHour = sp.getInt(HOUR, 0)
        mMin = sp.getInt(MIN, 0)
        mSec = sp.getInt(SEC, 0)

        setPicker()
        setText()
        init()
        regBack()
    }

    private fun setPicker() = with(binding) {
        picker.hour = mHour
        picker.min = mMin
        picker.sec = mSec
    }

    private fun setText() = with(binding) {
        var str = mHour.toString()
        tvHour.text = str
        str = mMin.toString()
        tvMin.text = str
        str = mSec.toString()
        tvSec.text = str
    }

    private fun init() = with(binding) {
        picker.setOnChangeListener {hour, min, sec ->
            mHour = hour
            mMin = min
            mSec = sec
            setText()
        }
        bnGetTime.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainActivity2::class.java))
        }
    }

    override fun onStop() {
        super.onStop()
        val editor = sp.edit()
        editor.putInt(HOUR, mHour)
        editor.putInt(MIN, mMin)
        editor.putInt(SEC, mSec)
        editor.putInt(PICKER_VALUE_2, pickerValue2)
        editor.apply()
    }

    // Регистрация кнопки Back
    private fun regBack() {
        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                exitOnBackPressed()
            }
        } else {
            onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        exitOnBackPressed()
                    }
                })
        }
    }

    // Кнопка Back
    fun exitOnBackPressed() {
        finishAffinity() // Закрыть все
    }
}