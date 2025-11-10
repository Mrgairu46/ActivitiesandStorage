package com.example.activitiesandstorage;

import android.content.Context;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage implements NoteStorage {

    private final Context context;

    public FileStorage(Context context) {
        this.context = context;
    }

    @Override
    public void saveNote(String title, String content) {
        try (FileOutputStream fos = context.openFileOutput(title + ".txt", Context.MODE_PRIVATE)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNote(String title) {
        context.deleteFile(title + ".txt");
    }

    @Override
    public List<String> getAllNotesTitles() {
        String[] files = context.fileList();
        List<String> titles = new ArrayList<>();
        for (String file : files) {
            if (file.endsWith(".txt")) {
                titles.add(file.replace(".txt", ""));
            }
        }
        return titles;
    }

    @Override
    public String getNoteContent(String title) {
        try {
            FileInputStream fis = context.openFileInput(title + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line).append("\n");
            return sb.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}