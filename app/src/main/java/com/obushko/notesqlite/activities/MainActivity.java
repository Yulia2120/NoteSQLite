package com.obushko.notesqlite.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.obushko.notesqlite.R;
import com.obushko.notesqlite.adapter.ListItem;
import com.obushko.notesqlite.adapter.NoteAdapter;
import com.obushko.notesqlite.data.NoteDbConstants;
import com.obushko.notesqlite.data.NoteDbManager;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcView;
    private NoteAdapter noteAdapter;
    private NoteDbManager noteDbManager;
    private FloatingActionButton  floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.id_search);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteAdapter.updateAdapter(noteDbManager.getFromDb(newText));
               // Log.d("NoteLog", "Query. " + newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //Ctrl+Alt+L format code
    private void init(){
        rcView = findViewById(R.id.rcView);
        noteAdapter = new NoteAdapter(this);
        noteDbManager = new NoteDbManager(this);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(rcView);
        rcView.setAdapter(noteAdapter);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        noteDbManager.openDb();
        noteAdapter.updateAdapter(noteDbManager.getFromDb(""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        noteDbManager.closeDb();
    }
    private ItemTouchHelper getItemTouchHelper(){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swipeFlags = ItemTouchHelper.LEFT;
                int dragFlags = ItemTouchHelper.RIGHT;
                return makeMovementFlags(swipeFlags, dragFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                 noteAdapter.removeItem(viewHolder.getAdapterPosition(), noteDbManager );
            }
        });
    }
}