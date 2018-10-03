package com.example.levi.wordz;

import android.content.Intent;
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

public class Collection extends AppCompatActivity {

    private String wordOrPhrase;
    private String description;
    private LinearLayout collectionScroll;
    private Button toMain;
    private int scrollViewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        collectionScroll = findViewById(R.id.linearLayout);
        toMain = findViewById(R.id.button);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        wordOrPhrase = intent.getStringExtra("wordOrPhrase");
        description = intent.getStringExtra("description");
        TextView addToCollection = new TextView(this);

        SpannableString ss1=  new SpannableString(wordOrPhrase);
        int length = wordOrPhrase.length();
        ss1.setSpan(new RelativeSizeSpan(2f), 0,length, 0); // set size

        addToCollection.setText(ss1);
        addToCollection.append("\n     " + description);

        collectionScroll.addView(addToCollection, scrollViewCount);
        scrollViewCount += 1;
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
