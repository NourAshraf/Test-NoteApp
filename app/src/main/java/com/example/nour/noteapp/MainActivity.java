package com.example.nour.noteapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView mListViewNotes;
    private Context mContext;
    private ArrayAdapter<NoteData> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Variables();
    }

    private void Variables() {
        ManageActionBar();
        mContext = MainActivity.this;
        mListViewNotes= (ListView) findViewById(R.id.lvNotes);
        mListViewNotes.setOnItemClickListener(this);
        mAdapter=new ArrayAdapter<NoteData>(this,R.layout.customnote,R.id.tvTitle){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                NoteData noteData = mAdapter.getItem(position);
                TextView mTextViewNote= (TextView) view.findViewById(R.id.tvNote);
                mTextViewNote.setText(noteData.getNoteText());
                return view;
            }
        };
        mListViewNotes.setAdapter(mAdapter);
        LoadNotes();
        registerForContextMenu(mListViewNotes);
    }

    private void ManageActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.notepad);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.main_screen_actionbar));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("Delete")){
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            TextView mTextView= (TextView) menuInfo.targetView.findViewById(R.id.tvTitle);
            String Title = mTextView.getText().toString();
            onDeleteNote(Title);
        }
        return super.onContextItemSelected(item);
    }

    private void onDeleteNote(String Title) {
        File mFile=new File(getFilesDir(),Title);
        mFile.delete();
        LoadNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            onNewNote();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onNewNote() {
        Intent mIntent=new Intent(mContext,Note.class);
        startActivityForResult(mIntent,0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            onEditNote(position);
    }

    private void onEditNote(int position) {
        String title = mAdapter.getItem(position).getTitle();
        File mFile=new File(getFilesDir(),title);
        try {
            FileInputStream mFileInputStream=openFileInput(mFile.getName());
            byte[] arr=new byte[(int) mFile.length()];
            int len = mFileInputStream.read(arr);
            mFileInputStream.close();
            String Text = new String(arr);
            Intent mIntent=new Intent(mContext,Note.class);
            mIntent.putExtra("Title",title);
            mIntent.putExtra("Text",Text);
            startActivityForResult(mIntent,0);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            LoadNotes();
    }

    private void LoadNotes() {
        mAdapter.clear();// remove old items

        File filesDir = getFilesDir();
        File[] files = filesDir.listFiles();
        for (File file : files) {
            try {
                FileInputStream fis = openFileInput(file.getName());
                byte[] barr = new byte[10];
                int len = fis.read(barr);
                fis.close();
                String Title=file.getName().toString();
                String sampleContent = new String(barr, 0, len);
                mAdapter.add(new NoteData(Title, sampleContent));


            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}
