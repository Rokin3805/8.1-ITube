package com.example.itube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.itube.Model.linkPair;

import java.util.ArrayList;

public class videoPlayer extends AppCompatActivity {

    ArrayList<linkPair> linkPairList;
    WebView player;

    String username;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        player = findViewById(R.id.vidPlayer);
        player.getSettings().setJavaScriptEnabled(true);
        player.setWebViewClient(new WebViewClient());

        //get the intent and retrieve the necessary data
        Intent intent = getIntent();
        linkPairList = intent.getParcelableArrayListExtra("linkPairList");
        username = intent.getStringExtra("username");
        position = intent.getIntExtra("position", 0);

        //play the video
        playVideo();

        //next button click listener
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //increments the position and play the next video
                position++;
                playVideo();
            }
        });
    }

    //method to play the video at the current position
    public void playVideo() {
        if (position < linkPairList.size()) {
            //get the link pair from the list at the current position
            linkPair currentLinkPair = linkPairList.get(position);
            //get the video URL from the link pair
            String videoUrl = currentLinkPair.getLink();
            //initialize the HTML content with the YouTube video player code (with my variable used)
            //source https://stackoverflow.com/questions/15090782/youtube-autoplay-not-working-on-mobile-devices-with-embedded-html5-player
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "  <head>\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=yes\">\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <div id=\"player\"></div>\n" +
                    "    <script>\n" +
                    "      var tag = document.createElement('script');\n" +
                    "      tag.src = \"https://www.youtube.com/iframe_api\";\n" +
                    "      var firstScriptTag = document.getElementsByTagName('script')[0];\n" +
                    "      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);\n" +
                    "      var player;\n" +
                    "      function onYouTubeIframeAPIReady() {\n" +
                    "        player = new YT.Player('player', {\n" +
                    "          width: '100%',\n" +
                    //uses my video id
                    "          videoId: '" + videoUrl + "',\n" +
                    "          playerVars: { 'autoplay': 1, 'playsinline': 1 },\n" +
                    "          events: {\n" +
                    "            'onReady': onPlayerReady\n" +
                    "          }\n" +
                    "        });\n" +
                    "      }\n" +
                    "      function onPlayerReady(event) {\n" +
                    "        event.target.mute();\n" +
                    "        event.target.playVideo();\n" +
                    "      }\n" +
                    "    </script>\n" +
                    "    <script src=\"https://www.youtube.com/iframe_api\"></script>\n" +
                    "  </body>\n" +
                    "</html>";

            //load the HTML content into the WebView to play the video
            player.loadData(htmlContent, "text/html", "utf-8");
        } else {
            //all videos in the playlist have been played, stop the player
            stopPlayer();
        }
    }

    //method to stop the player by loading null data into the WebView
    public void stopPlayer() {
        player.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        //toast to notify user playlist ended
        Toast.makeText(this, "Playlist ended", Toast.LENGTH_SHORT).show();
    }
}

