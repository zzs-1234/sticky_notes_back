package com.stickynotes.service;

import com.stickynotes.model.Note;
import java.util.List;

public interface NoteService {
    List<Note> getAllNotes();
    List<Note> getNotesByStatus(Boolean isDone);
    List<Note> getNotesByDate(String date);
    List<Note> getNotesByCategoryAndStatus(Long categoryId, Boolean isDone);
    Note getNoteById(Long id);
    Note createNote(Note note);
    Note updateNote(Long id, Note note);
    void deleteNote(Long id);
    Note toggleNoteStatus(Long id);
} 