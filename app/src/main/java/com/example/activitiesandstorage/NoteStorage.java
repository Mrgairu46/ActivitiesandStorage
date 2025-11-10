package com.example.activitiesandstorage;

import java.util.List;

public interface NoteStorage {
    void saveNote(String title, String content);
    void deleteNote(String title);
    List<String> getAllNotesTitles();
    String getNoteContent(String title);
}