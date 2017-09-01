package com.example.vaddisa.pixtop.ConnectivityManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.vaddisa.pixtop.BasePresenter;
import com.example.vaddisa.pixtop.Result;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class ConnectionManager extends AsyncTask<String, Void, String> {


    private ProgressDialog progressDialog;
    String server_response;
    ArrayList result = new ArrayList();
    BasePresenter basePresenter;
    Context ctx;
    boolean isLocalApi;
    private Result resultInterface;

    public ConnectionManager(BasePresenter basePresenter, Context ctx, boolean isLocalApi) {
        this.basePresenter = basePresenter;
        this.ctx = ctx;
        this.isLocalApi = isLocalApi;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx, "", "Fetching Data...");
    }

    @Override
    protected String doInBackground(String... strings) {

        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();


            server_response = readStream(urlConnection.getInputStream());
            Log.v("CatalogClient", server_response);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return server_response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.e("Response", "" + server_response);

        JsonParser jsonParser = new JsonParser();
        try {
//            basePresenter.setObject(JsonParser.deserializeObject(o, server_response));
            if (!isLocalApi)
                result = (ArrayList) jsonParser.getPixabayApiResults(s);
            else
                result = (ArrayList) jsonParser.getLocalApiResults(s);
        } catch (JSONException e) {
            resultInterface.onError("ERROR");
        }
        basePresenter.setResult(result);
        if (resultInterface != null)
            resultInterface.getResult(result);

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    public Result getResultInterface() {
        return resultInterface;
    }

    public void setResultInterface(Result resultInterface) {
        this.resultInterface = resultInterface;
    }
}
