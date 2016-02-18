package com.robosoft.archana.recyclerviewassignment.DataBaseHelper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.robosoft.archana.recyclerviewassignment.Modal.Message;
import com.robosoft.archana.recyclerviewassignment.Modal.ProductList;
import com.robosoft.archana.recyclerviewassignment.adapter.DatabaseAdapter;

import java.util.ArrayList;

/**
 * Created by archana on 17/2/16.
 */
public class DataInsertAsynTask extends AsyncTask<Void,Void,Long> {

    private String mName,mImageurl,mDescription;
    private int mCost;
    DatabaseAdapter databaseAdapter;
    private Context mContext;
    private ArrayList<ProductList> mCurrentList = new ArrayList<>();

    public DataInsertAsynTask(Context context,String mName, String mImageurl, String mDescription, int mCost) {
        this.mContext = context;
        this.mName = mName;
        this.mImageurl = mImageurl;
        this.mDescription = mDescription;
        this.mCost = mCost;
        databaseAdapter = new DatabaseAdapter(mContext);

    }

    @Override
    protected Long doInBackground(Void... params) {
        long id = databaseAdapter.insertData(mName,mCost,mImageurl,mDescription);
        return  id;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        if (aLong.intValue() < 0) {
            Message.message(mContext, "Unsuccessfully");
        } else {
            Message.message(mContext, "Data is inserted successfully");
        }

    }
}
