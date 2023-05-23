package com.obushko.notesqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    public void insertToDb(String title, String description){

        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDbConstants.COLUMN_NAME_TITLE, title);
        contentValues.put(NoteDbConstants.COLUMN_NAME_DESCRIPTION, description);
        db.insert(NoteDbConstants.TABLE_NAME, null, contentValues);
    }
    public List<String> getFromDb(){
        List<String> tempList = new ArrayList<>();
        Cursor cursor = db.query(NoteDbConstants.TABLE_NAME, null, null,
                null, null, null, null);

        while (cursor.moveToNext()){

            String title = cursor.getString(cursor.getColumnIndexOrThrow(NoteDbConstants.COLUMN_NAME_TITLE));
            tempList.add(title);
//           long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(NoteDbConstants._ID));
//           tempList.add(String.valueOf(itemId));
        }
        cursor.close();
        return tempList;
    }
    public void closeDb(){
        noteDbHelper.close();
    }

}
