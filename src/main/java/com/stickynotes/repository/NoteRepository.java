package com.stickynotes.repository;

import com.stickynotes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByIsDone(Boolean isDone);
    List<Note> findByCreateTime(String createTime);
    List<Note> findByCategoryIdAndIsDone(Long categoryId, Boolean isDone);
} 