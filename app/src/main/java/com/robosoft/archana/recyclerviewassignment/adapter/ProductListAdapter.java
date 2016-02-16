package com.robosoft.archana.recyclerviewassignment.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.robosoft.archana.recyclerviewassignment.Modal.AdapterViewFragmentCommunicator;
import com.robosoft.archana.recyclerviewassignment.Modal.ProductList;
import com.robosoft.archana.recyclerviewassignment.Network.ImageDownloader;
import com.robosoft.archana.recyclerviewassignment.R;
import com.robosoft.archana.recyclerviewassignment.fragment.ProductFragment;

import java.util.ArrayList;

/**
 * Created by archanakumari on 11/2/16.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ProductList> mProductList;
    private ArrayList<ProductList> mEditableList = new ArrayList<>();
    private View mOneRow;
    private int mPreviousPositon = 0;
    AdapterViewFragmentCommunicator fragmentCommunicator;
    private LruCache<String, Bitmap> mLrucache;

    public ProductListAdapter(LruCache<String, Bitmap> lruCache, Context mContext, ArrayList<ProductList> mProductList) {
        this.mLrucache = lruCache;
        this.mContext = mContext;
        this.mProductList = mProductList;
        fragmentCommunicator = (AdapterViewFragmentCommunicator) mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mOneRow = LayoutInflater.from(mContext).inflate(R.layout.child, parent, false);
        ViewHolder viewHolder = new ViewHolder(mOneRow);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final ProductList productListClass = mProductList.get(position);
        holder.mTextName.setText(productListClass.getmName());
        holder.mTextCost.setText(String.valueOf(productListClass.getmCost()));
        holder.mTextDesription.setText(productListClass.getmDesription());
        if(isNetworkAvailable()){
            new ImageDownloader(mLrucache, productListClass.getmImage(), holder.mImage).execute();
        }
       else
        {
            holder.mImage.setImageResource(R.drawable.download);
        }

        holder.mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductFragment editFragment = new ProductFragment();
                mEditableList.add(productListClass);
                fragmentCommunicator.onClickOfUpdateViewOfAdapter(editFragment, position, mEditableList);
            }
        });
        holder.mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(R.string.titleOfAlertDialog);
                builder.setMessage(R.string.messageOfAlertDialog);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete opeartaion
                        ProductList productList = mProductList.get(position);
                        mProductList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());

                    }
                });
                builder.setNegativeButton("No", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    //Custome animation
        if (position > mPreviousPositon) {
            com.robosoft.archana.recyclerviewassignment.Modal.AnimationUtils.animatie(holder, true);
        } else {
            com.robosoft.archana.recyclerviewassignment.Modal.AnimationUtils.animatie(holder, true);
        }
        mPreviousPositon = position;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public int getItemCount() {
        return mProductList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextName, mTextCost, mTextDesription;
        private ImageView mImage;
        private Button mButtonUpdate, mButtonDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextName = (TextView) itemView.findViewById(R.id.name);
            mTextCost = (TextView) itemView.findViewById(R.id.cost);
            mTextDesription = (TextView) itemView.findViewById(R.id.description);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mButtonUpdate = (Button) itemView.findViewById(R.id.fupdate);
            mButtonDelete = (Button) itemView.findViewById(R.id.fdelete);

        }
    }
}
