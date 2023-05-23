package com.obushko.notesqlite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.obushko.notesqlite.R;
import com.obushko.notesqlite.adapter.NoteAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcView;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    //Ctrl+Alt+L format code
    private void init(){
        rcView = findViewById(R.id.rcView);
        noteAdapter = new NoteAdapter(this);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        rcView.setAdapter(noteAdapter);
    }
}