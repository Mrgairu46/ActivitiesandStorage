package com.example.activitiesandstorage;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ViewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        TextView txtTitle = findViewById(R.id.tvNoteTitle);
        TextView txtContent = findViewById(R.id.tvNoteContent);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        txtTitle.setText(title);
        txtContent.setText(content);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}