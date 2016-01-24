package edu.rosehulman.defaritl.weatherpics;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by defaritl on 1/21/2016.
 */
public class Weatherpic {

    private String caption;
    private String url;

    @JsonIgnore
    private String key;

    public Weatherpic(){
        //emptaaay - required for Firebase (or Jackson more specfkly)
    }

    public Weatherpic(String caption, String url){
        this.caption = caption;
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
