package com.robosoft.archana.recyclerviewassignment.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.robosoft.archana.recyclerviewassignment.Modal.Communicator;
import com.robosoft.archana.recyclerviewassignment.Modal.EditFragmentCommunicator;
import com.robosoft.archana.recyclerviewassignment.Modal.Message;
import com.robosoft.archana.recyclerviewassignment.Modal.Notification;
import com.robosoft.archana.recyclerviewassignment.Modal.ProductList;
import com.robosoft.archana.recyclerviewassignment.R;
import com.robosoft.archana.recyclerviewassignment.adapter.DatabaseAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements Notification{

    private ArrayList<ProductList> addedProductList = new ArrayList<>();
    private Button  mPerformButton, mCancelButton;
    private EditText mEditName, mEditCost, mEditImage, mEditDescription;
    private View mOneRow;
    Communicator communicator;
    EditFragmentCommunicator editFragmentCommunicator;
    private Context mContext;
    DatabaseAdapter databaseAdapter;
    private String mProductOldName,mProductOldUrl,mProductOldDescription;
    private int mProductOldCost;
    public ProductFragment() {
        // Required empty public constructor
    }
    @Override
    public void sendData(ArrayList<ProductList> productLists) {
        addedProductList = productLists;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = container.getContext();
        communicator = (Communicator) mContext;
        databaseAdapter = new DatabaseAdapter(mContext);
        editFragmentCommunicator = (EditFragmentCommunicator) mContext;
        mOneRow = inflater.inflate(R.layout.fragment_product, container, false);

        mEditName = (EditText) mOneRow.findViewById(R.id.feditname);
        mEditCost = (EditText) mOneRow.findViewById(R.id.feditcost);
        mEditImage = (EditText) mOneRow.findViewById(R.id.feditimage);
        mEditDescription = (EditText) mOneRow.findViewById(R.id.feditdesription);
        mPerformButton = (Button) mOneRow.findViewById(R.id.fadd);
        mCancelButton = (Button) mOneRow.findViewById(R.id.fcancel);

        //Cancel button operation
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditName.getText().toString().trim().length() > 0) {
                    mEditName.setText(" ");
                }

                if (mEditCost.toString().trim().length() > 0) {
                    mEditCost.setText(" ");
                }
                if (mEditImage.getText().toString().trim().length() > 0) {
                    mEditImage.setText(" ");
                }
                if (mEditDescription.getText().toString().length() > 0) {
                    mEditDescription.setText(" ");
                }


            }
        });
        return mOneRow;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle==null){
            mPerformButton.setText("Add");
            //Add operation
            mPerformButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //addedProductList.clear();
                    String name = mEditName.getText().toString();
                    int cost = Integer.parseInt(mEditCost.getText().toString());
                    String image = mEditImage.getText().toString();
                    String description = mEditDescription.getText().toString();
                    long id = databaseAdapter.insertData(name,cost,image,description);
                    if (id < 0) {
                        Message.message(mContext, "Unsuccessfully");
                    } else {
                        Message.message(mContext, "Data is inserted successfully");
                    }
                    communicator.onClickOfAddButton(addedProductList);
                    getActivity().getSupportFragmentManager().beginTransaction().remove(ProductFragment.this).commit();
                }
            });
        }
        else {
            //Edit operation
            mPerformButton.setText("Update");
            final int position = bundle.getInt("Position");
            final ArrayList<ProductList> editablelist = (ArrayList<ProductList>) bundle.getSerializable("EditableList");
            for(int i = 0;i < editablelist.size();i++){
                ProductList productList1 = editablelist.get(i);
                mProductOldName = productList1.getmName();
                mEditName.setText(mProductOldName);
                mProductOldCost = productList1.getmCost();
                mEditCost.setText(String.valueOf(mProductOldCost));
                mProductOldUrl = productList1.getmImage();
                mEditImage.setText(mProductOldUrl);
                mProductOldDescription = productList1.getmDesription();
                mEditDescription.setText(mProductOldDescription);
            }
            final ArrayList<ProductList> arrayList = (ArrayList<ProductList>) bundle.getSerializable("List");
            mPerformButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductList productList = arrayList.get(position);
                    String editedname = mEditName.getText().toString();
                    int editedcost = Integer.parseInt(mEditCost.getText().toString());
                    String editedimage = mEditImage.getText().toString();
                    String editeddescription = mEditDescription.getText().toString();
                    productList.setmName(editedname);
                    productList.setmCost(editedcost);
                    productList.setmImage(editedimage);
                    productList.setmDesription(editeddescription);
                    arrayList.set(position, productList);
                    int noOfRowsUpdated = databaseAdapter.update(mProductOldName, editedname, editedcost, editedimage, editeddescription);
                    if(noOfRowsUpdated>0){
                        Message.message(mContext,""+noOfRowsUpdated+"Updated Successfully");
                    }
                    else
                    {
                        Message.message(mContext,"Not Updated");
                    }
                    editFragmentCommunicator.onClickOfEditButton(position);
                    getActivity().getSupportFragmentManager().beginTransaction().remove(ProductFragment.this).commit();

                }
            });
        }

    }



}
