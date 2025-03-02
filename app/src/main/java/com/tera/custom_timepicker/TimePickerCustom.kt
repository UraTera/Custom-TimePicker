package com.tera.custom_timepicker

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import java.util.Calendar

typealias OnChangeListener = (hour: Int, min: Int, sec: Int) -> Unit

class TimePickerCustom(
    context: Context,
    attrs: AttributeSet?,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleRes) {

    constructor(context: Context, attributesSet: AttributeSet?) :
            this(context, attributesSet, 0)

    constructor(context: Context) : this(context, null)

    companion object {
        private const val FADING_EXTENT = 7
    }

    // Слушатель
    private var mListener: OnChangeListener? = null

    private var mViewWidth = VIEW_WIDTH // Ширина элемента
    private var mOffDividerX = DIVIDER_OFFSET // Отступ разделителя от края
    private var mItemHeight = ITEM_HEIGHT  // Высота строки
    private var mHintHeight = 0f    // Высота подсказки
    private var mShownCount = 3     // Количество строк
    private var mViewCount = 3      // Количество компонентов

    private var valueH = 0
    private var valueM = 0
    private var valueS = 0

    // Атрибуты
    private var mMaxHour = MAX_VALUE_HOUR
    private var mDividerColor = 0
    private var mDividerHeight = 0
    private var mDividerOffset = 0

    private var mFadingExtent = 0f
    private var mFontFamily = Typeface.DEFAULT

    private var mHintColor = 0
    private var mHintSize = 0f
    private var mHintTextH: String? = null
    private var mHintTextM: String? = null
    private var mHintTextS: String? = null
    private var mIntervalLongPress = 0

    private var mTextColor = 0
    private var mTextColorSel = 0
    private var mTextSize = 0f
    private var mTextSizeSel = 0f

    private var mShowRows5 = SHOW_ROW_5  // Показать 5 строк
    private var mShowSec = true
    private var mShowTime = true

    private var pickerH: NumberPickerCustom
    private var pickerM: NumberPickerCustom
    private var pickerS: NumberPickerCustom

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.time_picker, this, true)

        pickerH = findViewById(R.id.pickerH)
        pickerM = findViewById(R.id.pickerM)
        pickerS = findViewById(R.id.pickerS)

        val a = context.obtainStyledAttributes(attrs, R.styleable.TimePickerCustom)

        mDividerHeight =
            a.getDimensionPixelSize(R.styleable.TimePickerCustom_tp_dividerHeight, DIVIDER_HEIGHT)
        mDividerColor = a.getColor(R.styleable.TimePickerCustom_tp_dividerColor, DIVIDER_COLOR)
        mDividerOffset = a.getDimensionPixelSize(R.styleable.TimePickerCustom_tp_dividerOffset, 0)

        val fading = a.getInt(R.styleable.TimePickerCustom_tp_fadingExtent, FADING_EXTENT)
        mFadingExtent = fading / 10f

        mFontFamily = a.getFont(R.styleable.TimePickerCustom_tp_fontFamily)
        mIntervalLongPress =
            a.getInt(R.styleable.TimePickerCustom_tp_intervalLongPress, INTERVAL_LONG_PRESS)
        mMaxHour = a.getInt(R.styleable.TimePickerCustom_tp_maxHours, MAX_VALUE_HOUR)

        mHintTextH = a.getString(R.styleable.TimePickerCustom_tp_hintTextHour)
        mHintTextM = a.getString(R.styleable.TimePickerCustom_tp_hintTextMin)
        mHintTextS = a.getString(R.styleable.TimePickerCustom_tp_hintTextSec)
        mHintSize =
            a.getDimensionPixelSize(R.styleable.TimePickerCustom_tp_hintTextSize, HINT_SIZE)
                .toFloat()
        mHintColor = a.getColor(R.styleable.TimePickerCustom_tp_hintTextColor, TEXT_COLOR)

        mTextSize =
            a.getDimensionPixelSize(R.styleable.TimePickerCustom_tp_textSize, TEXT_SIZE).toFloat()
        mTextSizeSel =
            a.getDimensionPixelSize(R.styleable.TimePickerCustom_tp_textSizeSel, TEXT_SIZE_SEL)
                .toFloat()
        mTextColor =
            a.getColor(R.styleable.TimePickerCustom_tp_textColor, TEXT_COLOR)
        mTextColorSel =
            a.getColor(R.styleable.TimePickerCustom_tp_textColorSel, TEXT_COLOR_SEL)

        mShowRows5 = a.getBoolean(R.styleable.TimePickerCustom_tp_showRows5, SHOW_ROW_5)
        mShowSec = a.getBoolean(R.styleable.TimePickerCustom_tp_showSec, true)
        mShowTime = a.getBoolean(R.styleable.TimePickerCustom_tp_showTime, true)

        a.recycle()

        initParams()
        initParamText()
        initListener()
        if (mShowTime)
            setTime()
    }

    private fun initParams() {

        var array = getArray(mMaxHour)
        pickerH.displayedValues = array
        array = getArray(59)
        pickerM.displayedValues = array
        pickerS.displayedValues = array

        pickerH.dividerColor = mDividerColor
        pickerM.dividerColor = mDividerColor
        pickerS.dividerColor = mDividerColor

        pickerH.dividerHeight = mDividerHeight
        pickerM.dividerHeight = mDividerHeight
        pickerS.dividerHeight = mDividerHeight

        pickerH.dividerOffset = mDividerOffset
        pickerM.dividerOffset = mDividerOffset
        pickerS.dividerOffset = mDividerOffset

        pickerH.fadingExtent = mFadingExtent
        pickerM.fadingExtent = mFadingExtent
        pickerS.fadingExtent = mFadingExtent

        pickerH.intervalLongPress = mIntervalLongPress
        pickerM.intervalLongPress = mIntervalLongPress
        pickerS.intervalLongPress = mIntervalLongPress

        if (mShowRows5) {
            pickerH.showRows5 = true
            pickerM.showRows5 = true
            pickerS.showRows5 = true
            mShownCount = 5
        } else {
            pickerH.showRows5 = false
            pickerM.showRows5 = false
            pickerS.showRows5 = false
            mShownCount = 3
        }
        // Показать Сек.
        if (!mShowSec) {
            mViewCount = 2
            pickerS.isVisible = false
        }
    }

    private fun initParamText() {
        // Подсказка
        if (mHintTextH != null) {
            pickerH.hintText = mHintTextH.toString()
            setHintHeight()
        }
        if (mHintTextM != null) {
            pickerM.hintText = mHintTextM.toString()
            setHintHeight()
        }
        if (mHintTextS != null) {
            pickerS.hintText = mHintTextS.toString()
            setHintHeight()
        }

        pickerH.hintColor = mHintColor
        pickerM.hintColor = mHintColor
        pickerS.hintColor = mHintColor

        pickerH.hintSize = mHintSize
        pickerM.hintSize = mHintSize
        pickerS.hintSize = mHintSize

        // Текст
        pickerH.textColor = mTextColor
        pickerH.textColorSel = mTextColorSel
        pickerH.textSize = mTextSize
        pickerH.textSizeSel = mTextSizeSel
        pickerH.fontFamily = mFontFamily

        pickerM.textColor = mTextColor
        pickerM.textColorSel = mTextColorSel
        pickerM.textSize = mTextSize
        pickerM.textSizeSel = mTextSizeSel
        pickerM.fontFamily = mFontFamily

        pickerS.textColor = mTextColor
        pickerS.textColorSel = mTextColorSel
        pickerS.textSize = mTextSize
        pickerS.textSizeSel = mTextSizeSel
        pickerS.fontFamily = mFontFamily
    }

    // Получить массив строк
    private fun getArray(max: Int): Array<String> {
        val arrayInt = (0..max).toList().toIntArray()
        val arrayStr = arrayInt.map { it.toString() }.toTypedArray()

        val n: Int = if (max < 9) max
        else 9

        for (i in 0..n) {
            arrayStr[i] = "0$i"
        }
        return arrayStr
    }

    // Установить время
    private fun setTime() {
        val calendar = Calendar.getInstance()
        pickerH.value = calendar[Calendar.HOUR_OF_DAY]
        pickerM.value = calendar[Calendar.MINUTE]
        pickerS.value = calendar[Calendar.SECOND]
    }

    // Установить высоту подсказки
    private fun setHintHeight() {
        mHintHeight = mHintSize + HINT_OFFSET
    }

    // Слушатели
    private fun initListener() {
        pickerH.setOnChangeListener {
            valueH = it
            if (mListener != null)
                mListener?.invoke(valueH, valueM, valueS)
        }
        pickerM.setOnChangeListener {
            valueM = it
            if (mListener != null)
                mListener?.invoke(valueH, valueM, valueS)
        }
        pickerS.setOnChangeListener {
            valueS = it
            if (mListener != null)
                mListener?.invoke(valueH, valueM, valueS)
        }
    }

    var hour: Int
        get() = pickerH.value
        set(value) {
            valueH = value
            pickerH.value = value
        }

    var min: Int
        get() = pickerM.value
        set(value) {
            valueM = value
            pickerM.value = value
        }

    var sec: Int
        get() = pickerS.value
        set(value) {
            valueS = value
            pickerS.value = value
        }

    var scrollHour: Int = 0
        set(value) {
            field = value
            pickerH.scroll = value
        }

    var scrollMin: Int = 0
        set(value) {
            field = value
            pickerM.scroll = value
        }

    var scrollSec: Int = 0
        set(value) {
            field = value
            pickerS.scroll = value
        }

    // Слушатель изменения значения
    fun setOnChangeListener(listener: OnChangeListener) {
        mListener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val wC = (mViewWidth + (mOffDividerX * 2)) * mViewCount
        val hC = mItemHeight * mShownCount + mHintHeight.toInt()

        setMeasuredDimension(
            resolveSize(wC, widthMeasureSpec),
            resolveSize(hC, heightMeasureSpec)
        )


    }

}