package com.example.activitiesandstorage;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SharedPrefStorage implements NoteStorage {

    private static final String PREF_NAME = "NotesPref";
    private SharedPreferences prefs;

    public SharedPrefStorage(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void saveNote(String title, String content) {
        prefs.edit().putString(title, content).apply();
    }

    @Override
    public void deleteNote(String title) {
        prefs.edit().remove(title).apply();
    }

    @Override
    public List<String> getAllNotesTitles() {
        Map<String, ?> all = prefs.getAll();
        return new ArrayList<>(all.keySet());
    }

    @Override
    public String getNoteContent(String title) {
        return prefs.getString(title, "");
    }
}