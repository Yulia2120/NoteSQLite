package com.obushko.notesqlite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.obushko.notesqlite.R;
import com.obushko.notesqlite.data.NoteDbManager;

public class EditActivity extends AppCompatActivity {

    private ImageView imageView;
    private CoordinatorLayout imageContainer;
    private ImageButton imEditImage, imDeleteImage;
    private EditText editTextTitle, editTextDescription;
    private NoteDbManager noteDbManager;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        noteDbManager.openDb();

    }
    private void init(){
        floatingActionButton = findViewById(R.id.floatingActionButton);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextViewDescription);

        noteDbManager = new NoteDbManager(this);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();

                if (title.equals("")
                        || description.equals("")) {
                    Toast.makeText(EditActivity.this, "Title or Description is Empty!", Toast.LENGTH_SHORT).show();
                } else {
                    noteDbManager.insertToDb(title, description);
                    Toast.makeText(EditActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                    finish();
                    noteDbManager.closeDb();
                }
            }
        });

    }


}