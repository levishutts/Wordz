//TODO: Add capability to save and delete from collection
package com.example.levi.wordz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
    private Button edit;
    private Button delete;
    private Button main;
    private Button cancel;
    private Set<String> myCollection;
    private int scrollViewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        collectionScroll = findViewById(R.id.linearLayout);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        main = findViewById(R.id.main);
        cancel = findViewById(R.id.cancel);
        delete.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        scrollViewCount = 0;

        //Load file and populate collectionView
        try{
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/" + FILE_NAME));
            String line;
            collectionScroll.removeAllViews();
            int scrollViewCount = 0;

            while((line = br.readLine()) != null){
                CheckBox checkBox = new CheckBox(this);
                //int length = wordOrPhrase.length();
                //ss1.setSpan(new RelativeSizeSpan(2f), 0, length, 0); // set size

                //addToCollection.setText(ss1);

                checkBox.setText(line);
                collectionScroll.addView(checkBox, scrollViewCount);
                scrollViewCount += 1;

            }

            System.out.println(scrollViewCount);
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
        if((intent.getStringExtra("wordOrPhrase") != null) && (intent.getStringExtra("description") != null)){
            wordOrPhrase = intent.getStringExtra("wordOrPhrase");
            description = intent.getStringExtra("description");
            CheckBox addToCollection = new CheckBox(this);
            int length = wordOrPhrase.length();
            SpannableString text = new SpannableString(wordOrPhrase + " " + description);
            text.setSpan(new RelativeSizeSpan(2f), 0, length, 0); // set size

            addToCollection.setText(text);

            collectionScroll.addView(addToCollection, scrollViewCount);
            scrollViewCount += 1;
        }
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
                if(v instanceof CheckBox){
                    line = ((CheckBox) v).getText().toString() + "\n";
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

    public void edit(View view){
        edit.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.VISIBLE);
        main.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.VISIBLE);
    }

    public void delete(View view){
        edit.setVisibility(View.VISIBLE);
        delete.setVisibility(View.INVISIBLE);
        main.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        scrollViewCount = collectionScroll.getChildCount();
        for(int i = 0; i < scrollViewCount; i++){
            View v = collectionScroll.getChildAt(i);
            if(v instanceof CheckBox){
                CheckBox checkbox = ((CheckBox) v);
                if (checkbox.isChecked()){
                    collectionScroll.removeViewAt(i);
                    i--;
                }
            }
        }
    }

    public void cancel(View view){
        edit.setVisibility(View.VISIBLE);
        delete.setVisibility(View.INVISIBLE);
        main.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.INVISIBLE);
    }
}
