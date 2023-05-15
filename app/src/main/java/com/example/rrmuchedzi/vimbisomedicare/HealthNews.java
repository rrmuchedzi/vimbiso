package com.example.rrmuchedzi.vimbisomedicare;

import java.io.Serializable;

/**
 * Created by Rufaro R.Muchedzi on 10/23/2017.
 */

class HealthNews implements Serializable {

    private String title;
    private String link;
    private String description;
    private String postBanner;
    private String postDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostBanner() {
        return postBanner;
    }

    public void setPostBanner(String postBanner) {
        this.postBanner = postBanner;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return "HealthNews{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", postBanner='" + postBanner + '\'' +
                ", postDate ='" + postDate + '\'' +
                '}';
    }
}
