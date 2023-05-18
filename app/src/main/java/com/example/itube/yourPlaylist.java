package com.example.itube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.itube.Data.LinkDatabaseHelper;
import com.example.itube.Model.linkPair;

import java.util.ArrayList;

public class yourPlaylist extends AppCompatActivity implements PlaylistAdapter.OnPairClickListener{

    String username;
    RecyclerView playlist;
    PlaylistAdapter adapter;
    ArrayList<linkPair> playlistLinks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_playlist);
        //get username from intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        //link db helper returns users playlist
        LinkDatabaseHelper db = new LinkDatabaseHelper(this);
        playlistLinks = db.getPairs(username);
        playlist = findViewById(R.id.playlistRecycler);

        //layout manager for recycler (grid with single column to display multiple rows per page)
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        playlist.setLayoutManager(layoutManager);

        adapter = new PlaylistAdapter(this, playlistLinks, this);
        playlist.setAdapter(adapter);
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, videoPlayer.class);
        intent.putParcelableArrayListExtra("linkPairList", playlistLinks);
        intent.putExtra("username", username);
        intent.putExtra("position", position);
        startActivity(intent);
    }


}