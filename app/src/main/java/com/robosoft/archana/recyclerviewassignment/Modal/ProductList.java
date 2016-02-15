package com.robosoft.archana.recyclerviewassignment.Modal;

import java.io.Serializable;

/**
 * Created by archanakumari on 11/2/16.
 */
public class ProductList implements Serializable {
    private String mName;
    private int mCost;
    private String mImage;

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

    private String mDesription;
}

