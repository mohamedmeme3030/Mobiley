package io.founder.co.mem.moh.mobileymeme.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import io.founder.co.mem.moh.mobileymeme.Class.Mobile;
import io.founder.co.mem.moh.mobileymeme.Interface.OnItemClickRecyclerListener;
import io.founder.co.mem.moh.mobileymeme.R;


public class RecyclerViewAdapterProfile extends RecyclerView.Adapter<RecyclerViewAdapterProfile.ViewHolder> {
    private static final String TAG = "meme";

    private ArrayList<Mobile> mMobiles;
    private Context mContext;
    private OnItemClickRecyclerListener listener;


    public RecyclerViewAdapterProfile(Context mContext, ArrayList<Mobile> newMobiles, OnItemClickRecyclerListener nListener) {
        mMobiles = newMobiles;
        this.mContext = mContext;
        this.listener = nListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_listitme_profile, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(mContext).load(mMobiles.get(position).getImage()).into(holder.image);
        holder.imageBrand.setText(mMobiles.get(position).getBrand());
        holder.imageModel.setText(mMobiles.get(position).getModel());
        holder.imagePhone.setText(mMobiles.get(position).getPhone());
        holder.imagePrice.setText(mMobiles.get(position).getPrice());
        holder.imageCondition.setText(mMobiles.get(position).getCondition());
        holder.imageLocation.setText(mMobiles.get(position).getLocation());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + mMobiles.get(position).getBrand());
                Toast.makeText(mContext, mMobiles.get(position).getModel(), Toast.LENGTH_LONG).show();
                listener.onClick(mMobiles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMobiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView imageModel, imagePrice, imagePhone, imageBrand, imageCondition, imageLocation;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageBrand = itemView.findViewById(R.id.image_brand);
            imageModel = itemView.findViewById(R.id.image_model);
            imagePrice = itemView.findViewById(R.id.image_price);
            imagePhone = itemView.findViewById(R.id.image_phone);
            imageCondition = itemView.findViewById(R.id.image_condition);
            imageLocation = itemView.findViewById(R.id.image_location);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    //new mobile added in recycler view
    public void onNewMobileAdded(Mobile mobile) {
        mMobiles.add(mobile);
        notifyDataSetChanged();
    }
}
