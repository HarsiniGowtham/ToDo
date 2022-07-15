package com.gloify.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;


public class ChooserDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    String date, time;
    Button dateButton, timeButton, submit;
    EditText message;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_fragment, container, false);
        dateButton = (Button) view.findViewById(R.id.dateBtn);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        ChooserDialogFragment.this, year, month, day);
                datePickerDialog.show();
            }
        });

        timeButton = (Button) view.findViewById(R.id.timeBtn);
        timeButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR);
                minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        ChooserDialogFragment.this, hour, minute, DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        message = (EditText) view.findViewById(R.id.message);

        submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProfileActivity) getActivity()).insertData(date, time, message.getText().toString());
                dismiss();
            }
        });


        return view;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        date = day + "/" + month + "/" + year;
        dateButton.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        time = hour + ":" + minute;
        timeButton.setText(time);
    }
}
