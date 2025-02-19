package com.tera.custom_timepicker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import com.tera.custom_timepicker.databinding.ActivityMain2Binding
import java.util.Calendar

class MainActivity2 : AppCompatActivity() {

    companion object {
        const val KEY_SOUND = "key_sound"
    }

    private lateinit var binding: ActivityMain2Binding
    private var mHour = 0
    private var mMin = 0
    private var mSec = 0

    private var volume = 0.1f
    private var keySound = true

    private var sounds: SoundPool? = null
    private var soundId = 0

    private var timer: CountDownTimer? = null
    private val timeMillis = 500L
    private val interval = 20L
    private var scrollSize = 80
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ориентация
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        sp = getSharedPreferences("set", Context.MODE_PRIVATE)
        keySound = sp.getBoolean(KEY_SOUND, true)

        initButtons()
        setIconButton()
        createSoundPool()
        loadSounds()

        handler.postDelayed({
            startTimer()
        }, 500)
    }

    private fun initButtons() = with(binding) {
        picker2.setOnChangeListener { hour, min, sec ->
            mHour = hour
            mMin = min
            mSec = sec
            setText()
            if (keySound) playAlarm()
        }
        bnSound.setOnClickListener {
            keySound = !keySound
            setIconButton()
        }
        bnCancel.setOnClickListener {
            startActivity(Intent(this@MainActivity2, MainActivity::class.java))
        }
    }

    private fun setText() = with(binding){
        var str = mHour.toString()
        tvHour.text = str
        str = mMin.toString()
        tvMin.text = str
        str = mSec.toString()
        tvSec.text = str
    }

    private fun setPicker() = with(binding) {
        val calendar = Calendar.getInstance()
        mHour = calendar[Calendar.HOUR_OF_DAY]
        mMin = calendar[Calendar.MINUTE]
        mSec = calendar[Calendar.SECOND]
        picker2.hour = mHour
        picker2.min = mMin
        picker2.sec = mSec
        setText()
    }

    private fun setIconButton() = with(binding) {
        if (keySound)
            bnSound.setImageResource(R.drawable.ic_volume_up_gray)
        else
            bnSound.setImageResource(R.drawable.ic_volume_off_gray)
    }

    private fun startTimer() = with(binding) {
        volume = 0.3f
        timer?.cancel()
        timer = object : CountDownTimer(timeMillis, interval) {
            override fun onTick(timeM: Long) {
                if (keySound) playAlarm()
                runOnUiThread {
                    picker2.scrollHour = scrollSize
                    picker2.scrollMin = -scrollSize
                    picker2.scrollSec = scrollSize
                }
            }
            override fun onFinish() {
                volume = 0.1f
                handler.postDelayed({
                    setPicker()
                }, 200)
            }
        }.start()
    }

    // Signal
    private fun createSoundPool() {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        sounds = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()
    }

    private fun loadSounds() {
        soundId = sounds!!.load(this, R.raw.click, 1)
    }

    private fun playAlarm() {
        sounds?.play(soundId, volume, volume, 1, 0, 1f)
    }

    override fun onStop() {
        super.onStop()
        val editor = sp.edit()
        editor.putBoolean(KEY_SOUND, keySound)
        editor.apply()
        timer?.cancel()
    }

}