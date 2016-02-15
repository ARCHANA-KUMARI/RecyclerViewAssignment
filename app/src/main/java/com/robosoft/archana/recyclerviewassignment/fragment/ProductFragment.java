package com.robosoft.archana.recyclerviewassignment.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.robosoft.archana.recyclerviewassignment.Modal.Communicator;
import com.robosoft.archana.recyclerviewassignment.Modal.EditFragmentCommunicator;
import com.robosoft.archana.recyclerviewassignment.Modal.ProductList;
import com.robosoft.archana.recyclerviewassignment.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private ArrayList<ProductList> addedProductList = new ArrayList<>();
    private Button mAddButton, mCancelButton;
    private EditText mEditName, mEditCost, mEditImage, mEditDescription;
    private View mOneRow;
    Communicator communicator;
    EditFragmentCommunicator editFragmentCommunicator;
    private Context mContext;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = container.getContext();
        communicator = (Communicator) mContext;
        editFragmentCommunicator = (EditFragmentCommunicator) mContext;
        mOneRow = inflater.inflate(R.layout.fragment_add_product, container, false);

        mEditName = (EditText) mOneRow.findViewById(R.id.feditname);
        mEditCost = (EditText) mOneRow.findViewById(R.id.feditcost);
        mEditImage = (EditText) mOneRow.findViewById(R.id.feditimage);
        mEditDescription = (EditText) mOneRow.findViewById(R.id.feditdesription);

        mAddButton = (Button) mOneRow.findViewById(R.id.fadd);

        mCancelButton = (Button) mOneRow.findViewById(R.id.fcancel);


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedProductList.clear();
                String name = mEditName.getText().toString();
                int cost = Integer.parseInt(mEditCost.getText().toString());
                String image = mEditImage.getText().toString();
                String description = mEditDescription.getText().toString();
                ProductList productList = new ProductList();
                productList.setmName(name);
                productList.setmCost(cost);
                productList.setmImage(image);
                productList.setmDesription(description);
                addedProductList.add(productList);
                communicator.toCommunicate(addedProductList);
                getActivity().getSupportFragmentManager().beginTransaction().remove(ProductFragment.this).commit();
            }
        });
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
        mCancelButton = (Button) mOneRow.findViewById(R.id.editfragcancel);
        Bundle bundle = getArguments();
        if(bundle==null){
            mAddButton.setText("Add");
        }
        else {
            mAddButton.setText("Update");
            final int position = bundle.getInt("Position");
            final ArrayList<ProductList> editablelist = (ArrayList<ProductList>) bundle.getSerializable("EditableList");
            for(int i = 0;i < editablelist.size();i++){
                ProductList productList1 = editablelist.get(i);
                String name = productList1.getmName();
                mEditName.setText(name);
                mEditCost.setText(String.valueOf( productList1.getmCost()));
                String image = productList1.getmImage();
                mEditImage.setText(image);
                String description = productList1.getmDesription();
                mEditDescription.setText(description);
            }
            final ArrayList<ProductList> arrayList = (ArrayList<ProductList>) bundle.getSerializable("List");
            mAddButton.setOnClickListener(new View.OnClickListener() {
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
                    editFragmentCommunicator.toSendEdittedList(position);
                    getActivity().getSupportFragmentManager().beginTransaction().remove(ProductFragment.this).commit();

                }
            });
        }

    }


}
