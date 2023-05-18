package com.example.itube.Model;

import android.os.Parcel;
import android.os.Parcelable;

//implemented parceable default methods so that i can pass the arrayList of linkPairs via intent
public class linkPair implements Parcelable
{
     String user;
     String link;

     public linkPair(String user, String link)
     {
          this.user = user;
          this.link = link;
     }

     protected linkPair(Parcel in) {
          user = in.readString();
          link = in.readString();
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(user);
          dest.writeString(link);
     }

     @Override
     public int describeContents() {
          return 0;
     }

     public static final Creator<linkPair> CREATOR = new Creator<linkPair>() {
          @Override
          public linkPair createFromParcel(Parcel in) {
               return new linkPair(in);
          }

          @Override
          public linkPair[] newArray(int size) {
               return new linkPair[size];
          }
     };

     public String getUser() {
          return user;
     }

     public void setUser(String user) {
          this.user = user;
     }

     public String getLink() {
          return link;
     }

     public void setLink(String link) {
          this.link = link;
     }
}
