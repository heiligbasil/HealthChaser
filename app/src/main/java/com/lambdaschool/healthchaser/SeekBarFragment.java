package com.lambdaschool.healthchaser;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.HashMap;


public class SeekBarFragment extends DialogFragment {

    static final String ARG_PARAM1 = "param1";
    static final String ARG_PARAM2 = "param2";

    private HashMap<Integer, String> mParam1;
    private String mParam2;

    private OnSeekBarFragmentInteractionListener mListener;

    public SeekBarFragment() {
        // Required empty public constructor
    }

    public static SeekBarFragment newInstance(String param1, String param2) {
        SeekBarFragment fragment = new SeekBarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = (HashMap<Integer, String>) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final SeekBarFragment seekBarFragment = this;

        final View rootView = inflater.inflate(R.layout.fragment_seek_bar, container, false);
        final TextView textView = rootView.findViewById(R.id.seek_bar_fragment_text_view_description);
        final SeekBar seekBar = rootView.findViewById(R.id.seek_bar_fragment_seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String translation = mParam1.get(progress);

                if (translation == null)
                    translation = "indeterminate";

                textView.setText(String.format("%s%s", mParam2, translation));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(1);
        seekBar.setProgress(0);

        Button buttonCancel = rootView.findViewById(R.id.seek_bar_fragment_button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBarFragment.dismiss();
            }
        });

        Button buttonOkay = rootView.findViewById(R.id.seek_bar_fragment_button_okay);
        buttonOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOkayPressed(seekBar.getProgress());
                seekBarFragment.dismiss();
            }
        });

        return rootView;
    }

    public void onOkayPressed(int seekBarSelection) {
        if (mListener != null) {
            mListener.onSeekBarFragmentInteraction(seekBarSelection);
        }
    }

    public interface OnSeekBarFragmentInteractionListener {
        void onSeekBarFragmentInteraction(int seekBarSelection);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSeekBarFragmentInteractionListener) {
            mListener = (OnSeekBarFragmentInteractionListener) context;
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
