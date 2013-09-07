package com.jameskbride.criminalIntent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class CrimeFragment extends Fragment {

    public static final String EXTRA_CRIME_ID = "com.jameskbride.criminalIntent.crime_id";

    private Crime crime;
    private EditText titleField;
    private Button dateButton;
    private CheckBox solvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        crime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, parent, false);

        wireTitleField(view);
        wireDateButton(view);
        wireSolvedCheckBox(view);

        return  view;
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void wireSolvedCheckBox(View view) {
        solvedCheckBox = (CheckBox)view.findViewById(R.id.crime_solved);
        solvedCheckBox.setChecked(crime.isSolved());
        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });
    }

    private void wireDateButton(View view) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        dateButton = (Button)view.findViewById(R.id.crime_date);
        dateButton.setText(dateFormatter.format(crime.getDiscoveredOn()));
        dateButton.setEnabled(false);
    }

    private void wireTitleField(View view) {
        titleField = (EditText)view.findViewById(R.id.crime_title);
        titleField.setText(crime.getTitle());
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }
}
