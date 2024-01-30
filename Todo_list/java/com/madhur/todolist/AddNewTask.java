package com.madhur.todolist;

import static com.madhur.todolist.Utils.NotificationHelper.showNotification;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.madhur.todolist.Model.ToDoModel;
import com.madhur.todolist.Utils.DataBaseHelper;
import com.madhur.todolist.Utils.NotificationHelper;

import java.util.Locale;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";

    //widgets
    private EditText mEditText;
    private Button mSaveButton;
    private Button mPickDateButton;
    private Button mPickTimeButton;
    private TextView mRemainingTimeTextView;

    private DataBaseHelper myDb;
    private DateHelper dateHelper;
    private TimeHelper timeHelper;
    private CountDownTimer countDownTimer;
    private long timeInMillis;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_newtask , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText = view.findViewById(R.id.edittext);
        mSaveButton = view.findViewById(R.id.button_save);
        mPickDateButton = view.findViewById(R.id.button_pick_date);
        mPickTimeButton = view.findViewById(R.id.button_pick_time);
        mRemainingTimeTextView = view.findViewById(R.id.textview_remaining_time);

        myDb = new DataBaseHelper(getActivity());
        dateHelper = new DateHelper(requireContext(), mEditText);
        timeHelper = new TimeHelper(requireContext(), mEditText);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSaveButton.setEnabled(!s.toString().isEmpty());
                mSaveButton.setBackgroundColor(mSaveButton.isEnabled() ?
                        getResources().getColor(R.color.colorPrimary) : Color.GRAY);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mPickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateHelper.showDatePicker();
            }
        });

        mPickTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeHelper.showTimePicker();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();


                ToDoModel item = new ToDoModel();
                item.setTask(text);
                item.setStatus(0);
                myDb.insertTask(item);
                timeInMillis = System.currentTimeMillis() + 24 * 60 * 60 * 1000; // 24 hours

                // Start the countdown timer
                startCountDownTimer();
                dismiss();
            }
        });
    }

    private void startCountDownTimer() {
        // Create a new CountDownTimer
        countDownTimer = new CountDownTimer(timeInMillis - System.currentTimeMillis(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the remaining time in the TextView
                long hours = millisUntilFinished / (60 * 60 * 1000);
                long minutes = (millisUntilFinished % (60 * 60 * 1000)) / (60 * 1000);
                long seconds = (millisUntilFinished % (60 * 1000)) / 1000;

                String remainingTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                mRemainingTimeTextView.setText("Remaining Time: " + remainingTime);
            }

            @Override
            public void onFinish() {
                // Handle when the countdown timer finishes (if needed)
                mRemainingTimeTextView.setText("Task Completed");
                showNotification();
            }
        };

        // Start the countdown timer
        countDownTimer.start();
    }

    private void showNotification() {
        String notificationTitle = "Task Reminder";
        String notificationText = "Your task is now due!";

        // Show the notification using the helper class
        NotificationHelper.showNotification(requireContext(), notificationTitle, notificationText);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }
    }
}
