package com.example.gujarati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView headingTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headingTextView = (TextView) findViewById(R.id.numbers_text_view);
        headingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }
        });

        headingTextView = (TextView) findViewById(R.id.family_members_text_view);
        headingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent familyMembersIntent = new Intent(MainActivity.this, FamilyMembersActivity.class);
                startActivity(familyMembersIntent);
            }
        });

        headingTextView = (TextView) findViewById(R.id.colors_text_view);
        headingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(colorsIntent);
            }
        });

        headingTextView = (TextView) findViewById(R.id.phrases_text_view);
        headingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesIntent);
            }
        });
    }
}