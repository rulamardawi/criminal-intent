package com.jameskbride.criminalIntent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class CrimeListFragment extends ListFragment {

    private List<Crime> crimes;
    private static final String TAG = "CrimeListFragment";
    private boolean subtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subtitleVisible = true;
        setRetainInstance(true);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);
        crimes = CrimeLab.getInstance(getActivity()).getCrimes();

        CrimeAdapter crimeArrayAdapter = new CrimeAdapter(crimes);
        setListAdapter(crimeArrayAdapter);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = super.onCreateView(layoutInflater, parent, savedInstanceState);

        if (AndroidVersionHelper.isHoneycombOrHigher() && subtitleVisible) {
            getActivity().getActionBar().setSubtitle(R.string.subtitle);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        startCrimePagerActivity(position);
    }

    private void startCrimePagerActivity(int position) {
        Crime crime = ((CrimeAdapter)getListAdapter()).getItem(position);
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_crime_list, menu);
        setMenuItemSubtitle(menu);
    }

    private void setMenuItemSubtitle(Menu menu) {
        MenuItem subtitleMenuItem = menu.findItem(R.id.meneu_item_show_subtitle);
        if (subtitleVisible && subtitleMenuItem != null) {
            subtitleMenuItem.setTitle(R.string.subtitle);
        }
    }

    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.getInstance(getActivity()).addCrime(crime);

                Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
                intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
                startActivityForResult(intent, 0);

                return true;
            case R.id.meneu_item_show_subtitle:
                if (getActivity().getActionBar().getSubtitle() == null) {
                    subtitleVisible = true;
                    getActivity().getActionBar().setSubtitle(R.string.subtitle);
                    menuItem.setTitle(R.string.hide_subtitle);

                } else {
                    subtitleVisible = false;
                    getActivity().getActionBar().setSubtitle(null);
                    menuItem.setTitle(R.string.show_subtitle);
                }
            default:
                return super.onOptionsItemSelected(menuItem);
        }
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
