package com.obushko.notesqlite.data;

import android.app.LauncherActivity;

import com.obushko.notesqlite.adapter.NotesListItem;

import java.util.List;

public interface OnDataReceived {
    void onReceived(List<NotesListItem> list);
}
