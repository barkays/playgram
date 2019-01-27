package com.example.amirah.playgram2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amirah.playgram2.R;
import com.example.amirah.playgram2.entity.Postingan;

import java.util.List;

public class PostinganListAdapter extends RecyclerView.Adapter<PostinganListAdapter.PostinganViewHolder> {

    class PostinganViewHolder extends RecyclerView.ViewHolder {
        private final TextView previewCaptionView;
        private final TextView previewDateView;
        private final TextView previewTimeView;
        private final ImageView previewImageView;

        private PostinganViewHolder(View itemView) {
            super(itemView);
            previewDateView = itemView.findViewById(R.id.previewDate);
            previewCaptionView = itemView.findViewById(R.id.previewCaption);
            previewTimeView = itemView.findViewById(R.id.previewTime);
            previewImageView = itemView.findViewById(R.id.previewImage);
        }
    }

    private final LayoutInflater mInflater;
    private List<Postingan> mPostingans; // Cached copy of words

    public PostinganListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PostinganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_list_activity, parent, false);
        return new PostinganViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostinganViewHolder holder, int position) {
        if (mPostingans != null) {
            Postingan current = mPostingans.get(position);
            holder.previewDateView.setText(current.getPostDate());
            holder.previewTimeView.setText(current.getPostDateTime());
            holder.previewImageView.setImageBitmap(BitmapFactory.decodeFile(current.getPathPicture()));
            holder.previewCaptionView.setText(current.getCaption());
        }
    }

    public void setPostingans(List<Postingan> postingans){
        mPostingans = postingans;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mPostingans has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mPostingans != null)
            return mPostingans.size();
        else return 0;
    }
}
