package com.madhur.todolist;

// Create a new DateHelper class in a file named DateHelper.java

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateHelper {

    private final Context context;
    private final EditText editText;

    public DateHelper(Context context, EditText editText) {
        this.context = context;
        this.editText = editText;
    }

    public void showDatePicker() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle selected date
                        String formattedDate = formatDate(year, month, dayOfMonth);
                        // Append the selected date to your EditText
                        editText.append(" " + formattedDate);
                    }
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }

    private String formatDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}
