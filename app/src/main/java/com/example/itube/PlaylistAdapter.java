package com.example.itube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itube.Model.linkPair;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PairViewHolder> {

    //list to populate the recycler view
    private ArrayList<linkPair> pairList;
    //listener for recycler view
    private OnPairClickListener listener;

    //constructor
    public PlaylistAdapter(Context context, ArrayList<linkPair> pairList, OnPairClickListener listener) {
        this.pairList = pairList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_items, parent, false);
        return new PairViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PairViewHolder holder, int position) {
        holder.bind(pairList.get(position));
    }

    @Override
    public int getItemCount() {
        return pairList.size();
    }

    public static class PairViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView itemView;

        private OnPairClickListener listener;


        public PairViewHolder(@NonNull View itemView, OnPairClickListener listener) {
            super(itemView);

            this.itemView = itemView.findViewById(R.id.textViewItem);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        //onclick gets adapter position
        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition());
        }

        //user getter from linkPair to set text to the URL
        public void bind(linkPair pair) {
            itemView.setText(pair.getLink());
        }
    }

    public interface OnPairClickListener {
        void onItemClick(int position);
    }
}
