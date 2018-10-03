package com.example.levi.wordz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class addWords extends AppCompatActivity {

    private EditText wordOrPhrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);
        wordOrPhrase = findViewById(R.id.editText);
    }

    public void sendMessage(View view) {
        // Do something in response to button
        String data = wordOrPhrase.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("wordOrPhrase", data);
        setResult(RESULT_OK, intent);
        finish();
    }
}
