package com.robosoft.archana.recyclerviewassignment.Network;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.robosoft.archana.recyclerviewassignment.Modal.JsonParser;
import com.robosoft.archana.recyclerviewassignment.Modal.Notification;
import com.robosoft.archana.recyclerviewassignment.Modal.ProductList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by archanakumari on 11/2/16.
 */
public class ParserinInBackground extends AsyncTask<Void, Void, ArrayList<ProductList>> {
    private Context mContext;
    private ArrayList<ProductList> produCArrayList = new ArrayList<>();
    Notification notification;

    public ParserinInBackground(Context mContext) {
        this.mContext = mContext;
        this.notification = (Notification) mContext;
    }

    JsonParser jsonParser = new JsonParser(mContext);

    @Override
    protected ArrayList doInBackground(Void... params) {

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
            produCArrayList = jsonParser.parseJson(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }


        return produCArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<ProductList> productLists) {
        super.onPostExecute(productLists);
        notification.sendData(productLists);
    }
}
