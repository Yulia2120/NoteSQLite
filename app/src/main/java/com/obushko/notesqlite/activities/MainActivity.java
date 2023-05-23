package com.obushko.notesqlite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.obushko.notesqlite.R;
import com.obushko.notesqlite.adapter.NoteAdapter;
import com.obushko.notesqlite.data.NoteDbManager;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcView;
    private NoteAdapter noteAdapter;
    private NoteDbManager noteDbManager;
    private EditText editTextTextPersonName, editTextTextPersonNameDesc;
    private FloatingActionButton floatingActionButton, floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    //Ctrl+Alt+L format code
    private void init(){
        rcView = findViewById(R.id.rcView);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextPersonNameDesc = findViewById(R.id.editTextViewDescription);
        noteAdapter = new NoteAdapter(this);
        noteDbManager = new NoteDbManager(this);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        rcView.setAdapter(noteAdapter);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDbManager.insertToDb("Test", "Test");
                noteAdapter.updateAdapter(noteDbManager.getFromDb());
              //  noteDbManager.insertToDb(editTextTextPersonName.getText().toString(), editTextTextPersonNameDesc.getText().toString());
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        noteDbManager.openDb();
        noteAdapter.updateAdapter(noteDbManager.getFromDb());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        noteDbManager.closeDb();
    }
}