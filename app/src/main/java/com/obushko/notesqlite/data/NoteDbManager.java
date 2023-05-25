package com.obushko.notesqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.obushko.notesqlite.adapter.ListItem;

import java.util.ArrayList;
import java.util.List;

public class NoteDbManager {
    private Context context;
    private NoteDbHelper noteDbHelper;
    private SQLiteDatabase db;

    public NoteDbManager(Context context) {
        this.context = context;
        noteDbHelper = new NoteDbHelper(context);
    }
    public void openDb(){
        db = noteDbHelper.getWritableDatabase();
    }
    public void insertToDb(String title, String description, String uri){

        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDbConstants.COLUMN_NAME_TITLE, title);
        contentValues.put(NoteDbConstants.COLUMN_NAME_DESCRIPTION, description);
        contentValues.put(NoteDbConstants.COLUMN_NAME_URI, uri);
        db.insert(NoteDbConstants.TABLE_NAME, null, contentValues);
    }
    public List<ListItem> getFromDb(){
        List<ListItem> tempList = new ArrayList<>();
        Cursor cursor = db.query(NoteDbConstants.TABLE_NAME, null, null,
                null, null, null, null);

        while (cursor.moveToNext()){

            ListItem item = new ListItem();

            String title = cursor.getString(cursor.getColumnIndexOrThrow(NoteDbConstants.COLUMN_NAME_TITLE));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(NoteDbConstants.COLUMN_NAME_DESCRIPTION));
            String uri = cursor.getString(cursor.getColumnIndexOrThrow(NoteDbConstants.COLUMN_NAME_URI));

            item.setTitle(title);
            item.setDescription(desc);
            item.setUri(uri);
            tempList.add(item);

        }
        cursor.close();
        return tempList;
    }
    public void closeDb(){
        noteDbHelper.close();
    }

}
