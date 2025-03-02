package com.stickynotes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stickynotes.model.Category;
import com.stickynotes.model.Note;
import com.stickynotes.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getAllNotes_ShouldReturnNotes() throws Exception {
        when(noteService.getAllNotes()).thenReturn(Arrays.asList(note));

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("测试内容"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void createNote_ShouldCreateNote() throws Exception {
        when(noteService.createNote(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("测试内容"))
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    void toggleNoteStatus_ShouldToggleStatus() throws Exception {
        note.setIsDone(true);
        when(noteService.toggleNoteStatus(1L)).thenReturn(note);

        mockMvc.perform(put("/api/notes/1/toggle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isDone").value(true))
                .andDo(MockMvcResultHandlers.print());;
    }
} 