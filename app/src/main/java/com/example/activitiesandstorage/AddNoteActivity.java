package com.example.activitiesandstorage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText txtTitle;
    private EditText txtContent;
    private NoteStorage noteStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // findViewById must reference IDs declared in activity_add_note.xml
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);

        // get active storage implementation
        noteStorage = StorageManager.getStorage(this);

        // Optional: ensure button exists and is clickable (defensive)
        Button saveButton = findViewById(R.id.btnSave);
        if (saveButton != null) {
            saveButton.setOnClickListener(this::onSaveClick);
        }
    }

    // Called either by button's onClick attribute or programmatically
    public void onSaveClick(View view) {
        String title = txtTitle.getText().toString().trim();
        String content = txtContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_warning), Toast.LENGTH_SHORT).show();
            return;
        }

        noteStorage.saveNote(title, content);
        Toast.makeText(this, getString(R.string.note_saved), Toast.LENGTH_SHORT).show();
        finish();
    }
}