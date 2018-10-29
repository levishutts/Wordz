//TODO: Add capability to save and delete from collection
package com.example.levi.wordz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class Collection extends AppCompatActivity {

    private String wordOrPhrase;
    private String description;
    private LinearLayout collectionScroll;
    private Set<String> myCollection;
    private int scrollViewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        collectionScroll = findViewById(R.id.linearLayout);

        //Get word data
        Intent intent = getIntent();
        wordOrPhrase = intent.getStringExtra("wordOrPhrase");
        description = intent.getStringExtra("description");

        Set<String> myWords = new HashSet<>();

        //Load prefs
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        if(prefs != null){
            myCollection = prefs.getStringSet("savedWords", myWords);
            System.out.println("onCreate " + myCollection);
            myCollection.add(wordOrPhrase + "\n" + description);
        }

        updateCollection();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Recover buttons from String Set and repopulate wordsDisplay
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        if(prefs != null){
            myCollection =  prefs.getStringSet("savedWords", myCollection);
            System.out.println("onResume " + myCollection);
            //prefs.getString("savedNum", savedNum);
            //System.out.println("From onResume " + savedNum);
            if(myCollection != null) {
                updateCollection();
            }
        }
    }

    private void updateCollection(){
        collectionScroll.removeAllViews();
        scrollViewCount = 0;

        //SpannableString ss1=  new SpannableString(wordOrPhrase);

        for(final String wordOrPhrase:myCollection) {
            TextView addToCollection = new TextView(this);
            //int length = wordOrPhrase.length();
            //ss1.setSpan(new RelativeSizeSpan(2f), 0, length, 0); // set size

            //addToCollection.setText(ss1);

            addToCollection.setText(wordOrPhrase);
            collectionScroll.addView(addToCollection, scrollViewCount);
            scrollViewCount += 1;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //System.out.println("From onPause " + savedNum);
        editor.putStringSet("savedWords", myCollection);
        System.out.println("onPause " + myCollection);
        //editor.putString("savedNum", savedNum);
        editor.apply();
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
