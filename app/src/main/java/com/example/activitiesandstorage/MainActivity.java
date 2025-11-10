package com.example.activitiesandstorage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewNotes;
    private TextView txtEmpty;
    private NoteStorage noteStorage;
    private boolean useSharedPrefs;
    private List<String> noteTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewNotes = findViewById(R.id.listViewNotes);
        txtEmpty = findViewById(R.id.txtEmpty);

        useSharedPrefs = StorageManager.isUsingShared(this);
        noteStorage = StorageManager.getStorage(this);

        loadNotes();

        listViewNotes.setOnItemClickListener((adapterView, view, position, id) -> {
            String title = noteTitles.get(position);
            String content = noteStorage.getNoteContent(title);

            Intent intent = new Intent(MainActivity.this, ViewNoteActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("content", content);
            startActivity(intent);
        });
    }

    private void loadNotes() {
        noteTitles = noteStorage.getAllNotesTitles();
        Log.d("DEBUG_NOTES", "Loaded " + noteTitles.size() + " notes");

        if (noteTitles.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
            listViewNotes.setVisibility(View.GONE);
        } else {
            txtEmpty.setVisibility(View.GONE);
            listViewNotes.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    noteTitles);
            listViewNotes.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_note) {
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        } else if (id == R.id.action_delete_note) {
            startActivity(new Intent(this, DeleteNoteActivity.class));
            return true;
        } else if (id == R.id.action_select_storage) {
            useSharedPrefs = !useSharedPrefs;
            StorageManager.setStorageMode(this, useSharedPrefs);
            noteStorage = StorageManager.getStorage(this);

            String modeName = StorageManager.getModeName(this);
            Toast.makeText(this,
                    getString(R.string.storage_switched, modeName),
                    Toast.LENGTH_SHORT).show();

            invalidateOptionsMenu();
            loadNotes();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}