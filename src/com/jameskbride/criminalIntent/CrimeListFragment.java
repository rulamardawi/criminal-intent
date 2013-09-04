package com.jameskbride.criminalIntent;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.util.List;

public class CrimeListFragment extends ListFragment {

    private List<Crime> crimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);
        crimes = CrimeLab.getInstance(getActivity()).getCrimes();
    }
}
