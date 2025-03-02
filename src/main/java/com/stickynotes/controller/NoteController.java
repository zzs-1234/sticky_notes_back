package com.stickynotes.controller;

import com.stickynotes.model.Note;
import com.stickynotes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@CrossOrigin
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(
            @RequestParam(required = false) Boolean isDone,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Long categoryId) {
        if (isDone != null && categoryId != null) {
            return ResponseEntity.ok(noteService.getNotesByCategoryAndStatus(categoryId, isDone));
        } else if (isDone != null) {
            return ResponseEntity.ok(noteService.getNotesByStatus(isDone));
        } else if (date != null) {
            return ResponseEntity.ok(noteService.getNotesByDate(date));
        }
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.createNote(note));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        return ResponseEntity.ok(noteService.updateNote(id, note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<Note> toggleNoteStatus(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.toggleNoteStatus(id));
    }
} 