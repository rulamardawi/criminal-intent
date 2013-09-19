package com.jameskbride.criminalIntent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class CrimeListFragment extends ListFragment {

    private List<Crime> crimes;
    private static final String TAG = "CrimeListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);
        crimes = CrimeLab.getInstance(getActivity()).getCrimes();

        CrimeAdapter crimeArrayAdapter = new CrimeAdapter(crimes);
        setListAdapter(crimeArrayAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Crime crime = ((CrimeAdapter)getListAdapter()).getItem(position);
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_crime_list, menu);
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(List<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = createView();
            }

            Crime crime = getItem(position);

            populateTitle(convertView, crime);
            populateDate(convertView, crime);
            populateSolved(convertView, crime);

            return convertView;
        }

        private void populateSolved(View convertView, Crime crime) {
            CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(crime.isSolved());
        }

        private void populateDate(View convertView, Crime crime) {
            TextView dateTextView = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
            SimpleDateFormat dateFormatter = getSimpleDateFormat();
            dateTextView.setText(dateFormatter.format(crime.getDiscoveredOn()));
        }

        private SimpleDateFormat getSimpleDateFormat() {
            return new SimpleDateFormat("MM/dd/yyyy");
        }

        private void populateTitle(View convertView, Crime crime) {
            TextView titleTextView = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(crime.getTitle());
        }

        private View createView() {
            return getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
        }
    }
}
