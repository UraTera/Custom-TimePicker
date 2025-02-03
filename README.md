## Fully customizable TimePicker.

Composite custom view of three NumberPickerCustom.

https://github.com/UraTera/Custom-NumberPicker/tree/main

![TimePicker](https://github.com/user-attachments/assets/82aad045-a652-45db-b2dd-0e464e4f0f9d)
![TimePicker2](https://github.com/user-attachments/assets/06d1f3b3-a787-453d-a31d-e7c6e3724041)

Open source. The libs folder contains the compiled TimePicker.aar library.

Dependencies:
```
implementation(files("libs/TimePicker.aar"))
```

### Attributes
|Attributes          |Description     |Default value|
|--------------------|----------------|-------------|
|np_dividerColor     |Divider color   |black
|np_dividerHeight    |Divider height|2dp
|np_dividerOffset    |Divider offset|0dp
|np_fadingExtent     |Edges fading extent (0-10)|7
|np_fontFamily       |Text font      |default
|np_hintTextHour     |Text hint hours   |nothing
|np_hintTextMin      |Text hint minutes |nothing
|np_hintTextSec      |Text hint seconds |nothing
|np_hintTextColor    |Color text hint |black
|np_hintTextSize     |Size text hint |16sp
|np_intervalLongPress|Interval update of long press |300
|np_maxHours         |Maximum value hours|100
|np_textColor        |Color unselected text |gray
|np_textColorSel     |Color selected text |black
|np_textSize         |Size unselected text |20sp
|np_textSizeSel      |Size selected text|24sp
|np_showRows5        |Show 5 rows |false
|np_showSec          |Show seconds picker |true
|np_showTime         |Show time | true

Values change listener.

Kotlin

```
picker.setOnChangeListener { picker, hour, min, sec ->
    mHour = hour
    mMin = min
    mSec = sec
}
```

Java

```
picker.setOnChangeListener((timePickerCustom, integer, integer2, integer3) -> {
    mHour = integer 
    mMin = integer2
    mSec = integer3 
    return null;
});
```


Methods:
```
setHour, getHour, setMin, getMin, setSec, getSec, setScrollHour, setScrollMin, setScrollSec
```
