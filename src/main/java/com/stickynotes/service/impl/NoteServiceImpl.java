package com.stickynotes.service.impl;

import com.stickynotes.model.Note;
import com.stickynotes.repository.NoteRepository;
import com.stickynotes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public List<Note> getNotesByStatus(Boolean isDone) {
        return noteRepository.findByIsDone(isDone);
    }

    @Override
    public List<Note> getNotesByDate(String date) {
        return noteRepository.findByCreateTime(date);
    }

    @Override
    public List<Note> getNotesByCategoryAndStatus(Long categoryId, Boolean isDone) {
        return noteRepository.findByCategoryIdAndIsDone(categoryId, isDone);
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Long id, Note note) {
        Note existingNote = getNoteById(id);
        existingNote.setContent(note.getContent());
        existingNote.setColor(note.getColor());
        existingNote.setCategory(note.getCategory());
        return noteRepository.save(existingNote);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Note toggleNoteStatus(Long id) {
        Note note = getNoteById(id);
        note.setIsDone(!note.getIsDone());
        return noteRepository.save(note);
    }
} 