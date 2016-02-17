package com.robosoft.archana.recyclerviewassignment.DataBaseHelper;

import android.content.Context;
import android.os.AsyncTask;

import com.robosoft.archana.recyclerviewassignment.Modal.Notification;
import com.robosoft.archana.recyclerviewassignment.Modal.ProductList;
import com.robosoft.archana.recyclerviewassignment.adapter.DatabaseAdapter;

import java.util.ArrayList;

/**
 * Created by archana on 17/2/16.
 */
public class DataFetchAsynTask extends AsyncTask<Void,Void,ArrayList<ProductList>>{

    ArrayList<ProductList> mDataBaseList = new ArrayList<>();
    private Context mContext;
    private DatabaseAdapter mDatabaseAdapter;
    Notification notification;

    public DataFetchAsynTask(Context mContext,DatabaseAdapter databaseAdapter) {
        this.mContext = mContext;
        this.mDatabaseAdapter = databaseAdapter;
        this.notification = (Notification) mContext;
    }

    @Override
    protected ArrayList<ProductList> doInBackground(Void... params) {
        mDataBaseList = mDatabaseAdapter.getAllData();
        return mDataBaseList;
    }

    @Override
    protected void onPostExecute(ArrayList<ProductList> arrayList) {
        super.onPostExecute(arrayList);
        notification.sendData(arrayList);
    }
}
