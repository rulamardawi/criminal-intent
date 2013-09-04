package com.jameskbride.criminalIntent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab instance;
    private Context context;
    private List<Crime> crimes;

    private CrimeLab(Context appContext) {
        this.context = appContext;
        this.crimes = new ArrayList<Crime>();

        populateCrimes();
    }

    private void populateCrimes() {
        for (int i=0; i<100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime # " + i);
            crime.setSolved(i % 2 ==0);
            crimes.add(crime);
        }
    }

    public static CrimeLab getInstance(Context appContext) {
        if (instance == null) {
            instance = new CrimeLab(appContext.getApplicationContext());
        }

        return instance;
    }

    public List<Crime> getCrimes() {
        return crimes;
    }


    public Crime getCrime(UUID id) {
        for(Crime crime : crimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }

        return null;
    }
}
