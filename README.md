## Fully customizable TimePicker.

Composite custom view of three NumberPickerCustom.

![TimePicker](https://github.com/user-attachments/assets/7531e303-519a-448e-8ce0-c4c336270f3c)
![TimePicker2](https://github.com/user-attachments/assets/c69cef8d-d2da-43ee-8bdf-994b93745583)

https://github.com/UraTera/Custom-NumberPicker

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
picker.setOnChangeListener { hour, min, sec ->
    myHour = hour
    myMin = min
    mySec = sec
}
```

Java

```
picker.setOnChangeListener((integer, integer2, integer3) -> {
    myHour = integer;
    myMin = integer2;
    mySec = integer3;
    return null;
});
```

Methods:
```
setHour, getHour, setMin, getMin, setSec, getSec, setScrollHour, setScrollMin, setScrollSec
```

