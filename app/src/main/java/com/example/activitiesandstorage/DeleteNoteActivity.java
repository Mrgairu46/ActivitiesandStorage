package com.example.activitiesandstorage;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class DeleteNoteActivity extends AppCompatActivity {

    private ListView listViewDeleteNotes;
    private TextView txtEmptyDelete;
    private NoteStorage noteStorage;
    private List<String> noteTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.delete_note));
        }

        listViewDeleteNotes = findViewById(R.id.listViewDeleteNotes);
        txtEmptyDelete = findViewById(R.id.txtEmptyDelete);

        noteStorage = StorageManager.getStorage(this);

        loadNotes();

        // Handle delete when tapping on note
        listViewDeleteNotes.setOnItemClickListener((adapterView, view, position, id) -> {
            String title = noteTitles.get(position);

            noteStorage.deleteNote(title);
            Toast.makeText(this, getString(R.string.note_deleted), Toast.LENGTH_SHORT).show();

            loadNotes(); // Refresh list after deletion
        });
    }

    private void loadNotes() {
        noteTitles = noteStorage.getAllNotesTitles();

        if (noteTitles.isEmpty()) {
            txtEmptyDelete.setVisibility(View.VISIBLE);
            listViewDeleteNotes.setVisibility(View.GONE);
        } else {
            txtEmptyDelete.setVisibility(View.GONE);
            listViewDeleteNotes.setVisibility(View.VISIBLE);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    noteTitles
            );
            listViewDeleteNotes.setAdapter(adapter);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Go back when pressing toolbar back arrow
        return true;
    }
}