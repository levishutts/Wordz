package com.example.levi.wordz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private LinearLayout wordsDisplay;
    private Set<String> savedWords;
    private String FILE_NAME = "mainWordz.txt";
    private File mainWordz;
    //private String savedNum;

    /*
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
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ScrollView with LinearLayout to put buttons
        wordsDisplay = findViewById(R.id.wordsLayout);
        //savedNum = prefs.getString("savedNum", savedNum);
    }

    //Called after word added or description added
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Add a button for a successful return from addWords
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                FileWriter fileWriter;
                File mainWordz;
                String line;
                mainWordz = new File (getFilesDir(), FILE_NAME);
                try {
                    fileWriter = new FileWriter(mainWordz, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(data.getStringExtra("wordOrPhrase"));
                    bufferedWriter.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        //Delete a button from MainActivity after successful delete or add description from addDescription
        if (requestCode == 2){
            if(resultCode == RESULT_OK){
                //TODO: return with word and scrollview value to remove from restoredText
                String delete = data.getStringExtra("word");
                savedWords.remove(delete);
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
        //Open and read mainWordz file to repopulate wordsDisplay
        try{
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/" + FILE_NAME));
            String line;
            wordsDisplay.removeAllViews();
            int scrollViewCount = 0;
            while((line = br.readLine()) != null){
                final String words = line;
                final int i = scrollViewCount;
                Button newWordOrPhrase = new Button(this);
                newWordOrPhrase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addDesActivity(words, i);
                    }
                });
                newWordOrPhrase.setTransformationMethod(null);
                newWordOrPhrase.setGravity(Gravity.LEFT);
                newWordOrPhrase.setText(line);
                wordsDisplay.addView(newWordOrPhrase, scrollViewCount);
                scrollViewCount += 1;
            }
            br.close();
        }
        catch (IOException e){
            System.out.println("NO FILE TO READ");
        }
    }

    /*
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
    */

    @Override
    protected void onPause(){
        super.onPause();
        FileWriter fileWriter;
        File mainWordz;
        String line;
        mainWordz = new File (getFilesDir(), FILE_NAME);
        try {
            fileWriter = new FileWriter(mainWordz);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(int i = 0; i < wordsDisplay.getChildCount(); i++){
                View v = wordsDisplay.getChildAt(i);
                if(v instanceof Button){
                    String word = ((Button) v).getText().toString();
                    System.out.println("Wrote '" + word + i + "' to " + getFilesDir());
                    line = ((Button) v).getText().toString() + "\n";
                    bufferedWriter.write(line);
                }
            }
            bufferedWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
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
