package com.obushko.notesqlite.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NoteDbHelper extends SQLiteOpenHelper {

    public NoteDbHelper(@Nullable Context context) {

        super(context, NoteDbConstants.DB_NAME, null, NoteDbConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(NoteDbConstants.TABLE_STRUCTURE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(NoteDbConstants.DROP_TABLE);
        onCreate(db);

    }
}
