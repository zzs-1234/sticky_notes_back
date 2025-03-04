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
    public List<Note> getAllNotes(Long userId) {
        return noteRepository.findByUserId(userId);
    }

    @Override
    public List<Note> getNotesByStatus(Long userId, Boolean isDone) {
        return noteRepository.findByUserIdAndIsDone(userId, isDone);
    }

    @Override
    public List<Note> getNotesByDate(Long userId, String date) {
        return noteRepository.findByUserIdAndCreateTime(userId, date);
    }

    @Override
    public List<Note> getNotesByCategoryAndStatus(Long userId, Long categoryId, Boolean isDone) {
        return noteRepository.findByUserIdAndCategoryIdAndIsDone(userId, categoryId, isDone);
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
        existingNote.setCategoryId(note.getCategoryId());
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