### Fully customizable TimePicker.

Composite custom view of three NumberPickerCustom.

![TimePicker](https://github.com/user-attachments/assets/4c090a53-014b-4b5d-920d-0e79ed83263a)
![TimePicker2](https://github.com/user-attachments/assets/6d8822c1-b629-4727-8889-fd1bec3ac81d)

To use the ready-made library, add the dependency:
```
dependencies {

    implementation("io.github.uratera:time_picker:1.0.0")
}
```
### Attributes
|Attributes          |Description     |Default value|
|--------------------|----------------|-------------|
|tp_dividerColor     |Divider color   |black
|tp_dividerHeight    |Divider height|2dp
|tp_dividerOffset    |Divider offset|0dp
|tp_fadingExtent     |Edges fading extent (0-10)|7
|tp_fontFamily       |Text font      |default
|tp_hintTextHour     |Text hint hours   |nothing
|tp_hintTextMin      |Text hint minutes |nothing
|tp_hintTextSec      |Text hint seconds |nothing
|tp_hintTextColor    |Color text hint |black
|tp_hintTextSize     |Size text hint |16sp
|tp_intervalLongPress|Interval update of long press |300
|tp_maxHours         |Maximum value hours|100
|tp_textColor        |Color unselected text |gray
|tp_textColorSel     |Color selected text |black
|tp_textSize         |Size unselected text |20sp
|tp_textSizeSel      |Size selected text|24sp
|tp_showRows5        |Show 5 rows |false
|tp_showSec          |Show seconds picker |true
|tp_showTime         |Show time | true

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


