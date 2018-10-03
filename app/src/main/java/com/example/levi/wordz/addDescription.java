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
    private Button okayEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);
        Intent intent = getIntent();
        wordOrPhrase = intent.getExtras().getString("info");
        button = intent.getExtras().getInt("button");

        header = findViewById(R.id.headerTextView);
        description = findViewById(R.id.descriptionEditText);
        okayEdit = findViewById(R.id.finishEdit);
        header.setText(wordOrPhrase);
    }

    public void sendMessage(View view) {
        // Do something in response to button
        Intent delete = new Intent();
        delete.putExtra("deleteButton", button);
        setResult(RESULT_OK, delete);

        Intent collectionActivity = new Intent(this, Collection.class);
        collectionActivity.putExtra("wordOrPhrase", wordOrPhrase);
        collectionActivity.putExtra("description", description.getText().toString());
        startActivity(collectionActivity);
    }
}
