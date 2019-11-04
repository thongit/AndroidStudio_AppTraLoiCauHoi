package com.huuthongit.doantraloicauhoi;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    public static final String BASE_URL = "http://10.0.2.2:8000/";
    static String convertToString (InputStream stream)
    {
        BufferedReader reader  = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line;

        try{
            while ((line  = reader.readLine())!=null)
            {
                builder.append(line);
                builder.append("\n");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            if(reader != null)
            {
                try{
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        if(builder.length()==0)
        {
            return null;
        }
        return builder.toString();
    }

    static String getJSONData(String uri, String method)
    {
        HttpURLConnection urlConnection = null;
        String jsonString = null;
        Uri builtURI = Uri.parse(BASE_URL + uri).buildUpon().build();

        try {
            URL requestURL  = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection)requestURL.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            jsonString = convertToString(inputStream);
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }
        return jsonString;
    }

    static String getJSONData(String uri, String method,Object[] nameParams, Object[] valueParams)
    {
        HttpURLConnection urlConnection = null;
        String jsonString = null;
        Uri.Builder builder = Uri.parse(BASE_URL +uri).buildUpon();
        for (int i=0; i<nameParams.length;i++)
        {
            builder.appendQueryParameter(nameParams[i].toString(),valueParams[i].toString());
        }
        Uri builtURI = builder.build();

        try {
            URL requestURL  = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection)requestURL.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            jsonString = convertToString(inputStream);
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }
        return jsonString;
    }
}