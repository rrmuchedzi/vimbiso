package com.example.rrmuchedzi.vimbisomedicare;

import java.io.Serializable;

/**
 * Created by Rufaro R.Muchedzi on 10/23/2017.
 */

class BookmarkArticle implements Serializable {

    private int _ID;
    private String title;
    private String story;
    private String imageLink;
    private String postDate;

    public BookmarkArticle(int _ID, String title, String story, String imageLink, String postDate) {
        this._ID = _ID;
        this.title = title;
        this.story = story;
        this.imageLink = imageLink;
        this.postDate = postDate;
    }

    public int get_ID() {
        return _ID;
    }

    public String getTitle() {
        return title;
    }

    public String getStory() {
        return story;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getPostDate() {
        return postDate;
    }

}
