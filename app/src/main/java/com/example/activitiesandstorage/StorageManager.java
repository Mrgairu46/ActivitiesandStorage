package com.example.activitiesandstorage;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageManager {
    private static final String PREF_NAME = "SettingsPref";
    private static final String KEY_STORAGE_MODE = "storage_mode";
    private static final String MODE_SHARED = "shared";
    private static final String MODE_FILE = "file";

    public static void setStorageMode(Context context, boolean useShared) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_STORAGE_MODE, useShared ? MODE_SHARED : MODE_FILE).apply();
    }

    public static boolean isUsingShared(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String mode = prefs.getString(KEY_STORAGE_MODE, MODE_SHARED);
        return mode.equals(MODE_SHARED);
    }

    public static NoteStorage getStorage(Context context) {
        return isUsingShared(context) ? new SharedPrefStorage(context) : new FileStorage(context);
    }

    public static String getModeName(Context context) {
        return isUsingShared(context)
                ? context.getString(R.string.storage_shared_prefs)
                : context.getString(R.string.storage_file);
    }
}