package com.example.levi.wordz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout wordsToLookup;

    private int scrollViewCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordsToLookup = findViewById(R.id.wordsLayout);
    }

    public void sendMessage(View v) {
        // Do something in response to button
        Intent intent = new Intent(this, addWords.class);
        startActivityForResult(intent, 1);
    }

    public void addDesActivity(String word, int button){
        Intent intent = new Intent(this, addDescription.class);
        intent.putExtra("info", word);
        intent.putExtra("button", button);
        startActivityForResult(intent, 2);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        if (requestCode == 2){
            if(resultCode == RESULT_OK){
                int button = data.getExtras().getInt("button");
                wordsToLookup.removeView(wordsToLookup.getChildAt(button));
                scrollViewCount -= 1;
            }
        }
    }
}