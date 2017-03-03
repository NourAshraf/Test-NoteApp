package com.example.nour.noteapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Note extends ActionBarActivity {

    private EditText mEditTextNoteTitle;
    private EditText mEditTextNoteText;
    private String mTitle;
    private String mText;
    private boolean Edit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Variables();
    }

    private void ManageActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.notepad);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.main_screen_actionbar));
    }
    private void Variables() {
        ManageActionBar();
        mEditTextNoteTitle= (EditText) findViewById(R.id.etNoteTitle);
        mEditTextNoteText= (EditText) findViewById(R.id.etNoteText);
        if (getIntent().hasExtra("Title")) {
            GetEditData();
        }
    }

    private void GetEditData() {
        mTitle = getIntent().getExtras().getString("Title");
        mText = getIntent().getExtras().getString("Text");
        if (mTitle!=null){
            Edit=true;
            mEditTextNoteTitle.setText(mTitle);
            mEditTextNoteText.setText(mText);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            onSaveNote();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSaveNote() {
        if (Edit){
            File mFile=new File(getFilesDir(),mTitle);
            mFile.delete();
        }
        String Title = mEditTextNoteTitle.getText().toString();
        String Text = mEditTextNoteText.getText().toString();
        try {
            FileOutputStream mFileOutputStream=openFileOutput(Title,MODE_PRIVATE);
            mFileOutputStream.write(Text.getBytes());
            mFileOutputStream.close();
            finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
