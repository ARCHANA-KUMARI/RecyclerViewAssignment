package com.robosoft.archana.recyclerviewassignment;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;

import com.robosoft.archana.recyclerviewassignment.DataBaseHelper.DataFetchAsynTask;
import com.robosoft.archana.recyclerviewassignment.Modal.Communicator;
import com.robosoft.archana.recyclerviewassignment.Modal.EditFragmentCommunicator;
import com.robosoft.archana.recyclerviewassignment.Modal.AdapterViewFragmentCommunicator;
import com.robosoft.archana.recyclerviewassignment.Modal.NameComparator;
import com.robosoft.archana.recyclerviewassignment.Modal.Notification;
import com.robosoft.archana.recyclerviewassignment.Modal.ProductList;
import com.robosoft.archana.recyclerviewassignment.Network.ParserinInBackground;
import com.robosoft.archana.recyclerviewassignment.adapter.DatabaseAdapter;
import com.robosoft.archana.recyclerviewassignment.adapter.ProductListAdapter;
import com.robosoft.archana.recyclerviewassignment.fragment.ProductFragment;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements Notification, Communicator, AdapterViewFragmentCommunicator, EditFragmentCommunicator {

    private ArrayList<ProductList> mProductArrayList;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mProductListAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    // Use 1/8th of the available memory for this memory cache.
    final int cacheSize = maxMemory / 8;
    private LruCache<String, Bitmap> mLrucCach = new LruCache<>(cacheSize);
    private String MyPREFERENCES = "mypreference";
    private static final String FIRSTTIME = "FirtstTime";
    private SharedPreferences mSharedPreference;
    private boolean mFirstTime;
    private DatabaseAdapter mDatabaseAdapter;
  //  private SQLiteDatabase mSQlitedatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseAdapter = new DatabaseAdapter(this);
       // mSQlitedatabase = mDatabaseAdapter.databaseHelper.getReadableDatabase();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mSharedPreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mFirstTime = mSharedPreference.getBoolean(FIRSTTIME,true);
        if(mFirstTime){

            ParserinInBackground parserinInBackground = new ParserinInBackground(this);
            parserinInBackground.execute();
         }

            SharedPreferences.Editor editor = mSharedPreference.edit();
            mFirstTime = false;
            editor.putBoolean(FIRSTTIME,mFirstTime);
            editor.commit();

        DataFetchAsynTask dataFetchAsynTask = new DataFetchAsynTask(this,mDatabaseAdapter);
        dataFetchAsynTask.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addmenu) {
            ProductFragment addProductFragment = new ProductFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, addProductFragment);
            fragmentTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendData(ArrayList<ProductList> productLists) {
        mProductArrayList = productLists;
        mProductListAdapter = new ProductListAdapter(mLrucCach, this, mProductArrayList);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mProductListAdapter);

    }

    @Override
    public void onClickOfAddButton(ArrayList<ProductList> arrayList) {
       Log.i("Hello","List size is"+arrayList.size());
        if (arrayList.size() != 0) {
            mProductArrayList.addAll(arrayList);
        }
        Collections.sort(mProductArrayList, new NameComparator());
        mRecyclerView.setAdapter(mProductListAdapter);
        mProductListAdapter.notifyDataSetChanged();
    }

    //for edit
    @Override
    public void onClickOfUpdateViewOfAdapter(Fragment fragment, int position, ArrayList<ProductList> editablelist) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt("Position", position);
        if (mProductArrayList.size() != 0) {
            bundle.putSerializable("List", mProductArrayList);
        }
        bundle.putSerializable("EditableList", editablelist);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment).commit();
    }

    @Override
    public void onClickOfEditButton(int position) {
        Collections.sort(mProductArrayList, new NameComparator());
        mRecyclerView.setAdapter(mProductListAdapter);
        mProductListAdapter.notifyItemChanged(position);

    }
}
