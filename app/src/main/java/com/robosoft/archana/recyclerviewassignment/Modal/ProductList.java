package com.robosoft.archana.recyclerviewassignment.Modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by archanakumari on 11/2/16.
 */
public class ProductList implements Serializable {

    @SerializedName("name")
    private String mName;
    @SerializedName("cost")
    private int mCost;
    @SerializedName("image")
    private String mImage;
    @SerializedName("description")
    private String mDesription;
    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmCost() {
        return mCost;
    }

    public void setmCost(int mCost) {
        this.mCost = mCost;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmDesription() {
        return mDesription;
    }

    public void setmDesription(String mDesription) {
        this.mDesription = mDesription;
    }


}


