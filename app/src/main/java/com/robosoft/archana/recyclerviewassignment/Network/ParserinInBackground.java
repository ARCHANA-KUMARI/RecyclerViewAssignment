package com.robosoft.archana.recyclerviewassignment.Network;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.robosoft.archana.recyclerviewassignment.Modal.JsonParser;
import com.robosoft.archana.recyclerviewassignment.Modal.JsonParserUsingGson;
import com.robosoft.archana.recyclerviewassignment.Modal.Message;
import com.robosoft.archana.recyclerviewassignment.Modal.Notification;
import com.robosoft.archana.recyclerviewassignment.Modal.ProductList;
import com.robosoft.archana.recyclerviewassignment.adapter.DatabaseAdapter;

import org.json.JSONException;

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
    private ProductList mProductList;
    DatabaseAdapter databaseAdapter;
    long id;
    public ParserinInBackground(Context mContext) {
        this.mContext = mContext;
        this.notification = (Notification) mContext;
        databaseAdapter = new DatabaseAdapter(mContext);
    }

   //JsonParser jsonParser = new JsonParser(mContext);
      JsonParserUsingGson jsonParserUsingGson = new JsonParserUsingGson(mContext);
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
          //  produCArrayList = jsonParser.parseJson(json);
            produCArrayList = jsonParserUsingGson.parseJsonUsingGson(json);
            for(int i = 0;i<produCArrayList.size();i++){
                mProductList = produCArrayList.get(i);

               id = databaseAdapter.insertData(mProductList.getmName(),mProductList.getmCost(),mProductList.getmImage(),mProductList.getmDesription());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return produCArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<ProductList> productLists) {
        super.onPostExecute(productLists);
        if(id>0){
            Message.message(mContext,"Data is inserted successfully");
            Log.i("Hello", "No of rows is" + id);
        }
        notification.sendData(productLists);
    }
}
