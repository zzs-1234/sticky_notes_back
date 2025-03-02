package com.stickynotes.service;

import com.stickynotes.model.Category;
import com.stickynotes.model.Note;
import com.stickynotes.repository.NoteRepository;
import com.stickynotes.service.impl.NoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    private Note note;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("工作");
        category.setColor("#ff7eb9");

        note = new Note();
        note.setId(1L);
        note.setContent("测试内容");
        note.setColor("#ff7eb9");
        note.setCreateTime("2024年03月01日");
        note.setIsDone(false);
        note.setCategory(category);
    }

    @Test
    void getAllNotes_ShouldReturnAllNotes() {
        List<Note> notes = Arrays.asList(note);
        when(noteRepository.findAll()).thenReturn(notes);

        List<Note> result = noteService.getAllNotes();

        assertEquals(1, result.size());
        assertEquals("测试内容", result.get(0).getContent());
        verify(noteRepository).findAll();
    }

    @Test
    void getNotesByStatus_ShouldReturnNotesByStatus() {
        List<Note> notes = Arrays.asList(note);
        when(noteRepository.findByIsDone(false)).thenReturn(notes);

        List<Note> result = noteService.getNotesByStatus(false);

        assertEquals(1, result.size());
        assertFalse(result.get(0).getIsDone());
        verify(noteRepository).findByIsDone(false);
    }

    @Test
    void getNotesByDate_ShouldReturnNotesByDate() {
        List<Note> notes = Arrays.asList(note);
        when(noteRepository.findByCreateTime("2024年03月01日")).thenReturn(notes);

        List<Note> result = noteService.getNotesByDate("2024年03月01日");

        assertEquals(1, result.size());
        assertEquals("2024年03月01日", result.get(0).getCreateTime());
        verify(noteRepository).findByCreateTime("2024年03月01日");
    }

    @Test
    void toggleNoteStatus_ShouldToggleStatus() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note result = noteService.toggleNoteStatus(1L);

        assertTrue(result.getIsDone());
        verify(noteRepository).findById(1L);
        verify(noteRepository).save(any(Note.class));
    }
} 