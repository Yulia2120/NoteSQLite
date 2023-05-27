package com.obushko.notesqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.obushko.notesqlite.adapter.NotesListItem;

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

    public void updateToDb(String title, String description, String uri, int id){
        String selection = NoteDbConstants._ID + "=" + id;
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDbConstants.COLUMN_NAME_TITLE, title);
        contentValues.put(NoteDbConstants.COLUMN_NAME_DESCRIPTION, description);
        contentValues.put(NoteDbConstants.COLUMN_NAME_URI, uri);

        db.update(NoteDbConstants.TABLE_NAME, contentValues, selection, null);
    }

    public void deleteFromDb(int id){
        String selection = NoteDbConstants._ID + "=" + id;
        db.delete(NoteDbConstants.TABLE_NAME, selection, null);
    }

    public List<NotesListItem> getFromDb(String searchText){
        List<NotesListItem> tempList = new ArrayList<>();
        String selection = NoteDbConstants.COLUMN_NAME_TITLE + " like ?";
        Cursor cursor = db.query(NoteDbConstants.TABLE_NAME, null, selection,
                new String[]{"%" + searchText + "%"}, null, null, null);

        while (cursor.moveToNext()){

            NotesListItem item = new NotesListItem();

            String title = cursor.getString(cursor.getColumnIndexOrThrow(NoteDbConstants.COLUMN_NAME_TITLE));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(NoteDbConstants.COLUMN_NAME_DESCRIPTION));
            String uri = cursor.getString(cursor.getColumnIndexOrThrow(NoteDbConstants.COLUMN_NAME_URI));
            int _id = cursor.getInt(cursor.getColumnIndexOrThrow(NoteDbConstants._ID));

            item.setTitle(title);
            item.setDescription(desc);
            item.setUri(uri);
            item.setId(_id);
            tempList.add(item);

        }
        cursor.close();
        return tempList;
    }
    public void closeDb(){
        noteDbHelper.close();
    }

}
