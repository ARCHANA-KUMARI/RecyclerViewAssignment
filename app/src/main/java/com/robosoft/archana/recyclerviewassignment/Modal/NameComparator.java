package com.robosoft.archana.recyclerviewassignment.Modal;

import java.util.Comparator;

/**
 * Created by archana on 12/2/16.
 */
public class NameComparator implements Comparator<ProductList> {
    @Override
    public int compare(ProductList objectbefore, ProductList objectafter) {
        return  objectbefore.getmName().compareToIgnoreCase(objectafter.getmName());
    }
}
