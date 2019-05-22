package com.lambdaschool.healthchaser;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user

        String time = String.format(Locale.getDefault(), "%d:%d", hourOfDay, minute);

        this.onCompleteListener.onComplete(time);
    }

    /**
     * Interface
     * @see  <a href="https://stackoverflow.com/questions/15121373/returning-string-from-dialog-fragment-back-to-activity/15121529#15121529">Stack Overflow</a>
     */
    public interface OnCompleteListener {
        void onComplete(String time);
    }

    private OnCompleteListener onCompleteListener;

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        try {
            this.onCompleteListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
}