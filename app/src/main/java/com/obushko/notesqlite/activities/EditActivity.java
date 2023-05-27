package com.obushko.notesqlite.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.obushko.notesqlite.R;
import com.obushko.notesqlite.adapter.NotesListItem;
import com.obushko.notesqlite.data.AppExecuter;
import com.obushko.notesqlite.data.NoteDbConstants;
import com.obushko.notesqlite.data.NoteDbManager;

public class EditActivity extends AppCompatActivity {

    private final int PICK_IMAGE_CODE = 123;

    private ImageView imageView;
    private ConstraintLayout imageContainer;
    private ImageButton imEditImage, imDeleteImage;
    private FloatingActionButton flAddImage;
    private EditText editTextTitle, editTextDescription;
    private NoteDbManager noteDbManager;
    private String tempUri = "empty";
    private boolean isEditState = true;
    private  NotesListItem item;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getNoteIntents();
        noteDbManager.openDb();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE && data != null){

            tempUri = data.getData().toString();
            imageView.setImageURI(data.getData());
            getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    private void init(){
        floatingActionButton = findViewById(R.id.floatingActionButton);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextViewDescription);
        imDeleteImage = findViewById(R.id.imDeleteImg);
        imEditImage = findViewById(R.id.imEditImg);
        flAddImage = findViewById(R.id.flAddImage);
        imageContainer = findViewById(R.id.imageContainer);
        imageView = findViewById(R.id.imageView);

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
                    if(isEditState) {
                        AppExecuter.getInstance().getSubIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                noteDbManager.insertToDb(title, description, tempUri);
                            }
                        });
                        Toast.makeText(EditActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                    }else {
                        noteDbManager.updateToDb(title, description, tempUri, item.getId());
                        Toast.makeText(EditActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                    }
                    noteDbManager.closeDb();
                    finish();
                }
            }
        });

        flAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageContainer.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
            }
        });

        imDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.baseline_image_defoult);
                tempUri = "empty";
                imageContainer.setVisibility(View.GONE);
                flAddImage.setVisibility(View.VISIBLE);
            }
        });

        imEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooser = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                chooser.setType("image/*");
                startActivityForResult(chooser, PICK_IMAGE_CODE);
            }
        });


    }
    private void getNoteIntents(){

        Intent intent = getIntent();
        if(intent != null){
            item = (NotesListItem)intent.getSerializableExtra(NoteDbConstants.LIST_ITEM_INTENT);
            isEditState = intent.getBooleanExtra(NoteDbConstants.EDIT_STATE, true);

            if(!isEditState){
                editTextTitle.setText(item.getTitle());
                editTextDescription.setText(item.getDescription());
                if(!item.getUri().equals("empty")){
                    tempUri = item.getUri();
                    imageContainer.setVisibility(View.VISIBLE);
                    imageView.setImageURI(Uri.parse(item.getUri()));
                    imEditImage.setVisibility(View.GONE);
                    imDeleteImage.setVisibility(View.GONE);
                }
            }
        }
    }


}