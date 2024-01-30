package com.madhur.todolist;

// Create a new TimeHelper class in a file named TimeHelper.java

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeHelper {

    private final Context context;
    private final EditText editText;

    public TimeHelper(Context context, EditText editText) {
        this.context = context;
        this.editText = editText;
    }

    public void showTimePicker() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Handle selected time
                        String formattedTime = formatTime(hourOfDay, minute);
                        // Append the selected time to your EditText
                        editText.append(" " + formattedTime);
                    }
                },
                hourOfDay,
                minute,
                false  // Set to true if you want the 24-hour format
        );
        timePickerDialog.show();
    }

    private String formatTime(int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return timeFormat.format(calendar.getTime());
    }
}
