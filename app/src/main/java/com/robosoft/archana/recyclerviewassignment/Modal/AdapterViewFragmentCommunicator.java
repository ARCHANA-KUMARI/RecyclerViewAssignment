package com.robosoft.archana.recyclerviewassignment.Modal;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by archana on 12/2/16.
 */
public interface AdapterViewFragmentCommunicator {
    public void onClickOfUpdateViewOfAdapter(Fragment fragment,int position,ArrayList<ProductList> editableList);
}
