package com.robosoft.archana.recyclerviewassignment.Modal;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by archana on 16/2/16.
 */
public class Message {

    public static String TAG = "Hello";
    public static void message(Context context ,String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
      Log.i("Hello","THis message"+message);
    }
}
