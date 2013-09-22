package com.jameskbride.criminalIntent;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class CriminalIntentSerializer {

    private Context context;
    private String fileName;

    public CriminalIntentSerializer(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public void saveCrimes(List<Crime> crimes) throws IOException, JSONException {
        JSONArray jsonArray = convertCrimesToJSON(crimes);
        writeCrimesToFile(jsonArray);
    }

    private void writeCrimesToFile(JSONArray jsonArray) throws IOException {
        Writer writer = null;
        try {
            OutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(jsonArray.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private JSONArray convertCrimesToJSON(List<Crime> crimes) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (Crime crime : crimes) {
            jsonArray.put(crime.toJSON());
        }
        return jsonArray;
    }

}
