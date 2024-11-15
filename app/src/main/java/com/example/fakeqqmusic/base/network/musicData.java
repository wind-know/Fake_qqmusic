package com.example.fakeqqmusic.base.network;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class musicData implements Serializable {
    public musicData(String name, String artistsname, String url, String picurl) {
        this.data = new DataDTO();
        this.data.name = name;
        this.data.artistsname = artistsname;
        this.data.url = url;
        this.data.picurl = picurl;

    }

    @SerializedName("data")
    private DataDTO data;

    public static class DataDTO implements Serializable{
        @SerializedName("name")
        private String name;
        @SerializedName("url")
        private String url;
        @SerializedName("picurl")
        private String picurl;
        @SerializedName("artistsname")
        private String artistsname;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicurl() {
            return picurl;
        }
         public void setPicurl(String picurl){
            this.picurl = picurl;
        }

        public String getArtistsname() {
            return artistsname;
        }


    }

    public DataDTO getData() {
        return data;
    }
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}
