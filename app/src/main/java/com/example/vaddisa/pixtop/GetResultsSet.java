package com.example.vaddisa.pixtop;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class GetResultsSet  implements Parcelable {

    private List<PictureDetails> results;

    protected GetResultsSet(Parcel in) {
        results = in.createTypedArrayList(PictureDetails.CREATOR);
    }

    public static final Creator<GetResultsSet> CREATOR = new Creator<GetResultsSet>() {
        @Override
        public GetResultsSet createFromParcel(Parcel in) {
            return new GetResultsSet(in);
        }

        @Override
        public GetResultsSet[] newArray(int size) {
            return new GetResultsSet[size];
        }
    };

    public GetResultsSet() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }

    public List<PictureDetails> getResults() {
        return results;
    }

    public void setResults(List<PictureDetails> results) {
        this.results = results;
    }
}

