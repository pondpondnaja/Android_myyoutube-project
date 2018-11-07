package com.example.nikep.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImagename = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();
    private ArrayList<String> mVideo = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter( Context mContext, ArrayList<String> mImagename, ArrayList<String> mImage, ArrayList<String> mVideoUrl){
        this.mVideo = mVideoUrl;
        this.mImagename = mImagename;
        this.mImage = mImage;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext).asBitmap().load(mImage.get(position)).into(holder.imageV);
        holder.imagename.setText(mImagename.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d(TAG, "onClick: clicked on"+mImagename.get(position));
                Toast.makeText(mContext,mImagename.get(position),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext,GalleryActivity.class);
                intent.putExtra("video_url",mVideo.get(position));
                intent.putExtra("image_name",mImagename.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImagename.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageV;
        TextView imagename;
        CardView parentLayout;

        public ViewHolder(View itemView){
            super(itemView);
            imageV = itemView.findViewById(R.id.iconview);
            imagename = itemView.findViewById(R.id.txtTittle);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
