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

import com.robosoft.archana.recyclerviewassignment.Modal.EditFragmentCommunicator;
import com.robosoft.archana.recyclerviewassignment.Modal.ProductList;
import com.robosoft.archana.recyclerviewassignment.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    private View mOneRow;
    int position;
    private EditText mEditName,mEditCost,mEditImage,mEditDescription;
    private Button mUpdateButton,mCancelButton;
    private EditFragmentCommunicator editFragmentCommunicator;
    private Context mContext;
    public EditFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = container.getContext();
        editFragmentCommunicator = (EditFragmentCommunicator) mContext;
        mOneRow =  inflater.inflate(R.layout.fragment_edit, container, false);
        mEditName =(EditText) mOneRow.findViewById(R.id.editfrageditname);
        mEditCost = (EditText) mOneRow.findViewById(R.id.editfrageditcost);
        mEditImage = (EditText)mOneRow.findViewById(R.id.editfrageditimage);
        mEditDescription = (EditText) mOneRow.findViewById(R.id.editfrageditdesription);
        mUpdateButton = (Button) mOneRow.findViewById(R.id.editfragadd);
        return mOneRow;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        position = bundle.getInt("Position");
        mCancelButton = (Button) mOneRow.findViewById(R.id.editfragcancel);
        final ArrayList<ProductList> editablelist = (ArrayList<ProductList>) bundle.getSerializable("EditableList");
           for(int i = 0;i < editablelist.size();i++){
               ProductList productList1 = editablelist.get(i);
               String name = productList1.getmName();
               mEditName.setText(name);
               int cost = productList1.getmCost();
               mEditCost.setText(""+cost);
               String image = productList1.getmImage();
               mEditImage.setText(image);
               String description = productList1.getmDesription();
               mEditDescription.setText(description);
           }
        final ArrayList<ProductList> arrayList = (ArrayList<ProductList>) bundle.getSerializable("List");
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
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
                editFragmentCommunicator.toSendEdittedList(arrayList, position);
                getActivity().getSupportFragmentManager().beginTransaction().remove(EditFragment.this).commit();

            }
        });
    }
}
