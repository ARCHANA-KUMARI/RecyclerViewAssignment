package com.robosoft.archana.recyclerviewassignment.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;

import com.robosoft.archana.recyclerviewassignment.Modal.Message;
import com.robosoft.archana.recyclerviewassignment.Modal.ProductList;

import java.util.ArrayList;

/**
 * Created by archana on 16/2/16.
 */
public class DatabaseAdapter {

    private Context mContext;
    public DatabaseHelper databaseHelper;
    private Cursor mCursor;
    private ProductList mProductList;
    private ArrayList<ProductList> mList = new ArrayList<>();
    public DatabaseAdapter(Context mContext) {
        this.mContext = mContext;
        databaseHelper = new DatabaseHelper(mContext);
    }
    public long  insertData(String name ,int cost,String url,String description){
        SQLiteDatabase sqLiteDatabase  = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.PRODUCT_NAME,name);
        contentValues.put(DatabaseHelper.PRODUCT_COST,cost);
        contentValues.put(DatabaseHelper.PRODUCT_URL,url);
        contentValues.put(DatabaseHelper.PRODUCT_DESCRIPTION, description);
        long id = sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
        return id;
    }

    public ArrayList<ProductList> getAllData(){



        String colums[] = {DatabaseHelper.PRODUCT_ID,DatabaseHelper.PRODUCT_NAME,DatabaseHelper.PRODUCT_COST,DatabaseHelper.PRODUCT_URL,DatabaseHelper.PRODUCT_DESCRIPTION};
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        mCursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME,colums,null,null,null,null,null);
        if(mCursor.moveToFirst()){
            while(mCursor.moveToNext()){
                mProductList = new ProductList();
                int id = mCursor.getInt(0);

                String name = mCursor.getString(1);
                mProductList.setmName(name);
                Log.i("Hello", " name is" + name);
                int cost = mCursor.getInt(2);
                mProductList.setmCost(cost);
                Log.i("Hello","Cost is"+cost);
                String url = mCursor.getString(3);
                mProductList.setmImage(url);
                Log.i("Hello","Url is"+url);

                String description = mCursor.getString(4);
                mProductList.setmDesription(description);
                mList.add(mProductList);
                Log.i("Hello", "Description is" + description);


            }
        }
       sqLiteDatabase.close();
        return  mList;
    }
    public int deleteRow(int id,SQLiteDatabase sqLiteDatabase){

        //Delete * from TABLE_NAME where NAME = "archana"
        // SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        //   int id = dataProvider.getmId();
        //  String whereArgs = ((Strubg) id);
        //String whereArgs[] = {"archana"};

        int noOfRows = sqLiteDatabase.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.PRODUCT_ID + "="+id,null);

        return noOfRows;


    }
    public int updateName(String oldProductName,String newProductName,int oldProductCost,int newProductCost,String oldProductUrl,String newProductUrl,String oldProductDescription,String newProductDescription){

        //Update TABLE_NAME set NAME = 'SINGH' where NAME = 'ARCHANA'
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.PRODUCT_NAME,newProductName);
        contentValues.put(DatabaseHelper.PRODUCT_COST,newProductCost);
        contentValues.put(DatabaseHelper.PRODUCT_URL,newProductUrl);
        contentValues.put(DatabaseHelper.PRODUCT_DESCRIPTION,newProductDescription);
        String whereArgs[] = {oldProductName};
        int noOfRows = sqLiteDatabase.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.PRODUCT_NAME+ " =? ", whereArgs);
        return noOfRows;
    }
    public Cursor  getCursor(SQLiteDatabase sqLiteDatabase){

        //StringBuffer stringBuffer = new StringBuffer();
        //Select UID,NAME and PASSWORD
        String columns[] = {DatabaseHelper.PRODUCT_ID,DatabaseHelper.PRODUCT_NAME,DatabaseHelper.PRODUCT_COST,DatabaseHelper.PRODUCT_URL,DatabaseHelper.PRODUCT_DESCRIPTION};
        //   SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor =  sqLiteDatabase.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);

        return  cursor;
    }
    static public class DatabaseHelper extends SQLiteOpenHelper {
        private Context mContext;
        private static final String DATABASE_NAME = "Productabase";
        private static final String TABLE_NAME = "Product";
        private static final int DATABASE_VERSION = 1;
        private static final String PRODUCT_NAME = "Name";
        private static final String PRODUCT_DESCRIPTION = "Description";
        private static final String PRODUCT_URL = "Url";
        private static final String  PRODUCT_COST ="Cost";
        private static final String PRODUCT_ID = "ID";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +PRODUCT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"  +PRODUCT_NAME + " VARCHAR(255)," + PRODUCT_COST  + " INTEGER  ," + PRODUCT_URL +" VARCHAR(255)," +  PRODUCT_DESCRIPTION + " VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public DatabaseHelper(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
            this.mContext = context;
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }
    }
}