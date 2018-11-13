package com.example.levi.wordz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class addDescription extends AppCompatActivity {

    private String wordOrPhrase;
    private int button;
    private TextView header;
    private TextView description;
    private Set<String> savedWords;
    private LinearLayout myCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_description);
        Intent intent = getIntent();
        wordOrPhrase = intent.getExtras().getString("info");
        button = intent.getExtras().getInt("button");

        header = findViewById(R.id.headerTextView);
        description = findViewById(R.id.descriptionEditText);
        header.setText(wordOrPhrase);
    }

    public void sendMessage(View view) {
        Intent delete = new Intent();
        delete.putExtra("deleteButton", button);
        delete.putExtra("wordOrPhrase", wordOrPhrase);
        delete.putExtra("description", description.getText().toString());
        setResult(RESULT_OK, delete);
        finish();
    }

    public void deleteWord(View view){
        Intent delete = new Intent();
        delete.putExtra("deleteButton", button);
        setResult(RESULT_OK, delete);
        finish();
    }
}
