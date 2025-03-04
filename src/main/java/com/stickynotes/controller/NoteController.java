package com.stickynotes.controller;

import com.stickynotes.dto.NoteDto;
import com.stickynotes.model.Note;
import com.stickynotes.service.NoteService;
import com.stickynotes.util.JwtUtil;
import com.stickynotes.exception.CustomException;
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
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) Boolean isDone,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Long categoryId) {
        
        // 从 token 中获取用户 ID
        Long userId = getUserIdFromToken(token);
        
        if (isDone != null && categoryId != null) {
            return ResponseEntity.ok(noteService.getNotesByCategoryAndStatus(userId, categoryId, isDone));
        } else if (isDone != null) {
            return ResponseEntity.ok(noteService.getNotesByStatus(userId, isDone));
        } else if (date != null) {
            return ResponseEntity.ok(noteService.getNotesByDate(userId, date));
        }
        return ResponseEntity.ok(noteService.getAllNotes(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(
            @RequestHeader("Authorization") String token,
            @RequestBody NoteDto noteDto) {
        Long userId = getUserIdFromToken(token);
        
        Note note = new Note();
        note.setContent(noteDto.getContent());
        note.setColor(noteDto.getColor());
        note.setCreateTime(noteDto.getCreateTime());
        note.setIsDone(noteDto.getIsDone());
        note.setCategoryId(noteDto.getCategoryId());
        note.setUserId(userId);
        
        return ResponseEntity.ok(noteService.createNote(note));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        System.out.println("id: " + id);
        System.out.println(note.toString());
        note.setId(id);
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

    private Long getUserIdFromToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtUtil.getUserIdFromToken(token);
        }
        throw new CustomException("无效的token");
    }
} 