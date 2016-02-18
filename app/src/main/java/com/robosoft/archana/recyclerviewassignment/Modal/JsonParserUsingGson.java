package com.robosoft.archana.recyclerviewassignment.Modal;

import android.content.Context;
import android.util.Log;

import com.google.gson.*;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by archana on 16/2/16.
 */
public class JsonParserUsingGson {

    private Context mContext;
    private ArrayList<ProductList> jsonList = new ArrayList<>();
    private ProductList mProductList;
    public JsonParserUsingGson(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<ProductList> parseJsonUsingGson(String data) throws JSONException {

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonRootElement = jsonParser.parse(data);
        Gson gson = new Gson();
        JsonArray array = jsonRootElement.getAsJsonArray();
        Iterator<JsonElement> iterator = array.iterator();
        while (iterator.hasNext()){
            JsonElement jsonElement = iterator.next();
            mProductList = gson.fromJson(jsonElement,ProductList.class);
            jsonList.add(mProductList);
        }
        return jsonList;
    }
}
