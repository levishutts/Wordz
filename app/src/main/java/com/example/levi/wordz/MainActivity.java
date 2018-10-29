package com.example.levi.wordz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private LinearLayout wordsDisplay;
    private Set<String> savedWords;
    //private String savedNum;

    private int scrollViewCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        Set<String> myWords = new HashSet<>();

        //ScrollView with LinearLayout to put buttons
        wordsDisplay = findViewById(R.id.wordsLayout);
        savedWords = prefs.getStringSet("savedWords", myWords);
        //savedNum = prefs.getString("savedNum", savedNum);
        updateScrollView(savedWords);
    }

    //Called after word added or description added
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Add a button for a successful return from addWords
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                //savedNum = prefs.getString("savedNum", savedNum);
                //System.out.println("From activity result " + savedNum);
                String wordOrPhrase = data.getStringExtra("wordOrPhrase");
                //String wordOrPhrase = savedNum + data.getStringExtra("wordOrPhrase");
                //updateSavedNum();
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                savedWords.add(wordOrPhrase);
                editor.clear();
                editor.putStringSet("savedWords", savedWords);
                editor.commit();
            }
        }

        //Delete a button from MainActivity after successful delete or add description from addDescription
        if (requestCode == 2){
            if(resultCode == RESULT_OK){
                //TODO: return with word and scrollview value to remove from restoredText
                String delete = data.getStringExtra("word");
                savedWords.remove(delete);
                updateScrollView(savedWords);
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.clear();
                editor.putStringSet("savedWords", savedWords);
                editor.commit();
                //  clear, reset, and apply to editor
                // MAKE SURE EDITOR IS CHANGING PREFS BEFORE ONRESUME!!!!!
            }
        }
    }

    /*
    private void updateSavedNum(){
        Integer num = Integer.parseInt(savedNum);
        num += 1;
        savedNum = "0000" + num;
    }
    */

    @Override
    protected void onResume(){
        super.onResume();
        //Recover buttons from String Set and repopulate wordsDisplay
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        if(prefs != null){
            prefs.getStringSet("savedWords", savedWords);
            //prefs.getString("savedNum", savedNum);
            //System.out.println("From onResume " + savedNum);
            if(savedWords != null) {
                updateScrollView(savedWords);
            }
        }
    }

    private void updateScrollView(Set<String> savedWords){
        wordsDisplay.removeAllViews();
        scrollViewCount = 0;

        for(final String wordOrPhrase:savedWords) {
            Button newWordOrPhrase = new Button(this);
            newWordOrPhrase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addDesActivity(wordOrPhrase, scrollViewCount);
                }
            });
            newWordOrPhrase.setTransformationMethod(null);
            newWordOrPhrase.setGravity(Gravity.LEFT);
            newWordOrPhrase.setText(wordOrPhrase);
            wordsDisplay.addView(newWordOrPhrase, scrollViewCount);
            scrollViewCount += 1;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //System.out.println("From onPause " + savedNum);
        editor.putStringSet("savedWords", savedWords);
        //editor.putString("savedNum", savedNum);
        editor.apply();
    }

    // Add button takes you to addWords activity
    public void addWord(View v) {
        Intent intent = new Intent(this, addWords.class);

        //Return after Done and add button to wordsDisplay
        startActivityForResult(intent, 1);
    }

    //Take the clicked button's info to addDescription
    public void addDesActivity(String word, int button){
        Intent intent = new Intent(this, addDescription.class);
        intent.putExtra("info", word);
        intent.putExtra("button", button);

        //Return after Done or Delete button are pressed to remove word/phrase from wordsDisplay
        //and Shared Preferences
        startActivityForResult(intent, 2);
    }
}
