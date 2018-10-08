package com.example.levi.wordz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private LinearLayout wordsToLookup;
    private SharedPreferences.Editor editor;
    private Set<String> restoredText;

    private int scrollViewCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ScrollView with LinearLayout to put buttons
        wordsToLookup = findViewById(R.id.wordsLayout);
    }

    void populateScroll(){
        scrollViewCount = 0;
        for(final String wordOrPhrase:restoredText){
            Button newWordOrPhrase = new Button(this);
            newWordOrPhrase.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    addDesActivity(wordOrPhrase, scrollViewCount);
                }
            });
            newWordOrPhrase.setTransformationMethod(null);
            newWordOrPhrase.setGravity(Gravity.LEFT);
            newWordOrPhrase.setText(wordOrPhrase);

            wordsToLookup.addView(newWordOrPhrase, scrollViewCount);
            scrollViewCount += 1;
        }
    }

    //Called after word added or description added
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Received a result from code " + requestCode);
        super.onActivityResult(requestCode, resultCode, data);

        //Add a button for a successful return from addWords
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                final String wordOrPhrase = data.getStringExtra("wordOrPhrase");
                Button newWordOrPhrase = new Button(this);
                newWordOrPhrase.setOnClickListener( new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        addDesActivity(wordOrPhrase, scrollViewCount);
                    }
                });
                newWordOrPhrase.setTransformationMethod(null);
                newWordOrPhrase.setGravity(Gravity.LEFT);
                newWordOrPhrase.setText(wordOrPhrase);

                wordsToLookup.addView(newWordOrPhrase, scrollViewCount);
                scrollViewCount += 1;
            }
        }

        //Delete a button from MainActivity after successful delete or add description from addDescription
        if (requestCode == 2){
            System.out.println("Back from addDescription");
            if(resultCode == RESULT_OK){
                //TODO: return with word and scrollview value to remove from restoredText
                //  clear, reset, and apply to editor
                // MAKE SURE EDITOR IS CHANGING PREFS BEFORE ONRESUME!!!!!
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Recover buttons from String Set and repopulate wordsToLookup
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        if (prefs != null){
            Set<String> myWords = new HashSet<>();
            restoredText = prefs.getStringSet("List", myWords);
            //TODO: sort by scrollViewCount
        }
        populateScroll();
    }

    @Override
    protected void onPause(){
        super.onPause();

        //Save buttons to String Set for saving ability
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        editor = prefs.edit();
        //TODO: Create restoredText with map to keep scrollview number with button
        //populate restoredText with button text and corresponding number
        /*
            View child = wordsToLookup.getChildAt(i);
            myWords.add(((Button) child).getText().toString());
        }
        */
        //Add restoredText to the editor
        //editor.putStringSet("List", myWords);
        editor.apply();
    }

    // Add button takes you to addWords activity
    public void sendMessage(View v) {
        Intent intent = new Intent(this, addWords.class);

        //Return after Done and add button to wordsToLookup
        startActivityForResult(intent, 1);
    }

    //Take the clicked button's info to addDescription
    public void addDesActivity(String word, int button){
        Intent intent = new Intent(this, addDescription.class);
        intent.putExtra("info", word);
        intent.putExtra("button", button);

        //Return after Done or Delete button are pressed to remove word/phrase from wordsToLookup
        //and Shared Preferences
        System.out.println("Starting addDescription activity with intent to receive result");
        startActivityForResult(intent, 2);
    }
}
