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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mVideoname = new ArrayList<>();
    private ArrayList<String> mImageURL = new ArrayList<>();
    private ArrayList<String> mVideo = new ArrayList<>();
    private ArrayList<String> mLikeCount = new ArrayList<>();
    private ArrayList<String> mViewCount = new ArrayList<>();
    private Context mContext;

    //POJO
    public RecyclerViewAdapter( Context mContext, ArrayList<String> mVideoname, ArrayList<String> mImageurl, ArrayList<String> mVideoUrl, ArrayList<String> mLikecount, ArrayList<String> mViewcount){
        this.mVideo = mVideoUrl;
        this.mVideoname = mVideoname;
        this.mImageURL = mImageurl;
        this.mLikeCount = mLikecount;
        this.mViewCount = mViewcount;
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

        Glide.with(mContext).asBitmap().load(mImageURL.get(position)).into(holder.imageV);
        holder.Videoname.setText(mVideoname.get(position));

        int likebefore = Integer.valueOf(mLikeCount.get(position));
        int likeafter = likebefore/1000;
        final String likereal = Integer.valueOf(likeafter).toString();

        String viewNumber = mViewCount.get(position);
        final String convertView = new DecimalFormat("#,###.##").format(Double.parseDouble(viewNumber));

        holder.videoLike.setText("Like : "+likeafter+"K");
        holder.videoView.setText(convertView +" Views");

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d(TAG, "onClick: clicked on"+mVideoname.get(position));
                Toast.makeText(mContext,mVideoname.get(position),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext,GalleryActivity.class);
                intent.putExtra("Video_url",mVideo.get(position));
                intent.putExtra("Video_name",mVideoname.get(position));
                intent.putExtra("Video_like",likereal);
                intent.putExtra("Video_view",convertView);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageV;
        TextView Videoname;
        TextView videoLike;
        TextView videoView;
        CardView parentLayout;

        public ViewHolder(View itemView){
            super(itemView);
            imageV = itemView.findViewById(R.id.iconview);
            Videoname = itemView.findViewById(R.id.txtTittle);
            videoLike = itemView.findViewById(R.id.videolike);
            videoView = itemView.findViewById(R.id.viewcount);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
