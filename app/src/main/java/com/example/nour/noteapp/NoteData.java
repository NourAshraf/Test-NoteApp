package com.example.nour.noteapp;

/**
 * Created by nour on 09-Jul-16.
 */
public class NoteData {
    private String Title;
    private String NoteText;

    public NoteData(String title, String noteText) {
        Title = title;
        NoteText = noteText;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNoteText() {
        return NoteText;
    }

    public void setNoteText(String noteText) {
        NoteText = noteText;
    }

    @Override
    public String toString() {
        return Title;
    }
}
