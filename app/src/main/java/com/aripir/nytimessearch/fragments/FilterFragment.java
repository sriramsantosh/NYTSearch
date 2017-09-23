package com.aripir.nytimessearch.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.aripir.nytimessearch.R;

import com.aripir.nytimessearch.models.FilterPreferences;
import com.aripir.nytimessearch.models.UserPreferencesDBHelper;

import java.util.Calendar;

/**
 * Created by saripirala on 9/19/17.
 */

public class FilterFragment extends DialogFragment {

    TextView beginDateTV;
    TextView datePickTV;
    Calendar mCurrentDate;
    int day, month, year;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button saveBtn;
    CheckBox checkBoxSports;
    CheckBox checkBoxFashion;
    CheckBox checkBoxArts;
    private OnCompleteListener mListener;
    private UserPreferencesDBHelper userPreferencesDBHelper;


    Spinner timeFrameSpinner;

    public FilterFragment(){

    }

    public static FilterFragment newInstance() {
        FilterFragment frag = new FilterFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        userPreferencesDBHelper = UserPreferencesDBHelper.getInstance(getContext());

        datePickTV =(TextView) view.findViewById(R.id.datePickerTV);
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        checkBoxArts = (CheckBox) view.findViewById(R.id.artsCB);
        checkBoxFashion = (CheckBox) view.findViewById(R.id.fashionCB);
        checkBoxSports = (CheckBox) view.findViewById(R.id.sportsSB);
        timeFrameSpinner = (Spinner)  view.findViewById(R.id.timeFrameSP);

        mCurrentDate = Calendar.getInstance();

        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        FilterPreferences filterPreferences = userPreferencesDBHelper.getUserPreferences();

       // datePickTV.setText(month+"/" + day + "/" + year);
        datePickTV.setText(filterPreferences.getBeginDate());
        if(filterPreferences.getSort().equalsIgnoreCase("oldest"))
        timeFrameSpinner.setSelection(1);

        if(!filterPreferences.isArts())
            checkBoxArts.setChecked(false);
        if(!filterPreferences.isFashion())
            checkBoxFashion.setChecked(false);
        if(!filterPreferences.isSports())
            checkBoxSports.setChecked(false);

        datePickTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.Theme_AppCompat_Light_DialogWhenLarge,mDateSetListener, year, month, day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                ++month;
                datePickTV.setText(month+"/" + day + "/" + year);
            }
        };
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle("Filter");
        // Show soft keyboard automatically and request focus to field
  //      mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferencesDBHelper.insertFilterPreferences(datePickTV.getText().toString(), timeFrameSpinner.getSelectedItem().toString(), checkBoxArts.isChecked(), checkBoxFashion.isChecked(), checkBoxSports.isChecked() );
                mListener.onComplete(datePickTV.getText().toString(), timeFrameSpinner.getSelectedItem().toString(), checkBoxArts.isChecked(), checkBoxFashion.isChecked(), checkBoxSports.isChecked() );
                getDialog().dismiss();
            }
        });
    }


    public static interface OnCompleteListener {
        public abstract void onComplete(String datePick, String timeFrame, boolean arts, boolean fashion, boolean sports );
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnCompleteListener) getActivity();
        }catch (final ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnCompleteListener");
        }
    }

}
