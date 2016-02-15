package com.robosoft.archana.recyclerviewassignment.Modal;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by archanakumari on 11/2/16.
 */
public class JsonParser {
    private Context mContext;

    public JsonParser(Context mContext) {
        this.mContext = mContext;

    }

    private ArrayList<ProductList> productArrayList = new ArrayList<>();

    public ArrayList<ProductList> parseJson(String data) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                int cost = jsonObject.getInt("cost");
                String image = jsonObject.getString("image");
                String description = jsonObject.getString("description");
                ProductList productList = new ProductList();
                productList.setmName(name);
                productList.setmCost(cost);
                productList.setmImage(image);
                productList.setmDesription(description);
                productArrayList.add(productList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productArrayList;
    }

}
