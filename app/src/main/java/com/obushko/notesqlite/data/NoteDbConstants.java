package com.obushko.notesqlite.data;

public class NoteDbConstants {
    public static final String TABLE_NAME = "notes";
    public static final String _ID = "_id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public static final String COLUMN_NAME_URI = "uri";
    public static final String DB_NAME = "note.db";
    public static final int DB_VERSION = 2;

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS "+
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME_TITLE + " TEXT, " +
            COLUMN_NAME_DESCRIPTION + " TEXT, " +
            COLUMN_NAME_URI + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
