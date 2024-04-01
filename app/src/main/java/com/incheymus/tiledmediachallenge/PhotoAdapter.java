package com.incheymus.tiledmediachallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private final List<Photo> photos;
    private final Context context;

    public PhotoAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    public void clearAdapter() {
        if(photos != null) {
            photos.clear();
            notifyDataSetChanged();
        }
    }

    public void addPhotosToAdapter(List<Photo> newPhotos) {
        int startPosition = photos.size();
        photos.addAll(newPhotos);
        notifyItemRangeInserted(startPosition, newPhotos.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView photoName;
        ImageView photoContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoName = itemView.findViewById(R.id.namePLACEHOLDER);
            photoContent = itemView.findViewById(R.id.imagePLACEHOLDER);
        }
    }

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_photos_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, int position) {
        if (photos == null || photos.isEmpty()) {
            return;
        }

        Photo photo = photos.get(position);

        holder.photoName.setText(photo.getName());

        Glide.with(context)
                .load(photo.getContentUrl())
                .placeholder(R.drawable.tiledmedia)
                .error(R.drawable.tiledmedia)
                .into(holder.photoContent);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
