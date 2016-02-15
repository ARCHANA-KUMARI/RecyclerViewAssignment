package com.robosoft.archana.recyclerviewassignment.Network;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by archanakumari on 11/2/16.
 */
public class ReaderFromAsset {

    private Context mContext;

    public ReaderFromAsset(Context mContext) {
        this.mContext = mContext;
    }

    public String loadJSONFromAsset() {

        String json = null;
        try {

            InputStream inputStream;
            AssetManager assetManager = mContext.getAssets();
            inputStream = assetManager.open("productlist.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
