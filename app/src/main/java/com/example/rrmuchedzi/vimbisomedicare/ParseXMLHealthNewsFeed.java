package com.example.rrmuchedzi.vimbisomedicare;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ParseXMLHealthNewsFeed {

    private static final String TAG = "ParseXMLHealthNewsFeed";
    private List <HealthNews> collection;

    public ParseXMLHealthNewsFeed(){
        collection = new ArrayList<>();
    }

    public List <HealthNews> getAvailableNewsFeed(){
        return collection;
    }

    public boolean parseHealthNewsXML ( String xmlFeedPassed ) {

        HealthNews currentFeed = null;
        String textFound = null;
        boolean inEntry = false;
        int eventType;
        String tagName = null;

        try {

            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput( new StringReader(xmlFeedPassed) );
            eventType = xmlPullParser.getEventType();
            while( eventType != xmlPullParser.END_DOCUMENT ) {

                tagName = xmlPullParser.getName();

                switch ( eventType ) {

                    case XmlPullParser.START_TAG: {
                        if( "item".equalsIgnoreCase(tagName) ) {
                            inEntry = true;
                            currentFeed = new HealthNews();
                        }
                        break;
                    }
                    case XmlPullParser.TEXT: {
                        textFound = xmlPullParser.getText();
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if( inEntry ) {
                            if( "item".equalsIgnoreCase(tagName) ) {
                                if(currentFeed.getPostBanner() != null){
                                   collection.add(currentFeed);
                                }
                                inEntry = false;
                            }
                            if( "title".equalsIgnoreCase(tagName) ) {
                                currentFeed.setTitle(textFound);
                            }
                            if( "link".equalsIgnoreCase(tagName) ) {
                                currentFeed.setLink( textFound );
                            }
                            if( "pubDate".equalsIgnoreCase(tagName) ) {
                                textFound = textFound.substring(0, 17);
                                currentFeed.setPostDate( textFound.trim() );
                            }
                            if( "description".equalsIgnoreCase(tagName) ) {
//                                Log.d(TAG, "parseHealthNewsXML: DESCRIPTION STARTS");
                                descritionParser( currentFeed, textFound );
//                                Log.d(TAG, "parseHealthNewsXML: DESCRIPTION ENDS");
                            }
                        }
                        break;
                    }
                    default:
                }

                eventType = xmlPullParser.next();

            }
            return true;

        } catch (XmlPullParserException e) {
            Log.e(TAG, "parseHealthNewsXML: XmlPullParserException " + e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, "parseHealthNewsXML: IOException" + e.getMessage() );
        }

        return false;
    }

    private void descritionParser ( HealthNews resourceRef, String descriptionValue ) {
        String textValue = null;
        String tagName = null;
        boolean foundSRC = false;
        int eventType;

        try {
            //final Pattern pattern = Pattern.compile("<p>(.+?)</p>");
            final Pattern pattern2 = Pattern.compile("src=\"(.+?)\"");
           // final Matcher matcher = pattern.matcher(descriptionValue.trim());
            final Matcher matcher2 = pattern2.matcher(descriptionValue.trim());

           // matcher.find();
            matcher2.find();

            String src = matcher2.group(1);
           // String des = matcher.group(1);

            if( src != null ) {

                try {
                    int lastIndex = src.lastIndexOf('?');
                    src = src.substring(0, lastIndex);
                } catch( StringIndexOutOfBoundsException w) {
                    Log.e(TAG, "descritionParser: Image SRC Parce Exception :" + src );
                }

                resourceRef.setPostBanner(src);
                resourceRef.setDescription("API Changed");

            }

        } catch (IllegalStateException e) {
            //Log.d(TAG, "descritionParser: CAUSED BY " + descriptionValue);
        }
    }

}
