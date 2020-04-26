package com.lambdaschool.healthchaser.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.lambdaschool.healthchaser.R;

import java.util.Locale;

public class NumberPickerFragment extends DialogFragment {

    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";
    public static final String ARG_PARAM3 = "param3";

    private String mParam1;
    private int mParam2;
    private int mParam3;

    private OnNumberPickerFragmentInteractionListener mListener;

    public NumberPickerFragment() {
        // Required empty public constructor
    }

    public static NumberPickerFragment newInstance(String param1, String param2, String param3) {
        NumberPickerFragment fragment = new NumberPickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final NumberPickerFragment numberPickerFragment = this;

        View rootView = inflater.inflate(R.layout.fragment_number_picker, container, false);
        final TextView textView = rootView.findViewById(R.id.number_picker_fragment_text_view_description);
        final NumberPicker numberPicker = rootView.findViewById(R.id.number_picker_fragment_number_picker);
        numberPicker.setMinValue(mParam2);
        numberPicker.setMaxValue(mParam3);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textView.setText(String.format(Locale.getDefault(), "%d %s", newVal, mParam1));
            }
        });

        Button buttonCancel = rootView.findViewById(R.id.number_picker_fragment_button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPickerFragment.dismiss();
            }
        });

        Button buttonOkay = rootView.findViewById(R.id.number_picker_fragment_button_okay);
        buttonOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOkayPressed(numberPicker.getValue());
                numberPickerFragment.dismiss();
            }
        });

        return rootView;
    }

    public void onOkayPressed(int numberPickerSelection) {
        if (mListener != null) {
            mListener.onNumberPickerFragmentInteraction(numberPickerSelection);
        }
    }

    public interface OnNumberPickerFragmentInteractionListener {
        void onNumberPickerFragmentInteraction(int numberPickerSelection);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNumberPickerFragmentInteractionListener) {
            mListener = (OnNumberPickerFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnSeekBarFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
