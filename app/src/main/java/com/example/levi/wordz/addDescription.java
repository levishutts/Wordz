package com.example.levi.wordz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class addDescription extends AppCompatActivity {

    private String wordOrPhrase;
    private int button;
    private TextView header;
    private TextView description;

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
        System.out.println("Attempting to remove " + wordOrPhrase + " after adding description");
        // Do something in response to button
        Intent delete = new Intent();
        delete.putExtra("deleteButton", button);
        delete.putExtra("word", wordOrPhrase);
        setResult(RESULT_OK, delete);
        finish();

        //TODO: Properly remove word from MainActivity while still able to go to Collection and back to main
        Intent collectionActivity = new Intent(this, Collection.class);
        collectionActivity.putExtra("wordOrPhrase", wordOrPhrase);
        collectionActivity.putExtra("description", description.getText().toString());
        startActivity(collectionActivity);
    }

    public void deleteWord(View view){
        System.out.println("Attempting to delete " + wordOrPhrase);
        Intent delete = new Intent();
        delete.putExtra("deleteButton", button);
        delete.putExtra("word", wordOrPhrase);
        setResult(RESULT_OK, delete);
        finish();
    }
}
