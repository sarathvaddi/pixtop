package com.example.vaddisa.pixtop;

import java.util.ArrayList;

/**
 * Created by vaddisa on 8/20/2017.
 */
public interface Result {

    public void getResult(ArrayList<PictureDetails> results);

    public void onError(String serverResponse);
}
