package com.example.funapp.adapters;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.funapp.R;
import java.util.List;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.ViewHolder> {

    private List<String> avataresList;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;

    public AvatarAdapter(List<String> avatares, int layout, Activity activity, OnItemClickListener listenerItem) {
        this.avataresList = avatares;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
    }

    @NonNull
    @Override
    public AvatarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        AvatarAdapter.ViewHolder viewHolder = new AvatarAdapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AvatarAdapter.ViewHolder viewHolder, final int i) {

        final String avatar = avataresList.get(i);
        switch (avatar) {
            case "avatar1":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar1);
                break;
            case "avatar2":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar2);
                break;
            case "avatar3":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar3);
                break;
            case "avatar4":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar4);
                break;
            case "avatar5":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar5);
                break;
            case "avatar6":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar6);
                break;
            case "avatar7":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar7);
                break;
            case "avatar8":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar8);
                break;
            case "avatar9":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar9);
                break;
            case "avatar10":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar10);
                break;
            case "avatar11":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar11);
                break;
            case "avatar12":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar12);
                break;
            case "avatar13":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar13);
                break;
            case "avatar14":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar14);
                break;
            case "avatar15":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar15);
                break;
            case "avatar16":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar16);
                break;
            case "avatar17":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar17);
                break;
            case "avatar18":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar18);
                break;
            case "avatar19":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar19);
                break;
            case "avatar20":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar20);
                break;
            case "avatar21":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar21);
                break;
            case "avatar22":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar22);
                break;
            case "avatar23":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar23);
                break;
            case "avatar24":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar24);
                break;
            case "avatar25":
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar25);
                break;
            default:
                viewHolder.imgvAvatar.setImageResource(R.drawable.avatar0);
                break;
        }
        viewHolder.imgvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerItem.onItemClick(avatar, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return avataresList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgvAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvAvatar = itemView.findViewById(R.id.imgVElegirAvatar);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String avatar, int position);
    }
}