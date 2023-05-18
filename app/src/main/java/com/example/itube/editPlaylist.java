package com.example.itube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.itube.Data.LinkDatabaseHelper;
import com.example.itube.Model.linkPair;

import java.util.ArrayList;

public class editPlaylist extends AppCompatActivity {

    EditText url;
    String username;
    ArrayList<linkPair> playlistLinks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_playlist);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        url = findViewById(R.id.editTextYouTubeURL);
    }

    public void addLink(View view) {
        String link = url.getText().toString().trim();

        //check if the link is blank/empty
        if (link.isEmpty())
        {
            //inform the user that the link cannot be empty
            Toast.makeText(this, "LINK CANNOT BE EMPTY", Toast.LENGTH_SHORT).show();
            return;
        }

        linkPair pair = new linkPair(username, link);

        LinkDatabaseHelper dbHelper = new LinkDatabaseHelper(this);
        boolean isNewLink = dbHelper.checkIsNew(pair);

        if (isNewLink)
        {
            //add the link to the LinkDatabase
            dbHelper.insertPair(pair);
            //Toast informs user successfully added
            Toast.makeText(this, "ADDED TO PLAYLIST", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Will not add duplicate links, inform user
            Toast.makeText(this, "LINK ALREADY IN PLAYLIST", Toast.LENGTH_SHORT).show();
        }
    }

    //playlist button takes user to playlist page, brings username in intent
    public void myPlaylistButton(View view)
    {
        Intent goToPlaylist = new Intent(this, yourPlaylist.class);
        goToPlaylist.putExtra("username", username);
        startActivity(goToPlaylist);
    }

    //play button begins playing the playlist from position 0
    public void playButton(View view)
    {

        //link db helper returns users playlist (we are not going through recycler view which does this, so we need to generate it)
        LinkDatabaseHelper db = new LinkDatabaseHelper(this);
        playlistLinks = db.getPairs(username);

        //same intent as in the onClick method in recycler view, except position is always 0 when playing from beginning
        Intent intent = new Intent(this, videoPlayer.class);
        intent.putParcelableArrayListExtra("linkPairList", playlistLinks);
        intent.putExtra("username", username);
        intent.putExtra("position", 0);
        startActivity(intent);
    }

}