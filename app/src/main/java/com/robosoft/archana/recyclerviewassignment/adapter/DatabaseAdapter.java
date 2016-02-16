package com.robosoft.archana.recyclerviewassignment.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by archana on 16/2/16.
 */
public class DatabaseAdapter {

    
    static public class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "Productabase";
        private static final String TABLE_NAME = "Product";
        private static final int DATABASE_VERSION = 1;
        private static final String PRODUCT_NAME = "Name";
        private static final String PRODUCT_DESCRIPTION = "Description";
        private static final String PRODUCT_URL = "url";
        private static int PRODUCT_COST ;
        private static final String PRODUCT_ID = "ID";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +PRODUCT_ID +" INTEGER PRIMARY KEY AUTOINCREAMENT"  +PRODUCT_NAME + " VARCHAR(255)," + PRODUCT_COST  + " INTEGER  ," + PRODUCT_DESCRIPTION +" VARCHAR(255)," +  PRODUCT_URL + " VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public DatabaseHelper(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
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
