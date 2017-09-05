package com.example.vaddisa.pixtop.ConnectivityManager;

import android.util.Log;

import com.example.vaddisa.pixtop.GetResultsSet;
import com.example.vaddisa.pixtop.PictureDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarathreddyvaddhi on 10/2/16.
 */
public class JsonParser {
    private static final String TAG_hits = "hits";
    private static final String TAG_largeImageURL = "largeImageURL";
    private static final String TAG_fullHDURL = "fullHDURL";
    private static final String TAG_imageURL = "imageURL";
    private static final String TAG_previewURL = "previewURL";
    private static final String TAG_user = "user";
    private static final String TAG_backDropPath = "backdrop_path";
    private static final String TAG_name = "name";
    private static final String TAG_id = "id";
    private static final String TAG_id_hash = "id_hash";
    private static final String TAG = JsonParser.class.getSimpleName();


    public String largeImageURL;
    public String fullHDURL;
    public String id_hash;
    public String imageURL;
    public String previewURL;
    public String user;
    public String id;
    public String name;


    ArrayList<GetResultsSet> feeds = new ArrayList<GetResultsSet>();
    GetResultsSet resultObject;


    public List<PictureDetails> getPixabayApiResults(String response) throws JSONException {



        JSONArray results;
        if (response != null) {
            JSONObject res = new JSONObject(response);

            results = res.getJSONArray(TAG_hits);
            resultObject = new GetResultsSet();
            resultObject.setResults(setResults(results));

            Log.i("TAG", "" + resultObject.getResults().size());
            return setResults(results);
        }
        return null;
    }

    private List<PictureDetails> setResults(JSONArray results) {

        List<PictureDetails> list = new ArrayList<>();
        for (int j = 0; j < results.length(); j++) {
            list.add(convert(results, j));
        }
        return list;
    }

    private PictureDetails convert(JSONArray results, int i) {

        try {
            JSONObject r = results.getJSONObject(i);

            largeImageURL = r.getString(TAG_largeImageURL);
            fullHDURL = r.getString(TAG_fullHDURL);
            id_hash = r.getString(TAG_id_hash);
            imageURL = r.getString(TAG_imageURL);
            previewURL = r.getString(TAG_previewURL);
            user = r.getString(TAG_user);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        PictureDetails movieDetailsObject = new PictureDetails(largeImageURL, id_hash, fullHDURL,
                previewURL, user, imageURL);
        Log.d(TAG, " largeImageURL " + largeImageURL);
        return movieDetailsObject;
    }

    public List<PictureDetails> getLocalApiResults(String response) throws JSONException {

        Log.i("TAG", "Entered into parser");


        if (response != null) {
            JSONArray results;
            JSONObject res = new JSONObject(response);

            results = res.getJSONArray(TAG_hits);
            resultObject = new GetResultsSet();
            resultObject.setResults(setLocalResults(results));

            Log.i("TAG", "" + resultObject.getResults().size());
            return setResults(results);
        }
        return null;
    }

    private List<PictureDetails> setLocalResults(JSONArray results) {

        List<PictureDetails> list = new ArrayList<>();
        for (int j = 0; j < results.length(); j++) {
            list.add(convertLocal(results, j));
        }
        return list;
    }

    private PictureDetails convertLocal(JSONArray results, int i) {

        try {
            JSONObject r = results.getJSONObject(i);

            id = r.getString(TAG_id);
            user = r.getString(TAG_user);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        PictureDetails movieDetailsObject = new PictureDetails(id, user);
        Log.d(TAG, " LocalImageId " + id);
        return movieDetailsObject;
    }


}
