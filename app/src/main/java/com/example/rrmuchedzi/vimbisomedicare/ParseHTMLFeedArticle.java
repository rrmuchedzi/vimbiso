package com.example.rrmuchedzi.vimbisomedicare;

import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * This class is used to download and parse the Article
 */

public class ParseHTMLFeedArticle {
    private static final String TAG = "ParseHTMLFeedArticle";
    private final String HTMLPageLink;
    private StringBuilder articleResult;

    public ParseHTMLFeedArticle( @NonNull String HTMLLink ) {
        this.HTMLPageLink = HTMLLink;
    }

    public String downloadAndParseHTMLPage() {
        Document doc;
        articleResult = new StringBuilder();

        try {
            Log.d(TAG, "downloadHTMLPage: LINK - " + HTMLPageLink);
            doc = Jsoup.connect(HTMLPageLink).get();
            String title = doc.title();
            Elements links = doc.select("article");

            for (Element linkAt: links) {
                Log.d(TAG, "downloadHTMLPage: ARTICLE FOUND : " + linkAt.text());
                articleResult.append(linkAt.text()).append("/n");
            }

            return articleResult.toString();

        } catch (IOException e) {

        }

        return "Article could not be loaded";
    }
}
