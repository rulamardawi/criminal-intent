package com.jameskbride.criminalIntent;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class CrimeListFragment extends ListFragment {

    private List<Crime> crimes;
    private static final String TAG = "CrimeListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);
        crimes = CrimeLab.getInstance(getActivity()).getCrimes();

        ArrayAdapter<Crime> crimeArrayAdapter = new ArrayAdapter<Crime>(getActivity(), android.R.layout.simple_list_item_1, crimes);
        setListAdapter(crimeArrayAdapter);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Crime crime = (Crime)(getListAdapter()).getItem(position);
        Log.d(TAG, crime.getTitle() + " was clicked");
    }
}
