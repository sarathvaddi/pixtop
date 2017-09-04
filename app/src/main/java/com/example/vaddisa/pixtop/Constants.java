package com.example.vaddisa.pixtop;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class Constants {


    public String tabTitles[] = new String[]{"All", "My Album", "Saved"};

    public static final String LOCAL_API_URL = "10.0.0.80:8080/photo_gallery/photos";
    public static final String API_STRING = "?key=";
    public static final String QUERY_STRING = "&response_group=high_resolution&q=";
    public static final String QUERY = "yosemite";
    public static final String API_KEY = "6089935-fe0f82301764e844a53159975";
    public static final String BASE_URL = "https://pixabay.com/api/";
    public static final String PIXABAY_HIGH_RES_API = BASE_URL + API_STRING + API_KEY + QUERY_STRING ;
    public static final String TAB_SELECTED_TEXT = "TabSelected";


}
