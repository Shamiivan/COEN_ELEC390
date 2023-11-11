package com.example.coenelec390.db_manager;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopulateDatabase {
    private Context context;

    public PopulateDatabase(Context context) {
        this.context = context;
        Log.d("CSV","new word lessgoo");
    }

    public List<String[]> readCSV(String fileName) throws IOException {
        InputStream is = context.getAssets().open(fileName);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";
        List<String[]> rows = new ArrayList<>();
        br.readLine(); // This will skip the first line (headers)
        while ((line = br.readLine()) != null) {
            String[] row = parseCSVLine(line);
            rows.add(row);
        }
        return rows;
    }

    private String[] parseCSVLine(String line) {
        List<String> cells = new ArrayList<>();
        StringBuilder cellBuilder = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                cells.add(cellBuilder.toString().trim());
                cellBuilder.setLength(0);
            } else {
                cellBuilder.append(c);
            }
        }

        cells.add(cellBuilder.toString().trim());

        return cells.toArray(new String[0]);
    }

    public void readCSVAndPrint(String fileName) {
        try {
            List<String[]> rows = readCSV(fileName);
            for (String[] row : rows) {
                Log.d("CSV", Arrays.toString(row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
