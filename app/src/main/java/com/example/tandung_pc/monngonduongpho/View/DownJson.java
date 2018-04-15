package com.example.tandung_pc.monngonduongpho.View;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DownJson extends AsyncTask<String,Integer,String> {
    String duongDan;
    Context context;

    public DownJson() {
    }

    public DownJson( String duongDan, Context context) {
        this.duongDan = duongDan;
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder content = new StringBuilder();

        try {
            // create a url object
            URL url = new URL(duongDan);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
