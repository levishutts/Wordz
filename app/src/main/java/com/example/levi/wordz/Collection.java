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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Collection extends AppCompatActivity {

    private String wordOrPhrase;
    private String description;
    private String FILE_NAME = "collectionWordz.txt";
    private LinearLayout collectionScroll;
    private Set<String> myCollection;
    private int scrollViewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        collectionScroll = findViewById(R.id.linearLayout);
        scrollViewCount = 0;

        //Load file and populate collectionView
        try{
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/" + FILE_NAME));
            String line;
            collectionScroll.removeAllViews();
            int scrollViewCount = 0;
            while((line = br.readLine()) != null){
                TextView addToCollection = new TextView(this);
                //int length = wordOrPhrase.length();
                //ss1.setSpan(new RelativeSizeSpan(2f), 0, length, 0); // set size

                //addToCollection.setText(ss1);

                addToCollection.setText(line);
                collectionScroll.addView(addToCollection, scrollViewCount);
                scrollViewCount += 1;
            }
            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //If intent exists, add word and description to collectionScroll
        //Get word data
        Intent intent = getIntent();
        if (intent != null){
            wordOrPhrase = intent.getStringExtra("wordOrPhrase");
            description = intent.getStringExtra("description");
        }
        TextView addToCollection = new TextView(this);
        //int length = wordOrPhrase.length();
        //ss1.setSpan(new RelativeSizeSpan(2f), 0, length, 0); // set size

        //addToCollection.setText(ss1);

        addToCollection.setText(wordOrPhrase + ": " + description);
        collectionScroll.addView(addToCollection, scrollViewCount);
        scrollViewCount += 1;
    }

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

            for(int i = 0; i < collectionScroll.getChildCount(); i++){
                View v = collectionScroll.getChildAt(i);
                if(v instanceof TextView){
                    line = ((TextView) v).getText().toString() + "\n";
                    bufferedWriter.write(line);
                }
            }
            bufferedWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
